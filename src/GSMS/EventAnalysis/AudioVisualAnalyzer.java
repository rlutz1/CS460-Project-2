package GSMS.EventAnalysis;

import GSMS.Common.RoomId;
import GSMS.EventAnalysis.SignalReceivers.Classroom;
import GSMS.EventAnalysis.SignalReceivers.Event;
import GSMS.EventAnalysis.SignalReceivers.SignalType;

public class AudioVisualAnalyzer extends EventAnalyzer {

    AudioVisualAnalyzer(){
        super();
    }

    @Override
    public void getData(RoomId roomId) {
        Classroom room = findClassroom(roomId);
        if (room != null) {
            // Thought here ; always sending audio alongside video because we
            // allow the ai to automatically do its own verification of event ?
            Event audioVisualEvent =
                    liveEventAI.detectEvent(room.getVideoFeedData()
                                          + room.getAudioDecibelData(),
                            SignalType.AUDIO_VISUAL);
            if (audioVisualEvent != null) {
                decideIfNeedToNotify(audioVisualEvent);
            }
        }
    }
}

