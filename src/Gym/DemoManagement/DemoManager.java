package Gym.DemoManagement;

import Gym.AgentGraphics.AgentGraphic;
import Gym.AgentGraphics.InstructorGraphic;
import Gym.AgentGraphics.MemberGraphic;
import Gym.Hardware.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
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

    /* ALL FRONT END COMPONENTS DEMO MANAGER USES DYNAMICALLY*/
    public AnchorPane targetMemberHouse;
    public Text houseChatBubble;
    public Text gymChatBubble;
    public AnchorPane entireGym;
    public MemberGraphic targetMemberInGym;
    public MemberGraphic targetMemberInHouse;
    public Shape houseDoorway;
    public InstructorGraphic targetInstructor;
    public HBox otherMembers;
    public Shape entryWay;
    public List<Hardware> targetHardware;

    /* CONSTRUCTOR */
    public DemoManager() {
        this.states = new ArrayList<>();
        this.currState = 0; // first state always
        init(); // init the manager
    }// end constructor

    /**
     * this method is to be called when the "next" or "start"
     * buttons are pressed.
     */
    public void next() {
        if (this.currState == 0) { resetStage(); } // reset all animated things to original state to enable looping
        if (!this.states.isEmpty()) {
            DemoState state = this.states.get(this.currState);
            this.currState = ((this.currState + 1) % this.states.size()); // for looping
            DemoLogger.update(state.toString());
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
        resetTransitionalObject(targetMemberInGym);
        resetTransitionalObject(targetMemberInHouse);
        resetTransitionalObject(targetInstructor);

        for (Node member : otherMembers.getChildren()) {
            resetTransitionalObject((AgentGraphic) member);
        } // end loop
        otherMembers.setVisible(true);

    } // end method

    /**
     * to avoid repetition of above code
     * @param graphic graphic to reset
     */
    private void resetTransitionalObject(AgentGraphic graphic) {
        graphic.setTranslateX(0);
        graphic.setTranslateY(0);
        graphic.setScaleX(1);
        graphic.setScaleY(1);
        graphic.root.setFill(graphic.baseColor);
        graphic.setVisible(true);
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
        initScenario5();
    } // end method

    /**
     * remove out the first scenario frames
     */
    private void initScenario1() {

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
                gymChatBubble.setText(
                        "ROXANNE: Oh god, I'm already late..."
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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: COME ON EVERYONE! LET'S GET SWEATY!!"
                );
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
                gymChatBubble.setText(
                        "ROXANNE: Those protein Doritos are like a rock in my stomach, ugh."
                );
            }
            @Override
            public String toString() {
                return "MemberDispatcher starts having exhaustion.";
            }
        });

//        // trigger the sending of some bad signals to the backend.
//        this.states.add(new DemoState() {
//            @Override
//            public void activate() {
//                sendBadSignals();
//            }
//            @Override public String toString() { return "Sending exhaustion signal to backend."; }
//        });

        // ---------- NEW: Send only the exhaustion signal ----------
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // Find the wearable of the target member (Jack Daniels = RKRAUSE1)
                // and send the over-exertion signal.
                for (Hardware h : targetHardware) {
                    if (h instanceof WearableSensors) {
                        WearableSensors ws = (WearableSensors) h;
                        if (ws.member.getId().equals("RKRAUSE1")) {
                            ws.setScenarioSignal("over_exertion");
                            ws.sendSignal();
                            break;
                        }
                    }
                }
            }
            @Override public String toString() { return "Sending exhaustion signal to backend."; }
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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Howdy y'all! Let's take a little break from getting sexy!"
                );
            }
            @Override
            public String toString() {
                return "InstructorDispatcher stops class";
            }
        });

        // member feels better
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.RelieveExhaustion(targetMemberInGym, 4);
                gymChatBubble.setText(
                        "ROXANNE: Ugh, thank god for the break. Starting to feel better already!"
                );
            }
            @Override
            public String toString() {
                return "MemberDispatcher's exhaustion is relieving.";
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

                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: ALRIGHT! Come on losers, let's get SWEATY!"
                );
            }
            @Override public String toString() {
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
                gymChatBubble.setText(
                        "OLD MAN JENKINS: You came in here reeking of doritos and SHAME.\n" +
                                "ROXANNE: My mother was filled with shame! How dare you!"
                );
            }
            @Override public String toString() {
                return "Conflict arises between two members.";
            }
        });

        // trigger the sending of some bad signals to the backend.
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // Use the first camera to send a conflict scene
                for (Hardware h : targetHardware) {
                    if (h instanceof Camera) {
                        ((Camera) h).setScenarioSignal("physical_altercation");
                        h.sendSignal();
                        break;   // only one camera needed
                    }
                }
            }
            @Override public String toString() {
                return "Sending conflict signal to backend.";
            }
        });

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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Hey now! That's not very sexy! Let's resolve like adults!"
                );
            }
            @Override public String toString() {
                return "Instructor stops class";
            }
        });

        // conflict deescalation
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Transitions.RelieveConflict(targetMemberInGym, (AgentGraphic) otherMembers.getChildren().getLast(), 5);
                gymChatBubble.setText(
                        "OLD MAN JENKINS: My bad, my wife left me last night.\n"
                            + "ROXANNE: No worries man, happens to the best of us."
                );
            }
            @Override public String toString() {
                return "Conflict deescalates between two members.";
            }
        });

        // resume class
        this.states.add(new DemoState() {
            @Override public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
                } // end loop

                Transitions.Workout(targetMemberInGym, 1, 0.5);

                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Alright guys, let's try this again!"
                );
            }
            @Override public String toString() {
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
                gymChatBubble.setText(
                        "ROXANNE: OH GOD! SHARP DORITO SHRAPNEL ENTERED MY AORTA."
                );
            }
            @Override
            public String toString() {
                return "Health emergency arises in member.";
            }
        });

        // ---------- NEW: Send only the health‑emergency signal ----------
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                for (Hardware h : targetHardware) {
                    if (h instanceof WearableSensors) {
                        WearableSensors ws = (WearableSensors) h;
                        if (ws.member.getId().equals("RKRAUSE1")) {   // target member
                            ws.setScenarioSignal("critical_hr_drop");
                            ws.sendSignal();
                            break;
                        }
                    }
                }
            }
            @Override public String toString() { return "Sending health emergency signal to backend."; }
        });

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

                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Everyone stop! Let me address the not-so-sexy situation!"
                );
            }
            @Override public String toString() {
                return "InstructorDispatcher stops class";
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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: I'm calling 911! DON'T GO TOWARD THE DORITO LIGHT."
                );
            }
            @Override
            public String toString() {
                return "InstructorDispatcher rushes to help, calls emergency services.";
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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Everyone stand back while this poor soul is carried to the hospital."
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
                gymChatBubble.setText(
                        "INSTRUCTOR JANE FONDA: Class dismissed--we've had enough sexy fun today!"
                );
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
        // class is done: instructor leaves, all other members leave
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                otherMembers.setVisible(false);
                targetInstructor.setVisible(false);
                gymChatBubble.setText(
                        "INSTRUCTOR JOHN TAYLOR: Something weird has been afoot in Jane's classes.\n"
                            + "I wonder what's been up in the last month..."
                );
            }
            @Override
            public String toString() {
                return "Other attendant is snooping on his coworker.";
            }
        });
    } // end method

} // end class
