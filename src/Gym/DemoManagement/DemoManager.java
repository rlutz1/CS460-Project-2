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

import javax.swing.*;
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
        // stop all current animations
        for  (Transition anim : Actions.LiveTransitions) {
            anim.stop();
        } // end loop
        Actions.LiveTransitions.clear();
        // attempts below to reset stage--revisit
//        List<Node> children = new ArrayList<>(mainStage.getChildren());
//        for (Node child : mainStage.getChildren()) {
//            child.setTranslateX(0);
//            child.setTranslateY(0);
//        }
//        mainStage.getChildren().clear();
//        mainStage.getChildren().addAll(children);
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
                Actions.EnterClassroom(targetMember, 5, 710, -240);
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
                for (Node member : otherMembers.getChildren()) {
                    Actions.Workout(member, 1, 0.5);
                } // end loop

                Actions.Workout(targetMember, 1, 0.5);
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
