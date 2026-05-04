package Gym.DemoManagement;

import Gym.AgentGraphics.AgentGraphic;
import Gym.AgentGraphics.InstructorGraphic;
import Gym.AgentGraphics.MemberGraphic;
import Gym.Hardware.*;
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
 * encapsulation of running the demo away from the
 * physical gym representation.
 */
public class DemoManager {

    private final List<DemoState> states;
    private int currState;

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
        for (Transition anim : Transitions.LiveTransitions) {
            anim.stop();
        }
        Transitions.LiveTransitions.clear();
        resetAllTransitionalObjects();
        targetMemberHouse.setVisible(true);
        entireGym.setVisible(false);
    }

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
        }
        otherMembers.setVisible(true);

        targetInstructor.setTranslateX(0);
        targetInstructor.setTranslateY(0);
        targetInstructor.setScaleX(1);
        targetInstructor.setScaleY(1);
        targetInstructor.root.setFill(targetInstructor.baseColor);
        targetInstructor.setVisible(true);
    }

    private void init() {
        initScenario1();
        initScenario2();
        initScenario3();
        initScenario4();
    }

    private void initScenario1() {
        // ... (unchanged, same as before)
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                targetMemberHouse.setVisible(true);
                entireGym.setVisible(false);
                houseChatBubble.setText(
                        "Ugh, I've been here for hours, covered in Protein Dorito crumbs.\n" +
                                "Maybe I should at least do something at home..."
                );
            }
            @Override public String toString() { return "Loser at home, spur a recommendation request."; }
        });

        this.states.add(new DemoState() {
            @Override
            public void activate() {
                houseChatBubble.setText(
                        "Come on, dawg, you're better than this.\n" +
                                "Dust off the crumbs and get to the gym."
                );
            }
            @Override public String toString() { return "Loser decides to stop being a loser."; }
        });

        this.states.add(new DemoState() {
            @Override
            public void activate() {
                houseChatBubble.setText("");
                Transitions.MoveWithExit(
                        targetMemberInHouse,
                        3,
                        houseDoorway.getLayoutX() - targetMemberInHouse.getLayoutX() - 50,
                        houseDoorway.getLayoutY() - targetMemberInHouse.getLayoutY() + 50,
                        false,
                        'y'
                );
            }
            @Override public String toString() { return "Loser leaves house."; }
        });
    }

    private void initScenario2() {
        // set up gym scene
        this.states.add(new DemoState() {
            @Override public void activate() {
                targetMemberHouse.setVisible(false);
                entireGym.setVisible(true);
            }
            @Override public String toString() { return "Target arrives at gym."; }
        });

        // move target into classroom
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.Move(
                        targetMemberInGym,
                        2,
                        otherMembers.getLayoutX() + otherMembers.getWidth() - targetMemberInGym.width,
                        - otherMembers.getLayoutY() - otherMembers.getHeight(),
                        true,
                        'x'
                );
            }
            @Override public String toString() { return "Target moving into classroom"; }
        });

        // everyone working out
        this.states.add(new DemoState() {
            @Override public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
                }
                Transitions.Workout(targetMemberInGym, 1, 0.5);
            }
            @Override public String toString() { return "Trigger movement cycle (class begins)."; }
        });

        // exhaustion visual
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.TriggerExhaustion(targetMemberInGym, 4);
            }
            @Override public String toString() { return "Member starts having exhaustion."; }
        });

        // ---------- NEW: Send only the exhaustion signal ----------
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                // Find the wearable of the target member (Jack Daniels = JDANIELS1)
                // and send the over-exertion signal.
                for (Hardware h : targetHardware) {
                    if (h instanceof WearableSensors) {
                        WearableSensors ws = (WearableSensors) h;
                        if (ws.member.getId().equals("JDANIELS1")) {
                            ws.setScenarioSignal("over_exertion");
                            ws.sendSignal();
                            break;
                        }
                    }
                }
            }
            @Override public String toString() { return "Sending exhaustion signal to backend."; }
        });

        // stop workouts
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.LiveTransitions.forEach(Animation::stop);
                Transitions.LiveTransitions.clear();
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
                for (Node member : otherMembers.getChildren()) {
                    member.setScaleX(1);
                    member.setScaleY(1);
                }
            }
            @Override public String toString() { return "Instructor stops class"; }
        });

        // relieve exhaustion
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.RelieveExhaustion(targetMemberInGym, 4);
            }
            @Override public String toString() { return "Member's exhaustion is relieving."; }
        });

        // resume class
        this.states.add(new DemoState() {
            @Override public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
                }
                Transitions.Workout(targetMemberInGym, 1, 0.5);
            }
            @Override public String toString() { return "Trigger movement cycle (class continues)."; }
        });
    }

    private void initScenario3() {
        // conflict animation
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.TriggerConflict(targetMemberInGym, (AgentGraphic) otherMembers.getChildren().getLast(), 2);
            }
            @Override public String toString() { return "Conflict arises between two members."; }
        });

        //  Send only the conflict signal
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
            @Override public String toString() { return "Sending conflict signal to backend."; }
        });

        // stop class
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.LiveTransitions.forEach(Animation::stop);
                Transitions.LiveTransitions.clear();
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
                for (Node member : otherMembers.getChildren()) {
                    member.setScaleX(1);
                    member.setScaleY(1);
                }
            }
            @Override public String toString() { return "Instructor stops class"; }
        });

        // de-escalation
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.RelieveConflict(targetMemberInGym, (AgentGraphic) otherMembers.getChildren().getLast(), 5);
            }
            @Override public String toString() { return "Conflict deescalates between two members."; }
        });

        // resume class
        this.states.add(new DemoState() {
            @Override public void activate() {
                for (Node member : otherMembers.getChildren()) {
                    Transitions.Workout((AgentGraphic) member, 1, 0.5);
                }
                Transitions.Workout(targetMemberInGym, 1, 0.5);
            }
            @Override public String toString() { return "Trigger movement cycle (class continues)."; }
        });
    }

    private void initScenario4() {
        // health emergency animation
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.TriggerHealthEmergency(targetMemberInGym, 1);
            }
            @Override public String toString() { return "Health emergency arises in a member."; }
        });

        // ---------- NEW: Send only the health‑emergency signal ----------
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                for (Hardware h : targetHardware) {
                    if (h instanceof WearableSensors) {
                        WearableSensors ws = (WearableSensors) h;
                        if (ws.member.getId().equals("JDANIELS1")) {   // target member
                            ws.setScenarioSignal("critical_hr_drop");
                            ws.sendSignal();
                            break;
                        }
                    }
                }
            }
            @Override public String toString() { return "Sending health emergency signal to backend."; }
        });

        // stop class
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.LiveTransitions.forEach(Animation::stop);
                Transitions.LiveTransitions.clear();
                targetMemberInGym.setScaleX(1);
                targetMemberInGym.setScaleY(1);
                for (Node member : otherMembers.getChildren()) {
                    member.setScaleX(1);
                    member.setScaleY(1);
                }
            }
            @Override public String toString() { return "Instructor stops class"; }
        });

        // instructor rushes to help
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.InstructorToHelp(targetInstructor, 0.5, 150, 70);
            }
            @Override public String toString() { return "Instructor rushes to help, calls emergency services."; }
        });

        // member taken away
        this.states.add(new DemoState() {
            @Override public void activate() {
                Transitions.MoveWithExit(targetMemberInGym, 2, -20, 20, true, 'y');
            }
            @Override public String toString() { return "Sick member whisked from classroom"; }
        });

        // class dismissed
        this.states.add(new DemoState() {
            @Override public void activate() {
                otherMembers.setVisible(false);
                targetInstructor.setVisible(false);
            }
            @Override public String toString() { return "Everyone leaves, class dismissed."; }
        });
    }

   // Took out the old sendBadSignals()
}
