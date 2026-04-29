package GSMS.Common;

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
    String data // general use field
    )
{

    @Override
    public String toString() {
        return jobType + " requested by " + senderId;
    } // end method
} // end record
