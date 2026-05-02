package GSMS.Common;

import java.util.Objects;

/**
 * Using a named type instead of a raw String lets the compiler
 * distinguish an AgentId from a RoomId at call sites, preventing
 * argument-swap mistakes from compiling silently.
 */
public class AgentId {

    private final String id;
    private final String name;
    private final AgentType type;

    /**
     * @param id Raw string identifier for this agent.
     *           Must not be null or blank.
     */
    public AgentId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("AgentId must not be null or blank.");
        } // end if
        this.id = id;
        this.name = "Unknown";
        this.type = AgentType.ANY;
    } // end constructor

    /**
     * @param id Raw string identifier for this agent.
     *           Must not be null or blank.
     */
    public AgentId(String id, String name, AgentType type) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("AgentId must not be null or blank.");
        } // end if
        this.id = id;
        this.name = name;
        this.type = type;
    } // end constructor

    /**
     * @return The raw string value of this identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return a readable name of the agent
     */
    public String getName() { return name; }

    /**
     * @return the type of the agent (instructor/member)
     */
    public AgentType getType() { return type; }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AgentId)) return false;
        return id.equals(((AgentId) obj).id);
    } // end method

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

} // end class
