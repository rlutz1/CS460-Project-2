package Gym.AgentGraphics;

import GSMS.Common.AgentId;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class InstructorGraphic extends AgentGraphic {

    public InstructorGraphic(AgentId id, Color color) {
        super();
        root = new Circle(width);
        root.setStroke(Color.BLACK);
        baseColor = color;
        root.setFill(baseColor);
        getChildren().add(root);
        this.id = id;
    } // end constructor

} // end class
