package Gym.AgentGraphics;

import GSMS.Common.AgentId;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * defaults for the agent graphics
 */
public abstract class AgentGraphic extends StackPane {

    public final double height = 25;
    public final double width = 25;
    public Color baseColor = Color.GREEN;
    public AgentId id = null;
    public Shape root = new Circle(height);

} // end class
