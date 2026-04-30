package Gym.AgentGraphics;

import GSMS.Common.AgentId;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MemberGraphic extends AgentGraphic {

    public MemberGraphic(AgentId id, Color color) {
        super();
        root = new Circle(width);
        baseColor = color;
        root.setFill(baseColor);
        getChildren().add(root);
        this.id = id;
    } // end constructor

} // end class
