package Gym.DemoManagement;

import Gym.Hardware.AudioSensor;
import Gym.Hardware.Camera;
import Gym.Hardware.WearableSensors;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

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

    // for testing and sending basic crap to backend
    // this may change to be more representative of the scenario, environment.
    public AudioSensor audioSensor;
    public Camera cameraFeed;
    public WearableSensors wearable;

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
        for  (Transition anim : Transitions.LiveTransitions) {
            anim.stop();
        } // end loop
        Transitions.LiveTransitions.clear();
        // attempts below to reset stage--revisit
        targetMember.setTranslateX(0);
        targetMember.setTranslateY(0);
        targetMember.setScaleX(1);
        targetMember.setScaleY(1);
        ((Shape)(targetMember.getChildren().getFirst())).setFill(Color.web("#22ff1f"));
        for (Node member : otherMembers.getChildren()) {
            member.setTranslateX(0);
            member.setTranslateY(0);
            member.setScaleX(1);
            member.setScaleY(1);
            ((Shape)member).setFill(Color.web("#22ff1f"));
        }
//        mainStage.getChildren().clear();
//        mainStage.getChildren().addAll(children);
    } // end method

    /**
     * this is going to be incredibly hardcoded, but testing.
     * initialize the demo state list.
     */
    private void init() {
//        initScenario1();
        initScenario2();
//        initScenario3();
//        initScenario4();
//        initScenario5();
    } // end method

    /**
     * remove out the first scenario frames
     */
    private void initScenario1() {
        // TODO
        // set an initial scene.
        // control goes over to the applications
        // transition to scenario 2
    } // end method

    /**
     * remove out the second scenario frames
     */
    private void initScenario2() {
        // move the target in the classroom
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.EnterClassroom(targetMember, 5, 710, -240);
            }
            @Override
            public String toString() {
                return "Target moving into classroom";
            }
        });

        // move everyone in room is working out
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout(member, 1, 0.5);
                } // end loop

                Transitions.Workout(targetMember, 1, 0.5);
            }
            @Override
            public String toString() {
                return "Trigger movement cycle (class begins).";
            }
        });


        // member starts having over exhaustion
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.TriggerExhaustion(targetMember.getChildren().getFirst(), 4);
            }
            @Override
            public String toString() {
                return "Member starts having exhaustion.";
            }
        });

        // trigger the sending of some bad signals to the backend.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                cameraFeed.sendSignal(); // TODO: this needs to be talked about with the guys in backend
                audioSensor.sendSignal(); // TODO: and work with what they're wanting. give a parameter? enum?
                wearable.sendSignal();
            }
            @Override
            public String toString() {
                return "Sending triggering feed to the backend.";
            }
        });

        // notification expected from the back end to both application windows

        // stop workouts and pause
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // stop all animations
                Transitions.LiveTransitions.forEach(Animation::stop);
                Transitions.LiveTransitions.clear();
                // stop everyone from "working out"
                targetMember.setScaleX(1);
                targetMember.setScaleY(1);
                for (Node member : otherMembers.getChildren()) {
                    member.setScaleX(1);
                    member.setScaleY(1);
                } // end loop
            }
            @Override
            public String toString() {
                return "Instructor stops class";
            }
        });

        // member feels better
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.RelieveExhaustion(targetMember.getChildren().getFirst(), 4);
            }
            @Override
            public String toString() {
                return "Member's exhaustion is relieving.";
            }
        });

        // instructor continues class.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout(member, 1, 0.5);
                } // end loop

                Transitions.Workout(targetMember, 1, 0.5);
            }
            @Override
            public String toString() {
                return "Trigger movement cycle (class continues).";
            }
        });
    } // end method

    /**
     * remove out the third scenario frames
     */
    private void initScenario3() {

    } // end method

    /**
     * remove out the fourth scenario frames
     */
    private void initScenario4() {

    } // end method

    /**
     * remove out the fifth scenario frames
     */
    private void initScenario5() {

    } // end method

} // end class
