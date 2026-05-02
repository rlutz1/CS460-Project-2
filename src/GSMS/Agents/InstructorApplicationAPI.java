package GSMS.Agents;

import GSMS.Common.AgentId;
import GSMS.Root.GymSpaceManagementController;
import InstructorApplication.InstructorApplication;

/**
 * a stand-in class for being an API for incoming
 * requests from instructor applications
 */

public class InstructorApplicationAPI {

    private GymSpaceManagementController gsmc;

    public InstructorApplicationAPI(GymSpaceManagementController gsmc) {
        this.gsmc = gsmc;
    } // end constructor

//    public void receiveRequest(AgentId senderId, String requestType, String requestData) {
//
//    }

} // end class
