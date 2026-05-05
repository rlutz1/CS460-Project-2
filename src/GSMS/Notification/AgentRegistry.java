package GSMS.Notification;

import GSMS.Agents.InstructorDispatcher;
import GSMS.Agents.MemberDispatcher;
import GSMS.Common.AgentId;
import GSMS.Common.RoomId;

import java.util.List;

/**
 * Integration point between the NotificationDispatcher and the
 * GymSpaceManagementController (GSMC).
 *
 * The GSMC implements this interface and passes itself into the NotificationDispatcher constructor.
 * This keeps the dispatcher fully detached from the GSMC's internal structures. The dispatcher only ever talks
 * to this interface, never directly to the GSMC.
 *
 * The dispatcher uses these five methods across its three routing
 * paths:
 *
 *   WARNING (UC1):
 *     getMemberById() — resolve the affected member.
 *     getInstructorForMember() — resolve their classroom instructor.
 *
 *   CRITICAL (UC2):
 *     getMembersInRoom() — all occupants of the event room.
 *     getInstructorInRoom() — the room's instructor.
 *
 *   EMERGENCY (UC4):
 *     getRoomIdForMember() — find the member's current room first.
 *     Then: getMembersInRoom() + getInstructorInRoom() on that room.
 */
public interface AgentRegistry {

    /**
     * Returns the MemberDispatcher component for the given AgentId.
     * @param agentId Unique identifier of the member.
     * @return The MemberDispatcher component, or null if not found.
     */
    MemberDispatcher getMemberById(AgentId agentId);

    /**
     * Returns the InstructorDispatcher currently assigned to the classroom
     * that the specified member is attending.
     * @param agentId Unique identifier of the member.
     * @return The InstructorDispatcher component, or null if not found.
     */
    InstructorDispatcher getInstructorForMember(AgentId agentId);

    /**
     * Returns all MemberDispatcher components currently present in the given room.
     * @param roomId Unique identifier of the room.
     * @return List of MemberDispatcher components; empty list if room is empty.
     */
    List<MemberDispatcher> getMembersInRoom(RoomId roomId);

    /**
     * Returns the InstructorDispatcher currently present in the given room.
     * @param roomId Unique identifier of the room.
     * @return The InstructorDispatcher component, or null if none present.
     */
    InstructorDispatcher getInstructorInRoom(RoomId roomId);

    /**
     * Returns the RoomId of the classroom the given member is currently in.
     * Used by EMERGENCY routing to resolve the broadcast room from a
     * member-scoped target before notifying all occupants.
     * @param agentId Unique identifier of the member.
     * @return The RoomId, or null if the member is not in a room.
     */
    RoomId getRoomIdForMember(AgentId agentId);

} // end interface
