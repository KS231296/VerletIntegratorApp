package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animation {

    private int x;
    private int y;
    private int durationtime;

    public void drawPendulum(AnchorPane pane, int Xposition, int Yposition, int AnimationDurationTime) {

        /**
         * By dodać animacje wystarczy np w controllerze wpisac np .  new PendulumAnimation().drawPendulum(pane,750,250 ,6);
         *
         *
         * */

        this.x = Xposition;
        this.y = Yposition;
        this.durationtime = AnimationDurationTime;

        Circle circle = new Circle();
        Rectangle rectangle1 = new Rectangle(x + 100, y, 1, 100);
        circle.setRadius(8);
        circle.setFill(Color.BLACK);
        circle.setLayoutY(y);
        circle.setLayoutX(x);

        CircleAnimation(circle);
        LineAnimation(rectangle1);

        pane.getChildren().add(circle);
        pane.getChildren().add(rectangle1);

    }


    private void CircleAnimation(Circle circle) {

        Rotate rotation = new Rotate();
        rotation.setPivotX(100);
        rotation.setPivotY(0);
        circle.getTransforms().add(rotation);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(durationtime), new KeyValue(rotation.angleProperty(), -180)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

    }

    private void LineAnimation(Rectangle rectangle) {

        Rotate rotation = new Rotate();
        rotation.setPivotX(x + 100);
        rotation.setPivotY(y);
        rectangle.getTransforms().add(rotation);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 90)),
                new KeyFrame(Duration.seconds(durationtime), new KeyValue(rotation.angleProperty(), -90)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

}
