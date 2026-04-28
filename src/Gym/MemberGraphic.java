package Gym;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class MemberGraphic extends StackPane {

    public MemberGraphic() {
        super();
        Circle circle = new Circle(50);
        circle.setFill(Color.BLUE);
        getChildren().add(circle);
    } // end constructor

} // end class
