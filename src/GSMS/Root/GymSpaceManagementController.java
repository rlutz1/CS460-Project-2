package GSMS.Root;

import Driver.AgentInitializer;
import GSMS.Agents.Instructor;
import GSMS.Agents.InstructorApplicationAPI;
import GSMS.Agents.Member;
import GSMS.Agents.MemberApplicationAPI;
import GSMS.Common.AgentId;
import GSMS.Common.JobInfo;
import GSMS.Common.Metadata;
import GSMS.Common.RoomId;
import GSMS.DataManagement.DataManager;
import GSMS.DataManagement.Logger;
import GSMS.EventAnalysis.EventAnalyzer;
import GSMS.EventAnalysis.LiveEventAI;
import GSMS.Notification.AgentRegistry;
import GSMS.Recommendation.RecommendationDispatcher;
import MemberApplication.MemberApplication;
import InstructorApplication.InstructorApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The root of the GSMS back end.
 *
 * Responsibilities the GSMC has:
 *   1. Instantiate and wire all backend components at startup.
 *   2. Implement AgentRegistry so the NotificationDispatcher can resolve
 *      Member and Instructor components by ID or by room at runtime.
 *   3. Maintain a live room-occupancy state: which members and instructor
 *      are currently in each room. This state is updated by the EventAnalyzer
 *      as members enter/exit via doorway sensors (verifyRoomAccess).
 *   4. Act as the central message router via scheduleJob() for API-originated
 *      requests and requestJob() for inter-component requests.
 *
 * Component wiring order (for the constructor):
 *   Logger and DataManager have no dependencies — constructed first.
 *   LiveEventAI has no dependencies — constructed second.
 *   NotificationDispatcher depends on AgentRegistry (this) — third.
 *   EventAnalyzer depends on NotificationDispatcher, LiveEventAI, Logger — fourth.
 *   RecommendationDispatcher has no GSMC dependencies — fifth.
 *
 * AgentRegistry implementation:
 *   The GSMC maintains three in-memory maps for live room state:
 *     roomMembers : RoomId string → a list of Member components in that room.
 *     roomInstructor: RoomId string → Instructor component in that room.
 *     memberRoom: AgentId string → RoomId string of the member's current room.
 *   These maps are populated by registerMemberInRoom() / registerInstructorInRoom()
 *   and cleared by removeMemberFromRoom() as doorway events are processed.
 *
 *   A separate memberRegistry map holds all known Member components by AgentId
 *   regardless of whether they are currently in a room, so getMemberById() always
 *   works even before a member enters a classroom.
 *
 */
public class GymSpaceManagementController implements AgentRegistry {
    /*
    The Backend components
    */

    private final Logger                 logger;
    private final DataManager            dataManager;
    private final LiveEventAI            liveEventAI;
    private final EventAnalyzer          eventAnalyzer;
    private final RecommendationDispatcher recommendationDispatcher;

    /*
     The AgentRegistry live state
    /*

    /** All known Member components, keyed by their AgentId string. */
    private final Map<String, Member>       memberRegistry;

    /** All known Instructor components, keyed by their AgentId string. */
    private final Map<String, Instructor>   instructorRegistry;

    /** Maps a RoomId string to the list of Member components currently in it. */
    private final Map<String, List<Member>> roomMembers;

    /** Maps a RoomId string to the Instructor currently in it. */
    private final Map<String, Instructor>   roomInstructor;

    /** Maps a Member's AgentId string to the RoomId string they are currently in. */
    private final Map<String, String>       memberRoom;

    // following necessary API components to enable correct communication flow with incoming app requests
    private final MemberApplicationAPI      memberApi;
    private final InstructorApplicationAPI  instructorApi;



    /*
    The Constructor - which wires all components
    /*

    /**
     * Constructs the GSMC and initializes all backend components in dependency
     * order. No component is constructed before the components it depends on.
     */
    public GymSpaceManagementController() {
        //No-dependency components first
        this.logger = new Logger();
        this.dataManager = new DataManager(); // TODO: this will be accessed statically as per eliud, so this can be ridden of i think
        this.liveEventAI = new LiveEventAI();



        //EventAnalyzer depends on NotificationDispatcher, LiveEventAI, Logger
        this.eventAnalyzer = new EventAnalyzer(this);   // 'this' is the AgentRegistry

        //RecommendationDispatcher has no GSMC-level dependencies
        this.recommendationDispatcher = new RecommendationDispatcher();

        //Initialize occupancy tracking maps
        this.memberRegistry     = new HashMap<>();
        this.instructorRegistry = new HashMap<>();
        this.roomMembers        = new HashMap<>();
        this.roomInstructor     = new HashMap<>();
        this.memberRoom         = new HashMap<>();

        // initialize the API needed for application communication receipt
        this.memberApi     = new MemberApplicationAPI(this);
        this.instructorApi = new InstructorApplicationAPI(this);

        System.out.println("[GSMC] All components initialized.");

    } // end constructor

