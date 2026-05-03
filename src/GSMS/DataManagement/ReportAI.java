package GSMS.DataManagement;

import GSMS.Common.ReportPackage;

/**
 * class to stand as component for report AI
 */

public class ReportAI {

    public ReportAI() {

    } // end constructor

    /**
     * returns a general compiled report of
     * gym space usage given the data received
     * @param profileAndNotificationInformation
     * @return
     */
    public String generateReport(ReportPackage profileAndNotificationInformation) {
        return "This instructor is a nightmare and should be fired.";
    } // end method

    /**
     * returns a specific compiled report about
     * class attendance according to the data received
     * @param memberOrClassAttendanceInfo
     * @return
     */
    public String generateAttendanceReport(String memberOrClassAttendanceInfo) {
        return null;
    } // end method

} // end class