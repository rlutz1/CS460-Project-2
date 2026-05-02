package GSMS.Agents;

import GSMS.Common.AgentId;
import GSMS.Notification.Notification;
import InstructorApplication.InstructorApplication;
import MemberApplication.MemberApplication;

/**
 * Stand-in for the Member component.
 * In charge of OUTgoing info to a member application.
 *   - Added AgentId field and constructor parameter so the NotificationDispatcher can identify this member when
 *     iterating room occupants during CRITICAL and EMERGENCY routing.
 *   - sendInformation(String) signature is unchanged...for now.
 */
public class Member {

    private final AgentId agentId;
    private MemberApplication app;

    /**
     * @param agentId Unique identifier for this member.
     */
    public Member(AgentId agentId) {
        this.agentId = agentId;
        this.app = null;
    } // end constructor

    /**
     * @param agentId Unique identifier for this member.
     * @param app the application to talk to
     */
    public Member(AgentId agentId, MemberApplication app) {
        this.agentId = agentId;
        this.app = app;
    } // end constructor

    /** @return This member's unique AgentId. */
    public AgentId getAgentId() { return agentId; }

    /**
     * Directive to send network-ready data over the network
     * to a specific member application.
     * @param notificationOrInformation Serialized notification string to transmit.
     */
    public void sendInformation(Notification notificationOrInformation) {
        // TODO: implement network transmission to member application
        System.out.println("[Member " + agentId + "] sendInformation: "
                + notificationOrInformation);

        // use the access point to give the application data
        app.receiveInformation(notificationOrInformation);
    } // end method

} // end class
