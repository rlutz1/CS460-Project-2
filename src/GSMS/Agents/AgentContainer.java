package GSMS.Agents;

import GSMS.Common.AgentId;
import InstructorApplication.InstructorApplication;
import MemberApplication.MemberApplication;

import java.util.HashMap;

/**
 * this class is NOT to be instantiated
 * but to be treated as a shared global static
 * CONTAINER of the shared Instructor
 * and Member resources for the dispatchers.
 *
 * this is for ease of programming only.
 * not to have any major functionalty.
 */
public class AgentContainer {

    // mappings of agents to applications
    public static final HashMap<AgentId, Member> MemberApps = new HashMap<>();
    public static final HashMap<AgentId, Instructor> InstructorApps = new HashMap<>();

}
