package GSMS.Agents;

import GSMS.Common.*;
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
            AgentId instructorId,
            List<String> targetIds,
            ReportType reportType,
            Metadata timeFrame
    ) {
        gsmc.scheduleJob(new JobInfo(
                JobType.REPORT_GENERATION,
                instructorId,
                targetIds,
                reportType,
                timeFrame
        ));
    }

} // end class
