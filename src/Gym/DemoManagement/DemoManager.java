package Gym.DemoManagement;

import Gym.AgentGraphics.AgentGraphic;
import Gym.AgentGraphics.InstructorGraphic;
import Gym.AgentGraphics.MemberGraphic;
import Gym.Hardware.AudioSensor;
import Gym.Hardware.Camera;
import Gym.Hardware.Hardware;
import Gym.Hardware.WearableSensors;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

//    public StackPane mainStage;
    public MemberGraphic targetMember;
    public InstructorGraphic targetInstructor;
    public HBox otherMembers;
    public Shape entryWay;
    public List<Hardware> targetHardware;

    // for testing and sending basic crap to backend
    // this may change to be more representative of the scenario, environment.
//    public AudioSensor audioSensor;
//    public Camera cameraFeed;
//    public WearableSensors wearable;

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
        if (this.currState == 0) { resetStage(); } // reset all animated things to original state to enable looping
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
        resetStage();
    } // end method

    /**
     * method to
     * 1. stop all animations.
     * 2. reset all animated objects to original state.
     */
    private void resetStage() {
        // stop all current animations
        for  (Transition anim : Transitions.LiveTransitions) {
            anim.stop();
        } // end loop
        Transitions.LiveTransitions.clear();

        resetAllTransitionalObjects(); // reset all the animated objects
    } // end mehtod

    /**
     * method to hold the hard reset of any and all objects that have
     * a transition at ANY point. add any and all here.
     */
    private void resetAllTransitionalObjects() {
        targetMember.setTranslateX(0);
        targetMember.setTranslateY(0);
        targetMember.setScaleX(1);
        targetMember.setScaleY(1);
        targetMember.root.setFill(targetMember.baseColor);
        targetMember.setVisible(true);

        for (Node member : otherMembers.getChildren()) {
            member.setTranslateX(0);
            member.setTranslateY(0);
            member.setScaleX(1);
            member.setScaleY(1);
            ((MemberGraphic)member).root.setFill(((AgentGraphic)member).baseColor);
            member.setVisible(true);
        } // end loop
        otherMembers.setVisible(true);

        targetInstructor.setTranslateX(0);
        targetInstructor.setTranslateY(0);
        targetInstructor.setScaleX(1);
        targetInstructor.setScaleY(1);
        targetInstructor.root.setFill(targetInstructor.baseColor);
        targetInstructor.setVisible(true);
    } // end method

    /**
     * this is going to be incredibly hardcoded, but testing.
     * initialize the demo state list.
     */
    private void init() {
//        initScenario1();
        initScenario2();
        initScenario3();
        initScenario4();
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
                List<Node> members = otherMembers.getChildren();
                Transitions.EnterClassroom(
                        targetMember,
                        2,
                        // TODO: this should be based upon distance away
                        otherMembers.getLayoutX() + otherMembers.getWidth() - targetMember.width,
                        - otherMembers.getLayoutY() - otherMembers.getHeight()
                );
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
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
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
                Transitions.TriggerExhaustion(targetMember, 4);
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
                sendBadSignals();
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
                Transitions.RelieveExhaustion(targetMember, 4);
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
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
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
        // a conflict arises between 2 members.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.TriggerConflict(targetMember, (AgentGraphic) otherMembers.getChildren().getLast(), 2);
            }
            @Override
            public String toString() {
                return "Conflict arises between two members.";
            }
        });

        // trigger the sending of some bad signals to the backend.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                sendBadSignals();
            }
            @Override
            public String toString() {
                return "Sending triggering feed to the backend.";
            }
        });

        // notification expected from the back end to both application windows

        // stop workouts and pause, instructor attempting to resolve conflict
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

        // conflict deescalation
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.RelieveConflict(targetMember, (AgentGraphic) otherMembers.getChildren().getLast(), 5);
            }
            @Override
            public String toString() {
                return "Conflict deescalates between two members.";
            }
        });

        // instructor continues class.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
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
     * remove out the fourth scenario frames
     */
    private void initScenario4() {
        // a health emergency arises between a member.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.TriggerHealthEmergency(targetMember, 1);
            }
            @Override
            public String toString() {
                return "Health emergency arises in a member members.";
            }
        });

        // trigger the sending of some bad signals to the backend.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                sendBadSignals();
            }
            @Override
            public String toString() {
                return "Sending triggering feed to the backend.";
            }
        });

        // notification expected from the back end to both application windows

        // instructor stops workouts
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

        // instructor attends to the collapsed member, calling emergency services
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.InstructorToHelp(
                        targetInstructor,
                        0.5,
                        150, // TODO: funky layout positions, hardcoded for now.
                        70
                );
            }
            @Override
            public String toString() {
                return "Instructor rushes to help, calls emergency services.";
            }
        });

        // services arrive, take member away
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.ExitClassroom(
                        targetMember,
                        2,
                        -20, // TODO: again, funky layout stuff that needs fixing
                        20
                );
            }
            @Override
            public String toString() {
                return "Sick member whisked from classroom";
            }
        });

        // class is done: instructor leaves, all other members leave
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                otherMembers.setVisible(false);
                targetInstructor.setVisible(false);
            }
            @Override
            public String toString() {
                return "Everyone leaves, class dismissed.";
            }
        });

    } // end method

    /**
     * remove out the fifth scenario frames
     */
    private void initScenario5() {

    } // end method

    private void sendBadSignals() {
        for (Hardware hardware : targetHardware) {
            hardware.sendSignal();
        } // end method
    } // end method

} // end class
