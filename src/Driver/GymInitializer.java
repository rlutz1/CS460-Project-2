package Driver;

import java.util.List;

/**
 * to pack up all metadata about our fake gym.
 * this should be usable by both frontend
 * and back end to initialize corresponding components.
 */
public record GymInitializer(
        // give all agents (members && instructors), not specific to classroom
        // likely useful for backend primarily
        List<AgentInitializer> allAgentsOnsite,
        // classrooms (likely just one for simplicity.)
        List<ClassroomInitializer> classrooms,

        /* PRIMARILY FOR FRONTEND SETUP EASE */
        // the target member to move around
        AgentInitializer targetMember,
        // the target instructor to move around in the target classroom
        AgentInitializer targetInstructor,
        // main class room target member goes to that has target instructor and other members
        ClassroomInitializer targetClassroom
    )
{
}
