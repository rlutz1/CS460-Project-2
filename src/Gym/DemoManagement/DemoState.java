package Gym.DemoManagement;

/**
 * representative of a state to switch to in the demo control
 * panel.
 *
 * an anonymous class to allow for dynamic method assignment.
 */
interface DemoState {
    void activate(); // implement depending on frame being used.
    String toString(); // for printing and sanity.
} // end class
