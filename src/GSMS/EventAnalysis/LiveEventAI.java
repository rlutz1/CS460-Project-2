package GSMS.EventAnalysis;

import GSMS.Common.AgentId;
import GSMS.Common.AgentType;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.SignalType;
import GSMS.Notification.AlertLevel;

/**
 * class to stand as component for live event AI
 */
public class LiveEventAI {

    public LiveEventAI() {
    }

    /**
     * analyzes the data of a given signal type (audio, visual, vitals).
     * If a safety/conflict event is detected, it returns an event category
     * as well as an associated probability based on the given processed data.
     *
     * @param data       The signal content string (may include a member prefix
     *                   for wearables: "memberId:data")
     * @param signalType The type of signal (AUDIO, VIDEO, WEARABLE)
     * @return non-null event if a safety/conflict event is detected;
     *         null otherwise.
     */
    public Event detectEvent(String data, SignalType signalType) {
        if (data == null) return null;

        String lower = data.toLowerCase();

        // ---- Use Case 1: Exhaustion (from wearable) ----
        if (lower.contains("over_exertion")) {
            String memberId = extractMemberId(data);
            // Fallback to JDANIELS1 if memberId extraction fails
            if (memberId == null) memberId = "JDANIELS1";
            return new Event(
                    AlertLevel.WARNING,
                    "Member showing signs of exhaustion.",
                    0.85,
                    new AgentId(memberId, "Jack Daniels", AgentType.MEMBER)  // name can be generic
            );
        }

        // ---- Use Case 2: Conflict (from video/audio) ----
        if (lower.contains("physical_altercation") || lower.contains("shouting")) {
            // Target the instructor directly for the demo notification
            return new Event(
                    AlertLevel.ALERT,
                    "Aggressive conflict detected in classroom.",
                    0.92,
                    new AgentId("JFONDA1", "Jane Fonda", AgentType.INSTRUCTOR)
            );
        }

        // ---- Use Case 4: Health Emergency (from wearable) ----
        if (lower.contains("critical_hr_drop")) {
            String memberId = extractMemberId(data);
            if (memberId == null) memberId = "JDANIELS1";
            return new Event(
                    AlertLevel.ALERT,
                    "Member collapsed – possible health emergency.",
                    0.98,
                    new AgentId(memberId, "Jack Daniels", AgentType.MEMBER)
            );
        }

        // ---- Additional catch‑all for video fall/collapse in case needed ----
        if (lower.contains("member_fall") || lower.contains("collapse_thud")) {
            return new Event(
                    AlertLevel.ALERT,
                    "Member fall detected.",
                    0.95,
                    new AgentId("JDANIELS1", "Jack Daniels", AgentType.MEMBER)
            );
        }

        // No recognized event
        return null;
    }

    /**
     * Helper to extract the member ID from a wearable string formatted as
     * "memberId:scenarioSignal". Returns null if the data doesn't contain
     * a colon.
     */
    private String extractMemberId(String data) {
        if (data.contains(":")) {
            return data.split(":")[0].trim();
        }
        return null;
    }
}