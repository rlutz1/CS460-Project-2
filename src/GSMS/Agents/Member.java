package GSMS.Agents;

import GSMS.Common.AgentId;

/**
 * Stand-in for the Member component.
 * In charge of OUTgoing info to a member application.
 *   - Added AgentId field and constructor parameter so the NotificationDispatcher can identify this member when
 *     iterating room occupants during CRITICAL and EMERGENCY routing.
 *   - sendInformation(String) signature is unchanged...for now.
 */
public class Member {

    private final AgentId agentId;

    /**
     * @param agentId Unique identifier for this member.
     */
    public Member(AgentId agentId) {
        this.agentId = agentId;
    } // end constructor

    /** @return This member's unique AgentId. */
    public AgentId getAgentId() { return agentId; }

    /**
     * Directive to send network-ready data over the network
     * to a specific member application.
     * @param notificationOrInformation Serialized notification string to transmit.
     */
    public void sendInformation(String notificationOrInformation) {
        // TODO: implement network transmission to member application
        System.out.println("[Member " + agentId + "] sendInformation: "
                + notificationOrInformation);
    } // end method

} // end class
