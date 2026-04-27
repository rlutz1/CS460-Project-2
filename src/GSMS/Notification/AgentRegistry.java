package GSMS.Notification;

import GSMS.Agents.Instructor;
import GSMS.Agents.Member;
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
     * Returns the Member component for the given AgentId.
     * @param agentId Unique identifier of the member.
     * @return The Member component, or null if not found.
     */
    Member getMemberById(AgentId agentId);

    /**
     * Returns the Instructor currently assigned to the classroom
     * that the specified member is attending.
     * @param agentId Unique identifier of the member.
     * @return The Instructor component, or null if not found.
     */
    Instructor getInstructorForMember(AgentId agentId);

    /**
     * Returns all Member components currently present in the given room.
     * @param roomId Unique identifier of the room.
     * @return List of Member components; empty list if room is empty.
     */
    List<Member> getMembersInRoom(RoomId roomId);

    /**
     * Returns the Instructor currently present in the given room.
     * @param roomId Unique identifier of the room.
     * @return The Instructor component, or null if none present.
     */
    Instructor getInstructorInRoom(RoomId roomId);

    /**
     * Returns the RoomId of the classroom the given member is currently in.
     * Used by EMERGENCY routing to resolve the broadcast room from a
     * member-scoped target before notifying all occupants.
     * @param agentId Unique identifier of the member.
     * @return The RoomId, or null if the member is not in a room.
     */
    RoomId getRoomIdForMember(AgentId agentId);

} // end interface
