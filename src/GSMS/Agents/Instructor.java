package GSMS.Agents;

import GSMS.Common.AgentId;
import InstructorApplication.InstructorApplication;

/**
 * Stand-in for the Instructor component.
 * In charge of OUTgoing info to an instructor application.
 * Mirrors the structure of Member.java:
 *   - Holds an AgentId so the NotificationDispatcher can identify this instructor when resolving room recipients.
 *   - sendInformation(String) signature matches Member for consistency.
 */
public class Instructor {

    private final AgentId agentId;
    private InstructorApplication app;

    /**
     * @param agentId Unique identifier for this instructor.
     */
    public Instructor(AgentId agentId) {
        this.agentId = agentId;
        this.app = null;
    } // end constructor

    /**
     * @param agentId Unique identifier for this instructor.
     * @param app
     */
    public Instructor(AgentId agentId, InstructorApplication app) {
        this.agentId = agentId;
        this.app = app;
    } // end constructor

    /** @return This instructor's unique AgentId. */
    public AgentId getAgentId() { return agentId; }

    /** set the application if not done in constructor. */
    public void setApp(InstructorApplication app) { this.app = app; }

    /**
     * Directive to send network-ready data over the network
     * to a specific instructor application.
     * @param notificationOrInformation Serialized notification string to transmit.
     */
    public void sendInformation(String notificationOrInformation) {
        // TODO: implement network transmission to instructor application
        System.out.println("[Instructor " + agentId + "] sendInformation: "
                + notificationOrInformation);

        // use the access point to give the application data
        app.receiveInformation(notificationOrInformation);
    } // end method

} // end class
