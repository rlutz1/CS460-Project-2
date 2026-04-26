package Gym.DemoManagement;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

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

//    @FXML
    public StackPane mainStage;

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
        this.states.add(new DemoState() {
            @Override
            public void activate() {
                Actions.test();
                System.out.println(mainStage);
            }
            @Override
            public String toString() {
                return "Testing state here!";
            }
        });
    } // end method

} // end class
