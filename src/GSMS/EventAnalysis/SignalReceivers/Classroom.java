package GSMS.EventAnalysis.SignalReceivers;

import GSMS.Common.AgentId;
import GSMS.Common.RoomId;
import GSMS.EventAnalysis.EventAnalyzer;
import GSMS.EventAnalysis.SignalReceivers.Hardware.AudioListener;
import GSMS.EventAnalysis.SignalReceivers.Hardware.VideoListener;
import GSMS.EventAnalysis.SignalReceivers.Hardware.WearableListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to stand as component for classroom abstraction
 */

public class Classroom {
    private EventAnalyzer eventAnalyzer;
    private RoomId classroomId;

    private String videoFeedData;
    private Integer audioDecibelData;
    private Map<AgentId, String> memberIdsToWearableData;

    private List<AudioListener> audioComponents;
    private List<VideoListener> videosComponents;
    private List<WearableListener> wearableComponents;

    public Classroom(EventAnalyzer eventAnalyzer, RoomId classroomId) {
        this.eventAnalyzer = eventAnalyzer;
        this.classroomId = classroomId;
        this.memberIdsToWearableData = new HashMap<>();
        this.audioComponents = new ArrayList<>();
        this.videosComponents = new ArrayList<>();
        this.wearableComponents = new ArrayList<>();
    } // end constructor
    /************************* NON-SAD * helper START *************************/
    public RoomId getClassroomId() {
        return classroomId;
    }
    public String getVideoFeedData() {
        return videoFeedData;
    }

    public Integer getAudioDecibelData() {
        return audioDecibelData;
    }

    public String getWearableInfoData(AgentId agentId) {
        // should always be non-null.
        if (memberIdsToWearableData.containsKey(agentId)) {
            return memberIdsToWearableData.get(agentId);
        }
        return null;
    }
    public void addNewAudioComponent() {
        audioComponents.add(new AudioListener(this));
    }
    public void addNewVideoComponent(){
        videosComponents.add(new VideoListener(this));
    }
    public void addNewWearableComponent(AgentId memberId){
        wearableComponents.add(new WearableListener(this, memberId));
    }

    public List<AudioListener> getAudioComponent(){
        return audioComponents;
    }
    public List<VideoListener> getVideosComponents(){
        return videosComponents;
    }
    public List<WearableListener> getWearableComponents(){
        return wearableComponents;
    }

    /************************* NON-SAD * helper END   *************************/

    /**
     * entry point to receive signal
     * data tied to this specific classroom’s sensors and feeds.
     * @param signal
     * @param signalType
     */
    public void receiveSignal(Signal<?> signal, SignalType signalType) {
        switch (signalType) {
            case AUDIO:
                audioDecibelData = ((Signal<Integer>) signal).getSignalData();
                eventAnalyzer.getAudioData(classroomId);
                break;
            case VIDEO:
                videoFeedData = ((Signal<String>) signal).getSignalData();
                eventAnalyzer.getVideoData(classroomId);
                break;
            case WEARABLE:
                // must always be non-null for WEARABLE signals.
                AgentId memberId = signal.getAgentId();

                // update current memberId-specific wearable data.
                if (memberIdsToWearableData.containsKey(memberId)) {
                    memberIdsToWearableData.replace(memberId,
                            (String) signal.getSignalData());
                }else{
                    memberIdsToWearableData.put(memberId,
                            (String) signal.getSignalData());
                }

                eventAnalyzer.getWearableData(classroomId, memberId);
                break;
        }
    } // end method




} // end class