package GSMS.Common;

import java.util.Objects;

/**
 * Kept separate from AgentId so that CRITICAL routing (which targets
 * a room) is compiler-enforced to be different from WARNING/EMERGENCY
 * routing (which targets a member by AgentId).
 */
public class RoomId {

    private final String id;

    /**
     * @param id Raw string identifier for this room.
     *           Must not be null or blank.
     */
    public RoomId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("RoomId must not be null or blank.");
        } // end if
        this.id = id;
    } // end constructor

    /**
     * @return The raw string value of this identifier.
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RoomId)) return false;
        return id.equals(((RoomId) obj).id);
    } // end method

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

} // end class