    /*
    The Public routing entry points
    /*

    /**
     * Entry point for requests arriving from Member or Instructor Application APIs.
     * Parses the request type and delegates to the appropriate internal component.
     *
     * Expected requestType values (consistent with use cases):
     *   "GENERATE_WORKOUT" → routes to RecommendationDispatcher (Use Case 3)
     *   "ANALYZE_ITINERARY" → routes to RecommendationDispatcher (Use Case 3 instructor path)
     *   "GENERATE_REPORT" → routes to DataManager (Use Case 5)
     *
     * @param info Serialized request payload (sender ID, request data, etc.).
     * @param sender String identifying the requesting API ("MEMBER_API" or "INSTRUCTOR_API").
     */

    public void scheduleJob(JobInfo info) {
        System.out.println("[GSMC] scheduleJob | sender=" + info.senderId() + " | info=" + info);

        if (info == null) {
            System.err.println("[GSMC] scheduleJob: received null or blank info. Ignored.");
            return;
        } // end if

        // TODO: define a structured request format (e.g. JSON or delimited string and parse requestType
        //  + payload from info. Stub routing shown below using placeholder parsing.

        switch (info.jobType()) {
            case RECOMMENDATION_ENGINE:
                recommendationDispatcher.receiveRequest(info.senderId(), info.recommendationOrAnalysis(), info.data());
                break;
            case REPORT_GENERATION:
                DataManager.GenerateReport(info.senderId(), info.targetIds(), info.reportType(), info.timeFrame());
                break;
            default:
                System.err.println("[GSMC] scheduleJob: unrecognized request type in info='"
                    + info + "'. No component delegated.");
        }

//        if (info.contains("GENERATE_WORKOUT")) {
//            // Use Case 3 — member requests a system-generated workout
//            recommendationDispatcher.receiveRequest(sender, "GENERATE_WORKOUT", info);
//
//        } else if (info.contains("ANALYZE_ITINERARY")) {
//            // Use Case 3 instructor path — instructor submits itinerary for analysis
//            recommendationDispatcher.receiveRequest(sender, "ANALYZE_ITINERARY", info);
//
//        } else if (info.contains("GENERATE_REPORT")) {
//            // Use Case 5 — attendant requests a usage or attendance report
//            dataManager.generateReport(sender, "GENERAL", info);
//
//        } else {
//
//        } // end if-else

    } // end method
    // OLD VERSION:
//    public void scheduleJob(String info, String sender) {
//        System.out.println("[GSMC] scheduleJob | sender=" + sender + " | info=" + info);
//
//        if (info == null || info.isBlank()) {
//            System.err.println("[GSMC] scheduleJob: received null or blank info. Ignored.");
//            return;
//        } // end if
//
//        // TODO: define a structured request format (e.g. JSON or delimited string and parse requestType
//        //  + payload from info. Stub routing shown below using placeholder parsing.
//
//        if (info.contains("GENERATE_WORKOUT")) {
//            // Use Case 3 — member requests a system-generated workout
//            recommendationDispatcher.receiveRequest(sender, "GENERATE_WORKOUT", info);
//
//        } else if (info.contains("ANALYZE_ITINERARY")) {
//            // Use Case 3 instructor path — instructor submits itinerary for analysis
//            recommendationDispatcher.receiveRequest(sender, "ANALYZE_ITINERARY", info);
//
//        } else if (info.contains("GENERATE_REPORT")) {
//            // Use Case 5 — attendant requests a usage or attendance report
//            dataManager.generateReport(sender, "GENERAL", info);
//
//        } else {
//            System.err.println("[GSMC] scheduleJob: unrecognized request type in info='"
//                    + info + "'. No component delegated.");
//        } // end if-else
//
//    } // end method

