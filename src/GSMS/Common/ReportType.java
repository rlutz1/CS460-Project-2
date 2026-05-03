package GSMS.Common;

import java.util.List;

/**
 * what is handed to the report AI as a report type.
 * adding just simple strings to signify what
 * to focus on when generating our nl report.
 * @param types
 */
public record ReportType(
    List<String> types
    )
{
} // end record
