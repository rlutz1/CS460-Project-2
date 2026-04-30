package Driver;

import GSMS.Common.RoomId;

import java.util.List;

/**
 * to pack up all metadata about a classroom that is useful for
 * initializing back and front end.
 * @param roomId
 * @param numAudioSensors
 * @param numCameras
 * @param numDoorwaySensors
 * @param agentsOnsite
 */
public record ClassroomInitializer(
        RoomId roomId,
        int numAudioSensors,
        int numCameras,
        int numDoorwaySensors,
        // give all agents in this room.
        // this is primarily for the frontend, unless useful for back.
        List<AgentInitializer> agentsOnsite
    )
{
}
