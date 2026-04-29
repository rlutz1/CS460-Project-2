package GSMS.EventAnalysis.SignalReceivers;

import GSMS.Common.AgentId;

public class Signal<T> {
    private T data;
    private AgentId agentId = null;
    //TODO: (future) add more data specific for Logger. (e.g. roomId).
    public Signal(T data) {
        this.data = data;
    }
    public T getSignalData() {
        return data;
    }



    // must always set AgentId separately. Mainly for WEARABLE signal type use.
    public void setAgentId(AgentId agentId) {
        this.agentId = agentId;
    }
    public AgentId getAgentId() {
        return agentId;
    }
}
