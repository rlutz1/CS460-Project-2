package Gym.AgentGraphics;

import GSMS.Common.AgentId;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class TargetMemberGraphic extends StackPane {

    public final double radius = 25;
    public final Color baseColor = Color.BLUE;
    public AgentId id;
    public Shape root;

    public TargetMemberGraphic(AgentId id) {
        super();
        root = new Circle(radius);
        root.setFill(baseColor);
        getChildren().add(root);
        this.id = id;
    } // end constructor

} // end class
