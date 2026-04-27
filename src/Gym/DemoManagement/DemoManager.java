package Gym.DemoManagement;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * this guy manages demo states and allows for more
 * encapsualtion of running the demo away from the
 * physical gym representation.
 */
public class DemoManager {

    private final List<DemoState> states; // list of frames, or states for demo
    private int currState;

    public StackPane mainStage;
    public AnchorPane targetMember;
    public Circle targetInstructor;
    public HBox otherMembers;

    public DemoManager() {
        this.states = new ArrayList<>();
        this.currState = 0; // first state always
        init(); // init the manager
    } // end constructor

    /**
     * this method is to be called when the "next" or "start"
     * buttons are pressed.
     */
    public void next() {
        if (!this.states.isEmpty()) {
            DemoState state = this.states.get(this.currState);
            this.currState = ((this.currState + 1) % this.states.size()); // for looping
            System.out.println(state.toString());
            state.activate(); // activate the state
        } // end if
    } // end method

    /**
     * call when resetting the demo states.
     */
    public void reset() {
        this.currState = 0;
    } // end method

    /**
     * this is going to be incredibly hardcoded, but testing.
     * initialize the demo state list.
     */
    private void init() {

        // TESTING! ALL HARDCODING BELOW AS I FIGURE OUT HOW TO BEST DO THIS

        // move the target in the classroom
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                SequentialTransition seq = new SequentialTransition();

                TranslateTransition transition1 = new TranslateTransition(Duration.seconds(3), targetMember);
                transition1.setToX(710);
                transition1.setInterpolator(Interpolator.EASE_IN);

                TranslateTransition transition2 = new TranslateTransition(Duration.seconds(3), targetMember);
                transition2.setToY(-240);
                transition2.setInterpolator(Interpolator.EASE_IN);

                seq.getChildren().addAll(
                        transition1,
                        transition2
                );

                seq.play();
            }
            @Override
            public String toString() {
                return "Target moving into classroom";
            }
        });

        // move the target in the classroom
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                int workoutPace = 1; // seconds

                for (Node member : otherMembers.getChildren()) {
                    SequentialTransition seq = new SequentialTransition();

                    ScaleTransition transition1 = new ScaleTransition(Duration.seconds(workoutPace), member);
                    transition1.setToX(0.5);
                    transition1.setInterpolator(Interpolator.EASE_IN);

                    ScaleTransition transition2 = new ScaleTransition(Duration.seconds(workoutPace), member);
                    transition2.setToX(1);
                    transition2.setInterpolator(Interpolator.EASE_IN);

                    seq.getChildren().addAll(
                            transition1,
                            transition2
                    );

                    seq.setCycleCount(Animation.INDEFINITE); // loop til we want it to stop
                    seq.play();
                } // end loop

                SequentialTransition seq = new SequentialTransition();

                ScaleTransition transition1 = new ScaleTransition(Duration.seconds(workoutPace), targetMember);
                transition1.setToX(0.5);
                transition1.setInterpolator(Interpolator.EASE_IN);

                ScaleTransition transition2 = new ScaleTransition(Duration.seconds(workoutPace), targetMember);
                transition2.setToX(1);
                transition2.setInterpolator(Interpolator.EASE_IN);

                seq.getChildren().addAll(
                        transition1,
                        transition2
                );

                seq.setCycleCount(Animation.INDEFINITE); // loop til we want it to stop
                seq.play();
                // TODO: likely need to ensure these are stopped manually. something does not like and occasional crash
            }
            @Override
            public String toString() {
                return "Trigger movement cycle (class begins).";
            }
        });


        // member starts having overexhaustion
        this.states.add(new DemoState() {
            @Override
            public void activate() {

                FillTransition transition1 = new FillTransition(Duration.millis(3000), (Shape)targetMember.getChildren().getFirst());
                transition1.setToValue(Color.YELLOW);
                transition1.setAutoReverse(true);

                transition1.play();
            }
            @Override
            public String toString() {
                return "Member starts having exhaustion.";
            }
        });
    } // end method

    //        this.states.add(new DemoState() {
    //            @Override
    //            public void activate() {
    //                Actions.test();
    //            }
    //            @Override
    //            public String toString() {
    //                return "Testing state here!";
    //            }
    //        });

} // end class
