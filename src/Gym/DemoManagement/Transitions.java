package Gym.DemoManagement;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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
    public static void EnterClassroom(Node target, int walkingSpeed, int x, int y) {
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
    public static void Workout(Node target, int pace, double squeeze) {
        SequentialTransition seq = new SequentialTransition();

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

//        seq.setOnFinished(event -> {
//            LiveTransitions.remove(seq); // remove myself once i'm finished
//        });

        seq.play();
    } // end method

    /**
     * Scenario 2:
     * trigger exhaustion in the target member as visual
     * @param target
     * @param exhaustionRate
     */
    public static void TriggerExhaustion(Node target, int exhaustionRate) {
        FillTransition exhaustion = new FillTransition(Duration.seconds(exhaustionRate), (Shape)target);
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
    public static void RelieveExhaustion(Node target, int reliefRate) {
        FillTransition relief = new FillTransition(Duration.seconds(reliefRate), (Shape)target);
        relief.setToValue(Color.web("#22ff1f"));
        relief.setAutoReverse(true);

        LiveTransitions.add(relief); // add to a list of live transitions

        relief.setOnFinished(event -> {
            LiveTransitions.remove(relief); // remove myself once i'm finished
        });

        relief.play();
    } // end method

//    public static void SendProblemSignals

    public static void test() {
        System.out.println("action test print?");
    } // end method
} // end class
