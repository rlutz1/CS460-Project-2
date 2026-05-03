package GSMS.Agents;

import GSMS.Common.AgentId;
import GSMS.Common.Metadata;
import GSMS.Common.ReportType;
import GSMS.Root.GymSpaceManagementController;
import InstructorApplication.InstructorApplication;

import java.util.List;

/**
 * a stand-in class for being an API for incoming
 * requests from instructor applications
 */

public class InstructorApplicationAPI {

    private GymSpaceManagementController gsmc;

    public InstructorApplicationAPI(GymSpaceManagementController gsmc) {
        this.gsmc = gsmc;
    } // end constructor

    public void requestReport(
            List<String> targetIds,
            ReportType reportType,
            Metadata timeFrame
    ) {

        // TODO create a Job Info and send to root with jobtype data management
    }

} // end class
