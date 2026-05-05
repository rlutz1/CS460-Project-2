package GSMS.DataManagement;

import GSMS.Agents.AgentContainer;
import GSMS.Agents.InstructorDispatcher;
import GSMS.Agents.MemberDispatcher;
import GSMS.Common.AgentId;
import GSMS.Common.Metadata;
import GSMS.Common.ReportPackage;
import GSMS.Common.ReportType;
import GSMS.Notification.AlertLevel;
import GSMS.Notification.Notification;
import MemberApplication.MemberApplication;
import InstructorApplication.InstructorApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * class to stand as component for data manager
 */

public class DataManager {

    private static ReportAI ai = new ReportAI();

    public DataManager() {

    } // end constructor

    /**
     * Add a new member profile to the database,
     * along with their corresponding communication component instantiation
     * @param agentId agent id
     * @param profileInfo profile info to add
     * @param app "network" communication info to instantiate
     */
    public static void AddProfile(AgentId agentId, Metadata profileInfo, MemberApplication app) {
        // write new member to the database (implicit for now), but what the profile info is for
        // create a new member component.
        MemberDispatcher member = new MemberDispatcher(agentId, app);
        AgentContainer.MemberApps.put(agentId, member);
        System.out.println("[DATA MAN] Added profile for member " + agentId);
    } // end method

    /**
     *  Add a new instructor profile to the database,
     *  along with their corresponding communication component instantiation
     * @param agentId agent id
     * @param profileInfo profile info to add
     * @param app network" communication info to instantiate
     */
    public static void AddProfile(AgentId agentId, Metadata profileInfo, InstructorApplication app) {
        // write new instructor to the database (implicit for now), but what the profile info is for
        // create a new instructor component.
        InstructorDispatcher instructor = new InstructorDispatcher(agentId, app);
        AgentContainer.InstructorApps.put(agentId, instructor);
        System.out.println("[DATA MAN] Added profile for instructor " + agentId);
    } // end method

    /**
     * Add a new class for a specific instructor.
     * @param instructorId instructor of class
     * @param classInfo the initial class information package
     */
    public static void AddClass(AgentId instructorId, Metadata classInfo) {

    } // and method

    /**
     * return a member or instructor’s profile information
     * requested by the requesting component
     * Optionally include what piece of information to focus on.
     * @param agentId agent id
     * @param dataSpec specific info to pull out
     * @return profile data
     */
    public static String GetProfile(AgentId agentId, String dataSpec) {
        return null;
    } // end method

    /**
     * return a class’s profile information requested
     * by the requesting component
     * Optionally include what piece of information to focus on.
     * @param classId class id
     * @param dataSpec specific info to pull out
     * @return class data
     */
    public static String GetClass(String classId, String dataSpec) {
        return null;
    } // end method

    /**
     * modify a member or instructor’s profile information as
     * requested by the requesting component
     * @param agentId agent id
     * @param modifiction modification specifics to the agent profile
     */
    public static void ModifyProfile(String agentId, String modifiction) {

    } // end method

    /**
     * modify a class’s profile information as requested
     * by the requesting component
     * @param classId class id
     * @param modifiction modification specifics to the class info
     */
    public static void ModifyClass(String classId, String modifiction) {

    } // end method

    /**
     * retrieve all alerts about a specific target
     * (member, instructor, class)
     * with an optional alertLevel and time frame
     * @param agentId target id (agent here)
     * @param targetId class id
     * @param alertLevel alert level to focus on
     * @param timeFrame optional time frame
     * @return logs from the database
     */
    public static List<String> RetrieveLogs(
            String agentId,
            String targetId,
            String alertLevel,
            String timeFrame
        )
    {
        return null;
    } // end method

    /**
     *  initiate the generation of a general or
     *  specific report from the Report AI.
     *  Pull all necessary profile and notification
     *  information from the database and
     *  hand to Report AI for generation.
     *  Optionally, it can be filtered by giving a
     *  specific target ID (instructor, member, class, room)
     *  or a list of them.
     * @param senderId sender id
     * @param targetId target(s) to focus on for report
     * @param reportType the type of report (focused or overivew)
     * @param timeFrame optional time frame
     */
    public static void GenerateReport(
            AgentId senderId,
            List<String> targetId,
            ReportType reportType,
            Metadata timeFrame
        )
    {
//        here are the following steps given our outline of process.
//        for demo purposes, we will not be implementing all of the following,
//        but this is here for acknowledgement of the actual flow.

//        for each targetId
//          depending on context of report type: alert level warning or alert
//          logs = RetrieveLogs(...)
//        for each targetId
//          if is classid: classProfile = GetClass(id, null)
//          if is instructorid or memberid: agentId = GetProfile(id, null)

//        package into ReportPackage.
//        the above calls have not been implemented, so we simulate below by
//        only packing up the target, report type and time frame.

        String report = ai.generateReport(new ReportPackage(targetId, reportType, timeFrame));

        Notification information = new Notification(
                report,
                AlertLevel.INFORMATIONAL_MESSAGE,
                senderId
        );

        switch (senderId.getType()) {
            case MEMBER -> AgentContainer.MemberApps.get(senderId).sendInformation(information);
            case INSTRUCTOR -> AgentContainer.InstructorApps.get(senderId).sendInformation(information);
            default -> System.out.println("[DATA MAN DISPATCH] Somehow a sender id was sent with not defined type: " + senderId);
        } // end switch case
    } // end method

} // end class