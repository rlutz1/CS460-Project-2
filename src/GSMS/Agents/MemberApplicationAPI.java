package GSMS.Agents;


import GSMS.Common.AgentId;
import GSMS.Common.JobInfo;
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
                memberId,
                RecommendationType.SYSTEM_GENERATE,
                exerciseType
        )); // TODO: this is simply working with what is there
    } // end method
} // end class
