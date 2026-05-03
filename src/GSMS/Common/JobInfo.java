package GSMS.Common;

import java.util.List;

/**
 * for use when scheduling a job with GSMC
 * when an application makes a request.
 *
 * add any fields you need for internal logic
 */
public record JobInfo(
        JobType jobType, // metadata about what we're sending for general purpose
        AgentId senderId, // AGENT sending, member or instructor. we need this for feedback
        RecommendationType recommendationOrAnalysis, // whether or not generating or analyzing
        List<String> targetIds, // for report generation: what agents, classes, rooms to focus on
        ReportType reportType, //
        Metadata timeFrame,
        String data // general use field
    )
{

    /**
     * constructor to allow for just making this for recommnedations
     * and not having to tweak all that shit.
     * @param jobType
     * @param senderId
     * @param recommendationOrAnalysis
     * @param data
     */
    public JobInfo(JobType jobType, // metadata about what we're sending for general purpose
                   AgentId senderId, // AGENT sending, member or instructor. we need this for feedback
                   RecommendationType recommendationOrAnalysis, // whether or not generating or analyzing)
                   String data // general use
        )// general use field
    {
        this(
                jobType,
                senderId,
                recommendationOrAnalysis,
                null,
                null,
                null,
                data
        );
    }

    /**
     * for sending through just enough info for a report generation
     * @param jobType
     * @param senderId
     * @param targetIds
     * @param reportType
     * @param timeFrame
     */
    public JobInfo(JobType jobType, // metadata about what we're sending for general purpose
                   AgentId senderId, // AGENT sending, member or instructor. we need this for feedback
                   List<String> targetIds, // for report generation: what agents, classes, rooms to focus on
                   ReportType reportType, // report types to focus on
                   Metadata timeFrame // the timeframe for the report
    )// general use field
    {
        this(
                jobType,
                senderId,
                null,
                targetIds,
                reportType,
                timeFrame,
                null
        );
    }


    @Override
    public String toString() {
        return jobType + " requested by " + senderId;
    } // end method
} // end record
