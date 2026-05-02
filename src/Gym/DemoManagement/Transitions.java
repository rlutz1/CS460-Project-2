package Gym.DemoManagement;

import Gym.AgentGraphics.AgentGraphic;
import Gym.AgentGraphics.MemberGraphic;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

// TODO: probably everything shouldn't be static, this was done for ease of initial use
// TODO: just make this a transition manager with non static fields, init in the demo manager.
public class Transitions {

    public static List<Transition> LiveTransitions = new ArrayList<>();

    /**
     * Scenario 2:
     * enter the target classroom
     * @param target
     * @param walkingSpeed
     * @param x
     * @param y
     */
    public static void EnterClassroom(AgentGraphic target, double walkingSpeed, double x, double y) {
        SequentialTransition seq = new SequentialTransition();

        TranslateTransition walkX = new TranslateTransition(Duration.seconds(walkingSpeed), target);
        walkX.setToX(x);
        walkX.setInterpolator(Interpolator.EASE_IN);

        TranslateTransition walkY = new TranslateTransition(Duration.seconds(walkingSpeed), target);
        walkY.setToY(y);
        walkY.setInterpolator(Interpolator.EASE_IN);

        seq.getChildren().addAll(
                walkX,
                walkY
        );

        LiveTransitions.add(seq); // add to a list of live transitions

        seq.setOnFinished(event -> {
            LiveTransitions.remove(seq); // remove myself once i'm finished
        });

        seq.play();
    } // end method

    /**
     * Scenario 2:
     * begin a simple workout animation
     * @param target
     * @param pace
     * @param squeeze
     */
    public static void Workout(AgentGraphic target, double pace, double squeeze) {
        SequentialTransition seq = new SequentialTransition(target);

        ScaleTransition workout = new ScaleTransition(Duration.seconds(pace), target);
        workout.setToX(squeeze);
        workout.setInterpolator(Interpolator.EASE_IN);

        ScaleTransition relax = new ScaleTransition(Duration.seconds(pace), target);
        relax.setToX(1);
        relax.setInterpolator(Interpolator.EASE_IN);

        seq.getChildren().addAll(
                workout,
                relax
        );

        seq.setCycleCount(Animation.INDEFINITE); // loop til we want it to stop

        LiveTransitions.add(seq); // add to a list of live transitions

        seq.play();
    } // end method

    /**
     * Scenario 2:
     * trigger exhaustion in the target member as visual
     * @param target
     * @param exhaustionRate
     */
    public static void TriggerExhaustion(AgentGraphic target, double exhaustionRate) {
        FillTransition exhaustion = new FillTransition(Duration.seconds(exhaustionRate), target.root);
        exhaustion.setToValue(Color.YELLOW);
        exhaustion.setAutoReverse(true);

        LiveTransitions.add(exhaustion); // add to a list of live transitions

        exhaustion.setOnFinished(event -> {
            LiveTransitions.remove(exhaustion); // remove myself once i'm finished
        });

        exhaustion.play();
    } // end method

    /**
     * Scenario 2:
     * relieve exhaustion in the target member as visual
     * @param target
     * @param reliefRate
     */
    public static void RelieveExhaustion(AgentGraphic target, double reliefRate) {
        FillTransition relief = new FillTransition(Duration.seconds(reliefRate), target.root);
        relief.setToValue(target.baseColor);
        relief.setAutoReverse(true);

        LiveTransitions.add(relief); // add to a list of live transitions

        relief.setOnFinished(event -> {
            LiveTransitions.remove(relief); // remove myself once i'm finished
        });

        relief.play();
    } // end method

    /**
     * Scenario 3:
     * trigger conflict between 2 specific members
     * @param target1
     * @param target2
     */
    public static void TriggerConflict(AgentGraphic target1, AgentGraphic target2, double angerRate) {
        ParallelTransition seq = new ParallelTransition();

        FillTransition angry1 = new FillTransition(Duration.seconds(angerRate), target1.root);
        angry1.setToValue(Color.ORANGERED);
        angry1.setAutoReverse(true);

        FillTransition angry2 = new FillTransition(Duration.seconds(angerRate), target2.root);
        angry2.setToValue(Color.ORANGERED);
        angry2.setAutoReverse(true);

        seq.getChildren().addAll(
                angry1,
                angry2
        );

        LiveTransitions.add(seq); // add to a list of live transitions

        seq.setOnFinished(event -> {
            LiveTransitions.remove(seq); // remove myself once i'm finished
        });

        seq.play();
    } // end method

