package GSMS.EventAnalysis.SignalReceivers;

import GSMS.Common.AgentId;
import GSMS.Notification.AlertLevel;

public record Event(AlertLevel alertLevel,
                    String eventInfo,
                    double probabilityOfCorrectness,
                    AgentId agentId) {
}
