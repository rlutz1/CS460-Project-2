package Gym.DemoManagement;

import Gym.AgentGraphics.AgentGraphic;
import Gym.AgentGraphics.InstructorGraphic;
import Gym.AgentGraphics.MemberGraphic;
import Gym.Hardware.Hardware;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

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
    public AnchorPane targetMemberHouse;
    public Text houseChatBubble;
    public AnchorPane entireGym;
    public MemberGraphic targetMemberInGym;
    public MemberGraphic targetMemberInHouse;
    public Shape houseDoorway;
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

        // reset the original scene setup.
        targetMemberHouse.setVisible(true);
        entireGym.setVisible(false);
    } // end mehtod

    /**
     * method to hold the hard reset of any and all objects that have
     * a transition at ANY point. add any and all here.
     */
    private void resetAllTransitionalObjects() {
        targetMemberInGym.setTranslateX(0);
        targetMemberInGym.setTranslateY(0);
        targetMemberInGym.setScaleX(1);
        targetMemberInGym.setScaleY(1);
        targetMemberInGym.root.setFill(targetMemberInGym.baseColor);
        targetMemberInGym.setVisible(true);

        targetMemberInHouse.setTranslateX(0);
        targetMemberInHouse.setTranslateY(0);
        targetMemberInHouse.setScaleX(1);
        targetMemberInHouse.setScaleY(1);
        targetMemberInHouse.root.setFill(targetMemberInHouse.baseColor);
        targetMemberInHouse.setVisible(true);

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
        initScenario1();
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
        // enter the shame zone
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // just to be sure correct scene is active, but it should be.
                targetMemberHouse.setVisible(true);
                entireGym.setVisible(false);

                // simple text bubble for now, if time, make prettier
                houseChatBubble.setText(
                        "Ugh, I've been here for hours, covered in Protein Dorito crumbs.\n" +
                        "Maybe I should at least do something at home..."
                        );
            }
            @Override
            public String toString() {
                return "Loser at home, spur a recommendation request.";
            }
        });

        // control goes over to the applications

        // you know what, screw it, go to the gym
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // simple text bubble for now, if time, make prettier
                houseChatBubble.setText(
                        "Come on, dawg, you're better than this.\n" +
                        "Dust off the crumbs and get to the gym."
                );
            }
            @Override
            public String toString() {
                return "Loser decides to stop being a loser.";
            }
        });

        // transition to going to the gym
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // empty the chat
                houseChatBubble.setText("");

                // transition him out the house.
                Transitions.MoveWithExit(
                        targetMemberInHouse,
                        3,
                        houseDoorway.getLayoutX() - targetMemberInHouse.getLayoutX() - 50,
                        houseDoorway.getLayoutY() - targetMemberInHouse.getLayoutY() + 50,
                        false,
                        'y'
                );
            }
            @Override
            public String toString() {
                return "Loser decides to stop being a loser.";
            }
        });


        // transition to scenario 2
    } // end method

    /**
     * remove out the second scenario frames
     */
    private void initScenario2() {
        // set up the gym scene
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                targetMemberHouse.setVisible(false);
                entireGym.setVisible(true);
            }
            @Override
            public String toString() {
                return "Target arrives at gym.";
            }
        });

        // move the target in the classroom
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.Move(
                        targetMemberInGym,
                        2,
                        // TODO: this should be based upon distance away
                        otherMembers.getLayoutX() + otherMembers.getWidth() - targetMemberInGym.width,
                        - otherMembers.getLayoutY() - otherMembers.getHeight(),
                        true,
                        'x'
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

                Transitions.Workout(targetMemberInGym, 1, 0.5);
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
                Transitions.TriggerExhaustion(targetMemberInGym, 4);
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
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
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
                Transitions.RelieveExhaustion(targetMemberInGym, 4);
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

                Transitions.Workout(targetMemberInGym, 1, 0.5);
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
                Transitions.TriggerConflict(targetMemberInGym, (AgentGraphic) otherMembers.getChildren().getLast(), 2);
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
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
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
                Transitions.RelieveConflict(targetMemberInGym, (AgentGraphic) otherMembers.getChildren().getLast(), 5);
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

                Transitions.Workout(targetMemberInGym, 1, 0.5);
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
                Transitions.TriggerHealthEmergency(targetMemberInGym, 1);
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
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
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
                Transitions.MoveWithExit(
                        targetMemberInGym,
                        2,
                        -20, // TODO: again, funky layout stuff that needs fixing
                        20,
                        true,
                        'y'
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
