package GSMS.Common;

import java.util.List;

/**
 * legit just a wrapper for the ai carrying
 * everthing handed to the datamanger for simulation
 * purposes.
 * @param targetId
 * @param reportType
 * @param timeFrame
 */
public record ReportPackage(
        List<String> targetId,
        ReportType reportType,
        Metadata timeFrame
    )
{
}
