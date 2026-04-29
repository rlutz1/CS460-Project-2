package Driver;

import GSMS.Common.AgentId;
import GSMS.Common.Metadata;

/**
 * internal class to encapsulate the instantiation of
 * members, instructors for the gym
 */
public record Initializer(
        AgentId id,
        Metadata initialProfileData
)
{
}