    // NOTE: commenting out only because we should ALWAYS know who the sender is.
    // otherwise, we don't know who to send data back to.
//    /**
//     * Preserved original single-parameter overload for backward compatibility
//     * with existing call sites that only pass info.
//     * Delegates to scheduleJob(String info, String sender) with sender="UNKNOWN".
//     * @param info Serialized request payload.
//     */
//    public void scheduleJob(String info) {
//        scheduleJob(info, "UNKNOWN");
//    } // end method

//    ====================================================================
//    NOTE: COMMENTING DUE TO REMOVAL FROM THE SAD
//    ====================================================================
//    /**
//     * Entry point for inter-component communication. Other backend components
//     * call this when they need the GSMC to coordinate an action that crosses
//     * component boundaries.
//     *
//     * Expected sender values:
//     *   "EVENT_ANALYZER" → sensor-triggered event needing cross-component action
//     *   "RECOMMENDATION" → workout/itinerary result ready to deliver
//     *   "DATA_MANAGER" → report generation complete, ready to return
//     *
//     * @param info   Serialized payload describing the needed action.
//     * @param sender String identifying the requesting component.
//     */
//    public void requestJob(String info, String sender) {
//        System.out.println("[GSMC] requestJob | sender=" + sender + " | info=" + info);
//
//        if (info == null || info.isBlank()) {
//            System.err.println("[GSMC] requestJob: received null or blank info. Ignored.");
//            return;
//        } // end if
//
//        // TODO: expand routing cases as components are implemented
//        switch (sender) {
//
//            case "EVENT_ANALYZER":
//                // EventAnalyzer is requesting cross-component coordination
//                // (e.g. room access verification needs a DataManager lookup)
//                // TODO: parse info and delegate to DataManager or NotificationDispatcher
//                break;
//
//            case "RECOMMENDATION":
//                // RecommendationDispatcher has a result ready to route back to a member
//                // TODO: parse memberId/instructorId from info and call Member/Instructor sendInformation() directly,
//                //  or route via the agent registry
//                break;
//
//            case "DATA_MANAGER":
//                // DataManager has a report ready to return to the requesting application
//                // TODO: parse target application from info and deliver
//                break;
//
//            default:
//                System.err.println("[GSMC] requestJob: unrecognized sender '"
//                        + sender + "'. No action taken.");
//
//        } // end switch
//
//    } // end method

    /*
    The AgentRegistry implementation
    These five methods are called exclusively by the NotificationDispatcher.
    /*

    /**
     * {@inheritDoc}
     * Resolves the Member component from the in-memory memberRegistry.
     */
    @Override
    public Member getMemberById(AgentId agentId) {
        return memberRegistry.get(agentId.getId());
    } // end method

    /**
     * {@inheritDoc}
     * Resolves the member's current room via memberRoom, then returns
     * the Instructor registered in that room via roomInstructor.
     */
    @Override
    public Instructor getInstructorForMember(AgentId agentId) {
        String roomId = memberRoom.get(agentId.getId());
        if (roomId == null) {
            return null;
        } // end if
        return roomInstructor.get(roomId);
    } // end method

    /**
     * {@inheritDoc}
     * Returns a defensive copy of the member list for the given room so
     * the dispatcher cannot accidentally modify the live occupancy state.
     */
    @Override
    public List<Member> getMembersInRoom(RoomId roomId) {
        List<Member> members = roomMembers.get(roomId.getId());
        if (members == null) {
            return new ArrayList<>();
        } // end if
        return new ArrayList<>(members);    // defensive copy
    } // end method

    /**
     * {@inheritDoc}
     * Returns the Instructor currently registered in the given room.
     */
    @Override
    public Instructor getInstructorInRoom(RoomId roomId) {
        return roomInstructor.get(roomId.getId());
    } // end method

    /**
     * {@inheritDoc}
     * Returns the RoomId of the classroom the given member is currently in.
     */
    @Override
    public RoomId getRoomIdForMember(AgentId agentId) {
        String roomId = memberRoom.get(agentId.getId());
        if (roomId == null) {
            return null;
        } // end if
        return new RoomId(roomId);
    } // end method

    /*
    The Occupancy state management
    Called by EventAnalyzer when doorway sensor signals are processed.
    /*

    /**
     * Registers a Member component as present in the given room.
     * Called when a member's RFID wearable is read by a doorway sensor.
     *
     * Also adds the member to memberRegistry if not already known,
     * so getMemberById() works from the first time a member is seen.
     *
     * @param member The Member component to register.
     * @param roomId String ID of the room the member is entering.
     */
    public void registerMemberInRoom(Member member, String roomId) {
        //Ensure the member is in the global registry
        memberRegistry.putIfAbsent(member.getAgentId().getId(), member);

        //Add to room occupancy list
        roomMembers.computeIfAbsent(roomId, k -> new ArrayList<>()).add(member);

        //Record which room this member is currently in
        memberRoom.put(member.getAgentId().getId(), roomId);

        System.out.println("[GSMC] registerMemberInRoom: member="
                + member.getAgentId() + " room=" + roomId);
    } // end method

