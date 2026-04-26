package Gym.DemoManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * this guy manages demo states and allows for more
 * encapsualtion of running the demo away from the
 * physical gym representation.
 */
public class DemoManager {

    private List<DemoState> states; // list of frames, or states for demo
    private int currState;

    public DemoManager() {
        this.states = new ArrayList<>();
        this.currState = 0; // first state always
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

} // end class