    /**
     * Scenario 3:
     * trigger conflict between 2 specific members
     * @param target1
     * @param target2
     */
    public static void RelieveConflict(AgentGraphic target1, AgentGraphic target2, double reliefRate) {
        ParallelTransition seq = new ParallelTransition();

        FillTransition relief1 = new FillTransition(Duration.seconds(reliefRate), target1.root);
        relief1.setToValue(target1.baseColor);
        relief1.setAutoReverse(true);

        FillTransition relief2 = new FillTransition(Duration.seconds(reliefRate), target2.root);
        relief2.setToValue(target2.baseColor);
        relief2.setAutoReverse(true);

        seq.getChildren().addAll(
                relief1,
                relief2
        );

        LiveTransitions.add(seq); // add to a list of live transitions

        seq.setOnFinished(event -> {
            LiveTransitions.remove(seq); // remove myself once i'm finished
        });

        seq.play();
    } // end method

    /**
     * Scenario 4:
     * trigger a health emergency for a member
     * @param target
     * @param emergencyRate
     */
    public static void TriggerHealthEmergency(AgentGraphic target, double emergencyRate) {
        // stop the member from moving
        Transition tTarget = null;
        for (Transition t : LiveTransitions){
            if (t instanceof SequentialTransition  && ((SequentialTransition)t).getNode() == target) {
                tTarget = t;
                break;
            } // end if
        } // end loop
        if (tTarget != null) {
            tTarget.stop();
            LiveTransitions.remove(tTarget);
        } // end if


        FillTransition emergency = new FillTransition(Duration.seconds(emergencyRate), target.root);
        emergency.setToValue(Color.RED);
        emergency.setAutoReverse(true);

        LiveTransitions.add(emergency); // add to a list of live transitions

        emergency.setOnFinished(event -> {
            LiveTransitions.remove(emergency); // remove myself once i'm finished
        });

        emergency.play();
    } // end method


    /**
     * Scenario 4:
     * instructor moves to help the afflicted
     * @param target
     * @param walkingSpeed
     * @param x
     * @param y
     */
    public static void InstructorToHelp(AgentGraphic target, double walkingSpeed, double x, double y) {
        TranslateTransition help = new TranslateTransition(Duration.seconds(walkingSpeed), target);
        help.setToX(x);
        help.setToY(y);
        help.setInterpolator(Interpolator.LINEAR);

        LiveTransitions.add(help); // add to a list of live transitions

        help.setOnFinished(event -> {
            LiveTransitions.remove(help); // remove myself once i'm finished
        });

        help.play();
    } // end method

    /**
     * Scenario 2:
     * enter the target classroom
     * @param target
     * @param walkingSpeed
     * @param x
     * @param y
     */
    public static void ExitClassroom(AgentGraphic target, double walkingSpeed, double x, double y) {
        SequentialTransition seq = new SequentialTransition();

        TranslateTransition walkX = new TranslateTransition(Duration.seconds(walkingSpeed), target);
        walkX.setToX(x);
        walkX.setInterpolator(Interpolator.EASE_IN);

        TranslateTransition walkY = new TranslateTransition(Duration.seconds(walkingSpeed), target);
        walkY.setToY(y);
        walkY.setInterpolator(Interpolator.EASE_IN);

        seq.getChildren().addAll(
                walkY,
                walkX
        );

        LiveTransitions.add(seq); // add to a list of live transitions

        seq.setOnFinished(event -> {
            LiveTransitions.remove(seq); // remove myself once i'm finished
            target.setVisible(false);
        });

        seq.play();
    } // end method

    public static void test() {
        System.out.println("action test print?");
    } // end method
} // end class