    /**
     * Removes a Member from a room's occupancy list when they exit.
     * Called when a member's exit is detected via a doorway sensor.
     *
     * @param agentId String ID of the exiting member.
     * @param roomId  String ID of the room the member is leaving.
     */
    public void removeMemberFromRoom(String agentId, String roomId) {
        List<Member> members = roomMembers.get(roomId);
        if (members != null) {
            members.removeIf(m -> m.getAgentId().getId().equals(agentId));
        } // end if
        memberRoom.remove(agentId);

        System.out.println("[GSMC] removeMemberFromRoom: member=" + agentId
                + " room=" + roomId);
    } // end method

    /**
     * Registers an Instructor as present in the given room.
     * Called when an instructor's session begins in a classroom.
     *
     * @param instructor The Instructor component to register.
     * @param roomId     String ID of the room the instructor is in.
     */
    public void registerInstructorInRoom(Instructor instructor, String roomId) {
        instructorRegistry.putIfAbsent(instructor.getAgentId().getId(), instructor);
        roomInstructor.put(roomId, instructor);

        System.out.println("[GSMC] registerInstructorInRoom: instructor="
                + instructor.getAgentId() + " room=" + roomId);
    } // end method

    /**
     * Removes an Instructor from a room when their session ends.
     *
     * @param roomId String ID of the room the instructor is leaving.
     */
    public void removeInstructorFromRoom(String roomId) {
        Instructor removed = roomInstructor.remove(roomId);
        if (removed != null) {
            System.out.println("[GSMC] removeInstructorFromRoom: instructor="
                    + removed.getAgentId() + " room=" + roomId);
        } // end if
    } // end method

    /*
    The Component accessors — for use by other components that need a reference
    /*

    /** @return The EventAnalyzer component. */
    public EventAnalyzer getEventAnalyzer()                       { return eventAnalyzer; }

    /** @return The DataManager component. */
    public DataManager getDataManager()                           { return dataManager; }

    /** @return The Logger component. */
    public Logger getLogger()                                     { return logger; }

    /** @return The RecommendationDispatcher component. */
    public RecommendationDispatcher getRecommendationDispatcher() { return recommendationDispatcher; }

    /** @return the member api*/
    public MemberApplicationAPI getMemberApi()                    { return memberApi; }

    /** @return the member api*/
    public InstructorApplicationAPI getInstructorApi()            { return instructorApi; }


    // ==============================================================================
    // THIS IS FOR INIT ONLY, SIMULATION PURPOSES ONLY!!!
    // ==============================================================================

    /**
     * worst code everwritten, but works at a small scale.
     * initialize the initial members/instructors that already "exist" in the
     * system on start.
     *
     * we could likely move this to scheduleJob and iterate if we really wanted to
     * but i didn't want to change too much existing code unless it was truly necessary.
     * @param members
     * @param instructors
     * @param inits
     */
    public void registerAgentApplications(
            HashMap<AgentId, MemberApplication> members,
            HashMap<AgentId, InstructorApplication> instructors,
            List<AgentInitializer> inits // maybe a map later to make this cleaner
        )
    {
        // need to pass to data manager to register components.
        Metadata profileInfo = null;

        for (Map.Entry<AgentId, MemberApplication> entry : members.entrySet()) {
            for (AgentInitializer init : inits) {
                if (init.id().getId().equals(entry.getKey().getId())) {
                    profileInfo = init.initialProfileData();
                    break;
                }
            }
            // give agent id, profile info, and the application for component init
            DataManager.AddProfile(entry.getKey(), profileInfo, entry.getValue());
        } // end loop

        for (Map.Entry<AgentId, InstructorApplication> entry : instructors.entrySet()) {
            for (AgentInitializer init : inits) {
                if (init.id().getId().equals(entry.getKey().getId())) {
                    profileInfo = init.initialProfileData();
                    break;
                }
            }
            // give agent id, profile info, and the application for component init
            DataManager.AddProfile(entry.getKey(), profileInfo, entry.getValue());
        } // end loop
    }
} // end class
