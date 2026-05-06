package GSMS.Agents;


import GSMS.Common.AgentId;
import GSMS.Common.JobInfo;
import GSMS.Common.JobType;
import GSMS.Common.RecommendationType;
import GSMS.Root.GymSpaceManagementController;

/**
 * a stand-in class for being an API for incoming
 * requests from member applications
 */

public class MemberApplicationAPI {

    private GymSpaceManagementController gsmc;

    public MemberApplicationAPI(GymSpaceManagementController gsmc) {
        this.gsmc = gsmc;
    } // end constructor

    public void transmitRecommendationRequest(AgentId memberId, String exerciseType) {
        gsmc.scheduleJob(new JobInfo(
                JobType.RECOMMENDATION_ENGINE,
                memberId,
                RecommendationType.SYSTEM_GENERATE,
                exerciseType
        ));
    } // end method

//    public void transmitScheduleViewingRequest(AgentId memberId, String viewRequest) {
//        gsmc.scheduleJob(new JobInfo(
//                JobType.RECOMMENDATION_ENGINE,
//                memberId,
//                RecommendationType.SEND_SCHEDULE,
//                viewRequest
//        ));
//    }
} // end class
