package Gym.DemoManagement;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Actions {

    public static List<Transition> LiveTransitions = new ArrayList<>();

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

    public static void TriggerExhaustion(Node target) {

    } // end method

    public static void test() {
        System.out.println("action test print?");
    } // end method
} // end class
