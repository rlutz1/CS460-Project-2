package Gym.AgentGraphics;

import GSMS.Common.AgentId;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TargetInstructorGraphic extends StackPane {

    public final double radius = 25;
    public final Color baseColor = Color.PURPLE;
    public AgentId id;

    public TargetInstructorGraphic(AgentId id) {
        super();
        Circle circle = new Circle(radius);
        circle.setFill(baseColor);
        getChildren().add(circle);
        this.id = id;
    } // end constructor

} // end class
