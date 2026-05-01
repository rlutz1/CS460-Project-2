package GSMS.EventAnalysis;

import GSMS.Common.AgentId;
import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.SignalType;

public class WearableAnalyzer extends EventAnalyzer{

    WearableAnalyzer(){
        super();
    }

    // Overload of base class
    public void getData(RoomId roomId, AgentId memberId) {
        Classroom room = findClassroom(roomId);
        if (room != null) {
            Event wearableEvent =
                    liveEventAI.detectEvent(room.getWearableInfoData(memberId),
                            SignalType.WEARABLE);
            if (wearableEvent != null) {
                decideIfNeedToNotify(wearableEvent);
            }
        }
    }
}
