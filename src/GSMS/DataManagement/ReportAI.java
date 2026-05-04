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
          // prompt: I have an instructor of a gym aerobics class named Jane Fonda. She has had a strangely high amount of safety, health, and conflict emergencies in the last month. Can you write a brief synopsis of faked events over the last month that have occurred in her classroom?
          // follow up when refused to do it with Jane Fonda's name: The same request, but her name is Jane Doe.
//        return """
//                Over the past month, Jane Fonda’s aerobics class has taken on a life of its own, evolving from a routine fitness session into a string of increasingly bizarre and memorable incidents. What began as minor disruptions—like a participant fainting during an especially intense cardio burst—quickly escalated into a pattern of unexpected emergencies. A misstep during a step routine led to a domino effect of toppled risers, while an overenthusiastic newcomer triggered a fire alarm after misunderstanding how to use the studio’s equipment.
//
//                Midway through the month, tensions flared when two regulars clashed over space and nearly turned a cooldown stretch into a full-blown argument, forcing Jane to step in as mediator. In another session, a spilled water bottle caused a slippery floor hazard that sent multiple participants scrambling, though thankfully without serious injury. The most surreal moment came when a power outage struck mid-class, leaving the group attempting to finish their routine in semi-darkness, guided only by Jane’s voice.
//
//                Despite the chaos, attendance has remained oddly high—drawn perhaps by the unpredictability as much as the workout itself. Jane Fonda, maintaining a calm and determined demeanor throughout, has become as much a crisis manager as a fitness instructor, closing out the month with a reputation for running the most eventful aerobics class in town.
//
//                """;

        // prompt: Okay, the first version, without the ominousness, make that one more absurdly dangerous.
        return """
                
                Over the past month, Jane Fonda’s aerobics class has transformed from a standard fitness session into something closer to a weekly survival exercise—though no one signed up for that. It started innocently enough with a mistimed grapevine step that somehow triggered a chain reaction of toppled platforms, water bottles, and one rogue resistance band that snapped across the room like a slingshot. From there, things escalated with alarming creativity.
                
                In the second week, a well-meaning attempt to “spice up cardio” with light props resulted in a cascade of airborne foam dumbbells, one of which struck the stereo hard enough to switch it into an endless loop of increasingly fast music, pushing the class into near breakneck tempo. During another session, someone brought in a personal mini-trampoline—unapproved—which led to a mid-routine bounce collision that sent two participants careening into a stack of yoga mats that collapsed like a poorly engineered tower.
                
                Mid-month, the hazards took on a life of their own. A spilled sports drink turned the floor into a skating rink just as the group transitioned into lateral lunges, sending multiple participants sliding in different directions. A ceiling fan, set inexplicably to its highest setting, began dislodging lightweight items from a nearby shelf, raining down towels and clipboards mid-routine. At one point, a resistance band got tangled in a door handle, snapping back with enough force to knock over a row of step risers like dominoes—again.
                
                The peak came during what was supposed to be a themed “high-energy finale” class. Fog from a misused cleaning spray set off the fire alarm, forcing an evacuation—only for several participants to attempt to keep the routine going in the parking lot, where a misplaced cone caused yet another trip-and-fall chain reaction. Jane, maintaining remarkable composure, somehow continued counting through most of it, pausing only to redirect traffic and ensure no one wandered into the path of a reversing car.
                
                Despite—or perhaps because of—the chaos, attendance has surged. Participants now arrive with a mix of gym gear and what looks suspiciously like protective instinct: tighter spacing, quicker reflexes, and an unspoken readiness to duck. Jane Fonda remains at the center of it all, leading each class with determination, as if daring the next improbable hazard to present itself—and somehow, every time, it does.
                
                """;
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