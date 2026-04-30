package Gym.AgentGraphics;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TargetInstructorGraphic extends StackPane {

    public final double radius = 30;
    public final Color baseColor = Color.PURPLE;

    public TargetInstructorGraphic() {
        super();
        Circle circle = new Circle(radius);
        circle.setFill(baseColor);
        getChildren().add(circle);
    } // end constructor

} // end class
