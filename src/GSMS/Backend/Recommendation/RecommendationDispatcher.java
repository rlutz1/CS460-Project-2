package GSMS.Backend.Recommendation;

/**
 * class to stand as component for recc dispatcher
 *
 * main functions:
 * + receive a request for (a) itinerary analysis or (b) workout generation
 * + send out (a) itnerary with feedback or (b) system generated workout
 */

public class RecommendationDispatcher {

    public RecommendationDispatcher() {

    } // end constructor

    /**
     * entry point to receive an analysis or generation request
     * @param senderId
     * @param requestData
     */
    public void receiveRequest(String senderId, String requestData) {

    } // end method

    /**
     * send out a response to this agent
     * @param instructorID
     * @param responseData
     */
    public void sendItinerary(String instructorID, String responseData) {

    } // end method

    /**
     * send out a response to this agent
     * @param memberId
     * @param responseData
     */
    public void sendGeneratedWorkout(String memberId, String responseData) {

    } // end method

} // end class