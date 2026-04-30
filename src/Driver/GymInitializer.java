package Driver;

import java.util.List;

/**
 * to pack up all metadata about our fake gym.
 * this should be usable by both frontend
 * and back end to initialize corresponding components.
 */
public record GymInitializer(
        // give all agents, not specific to classroom
        // likely useful for backend primarily
        List<AgentInitializer> allAgentsOnsite,
        // classrooms (likely just one for simplicity for now.)
        List<ClassroomInitializer> classrooms,
        // the target member
        AgentInitializer targetMember,
        // the target instructor
        AgentInitializer targetInstructor,
        // main class room target member goes to that has target instructor
        ClassroomInitializer targetClassroom
    )
{
}
