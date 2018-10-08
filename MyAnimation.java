package javafxprogramme;

import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MyAnimation extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Rectangle rect = new Rectangle(10, 10, 25, 25);

		// Create the KeyValue for the xProperty and value of 300px
		KeyValue keyValuex = new KeyValue(rect.xProperty(), 400,
				Interpolator.EASE_BOTH);
		KeyValue keyValuey = new KeyValue(rect.yProperty(), 300,
				Interpolator.EASE_BOTH);		
		
		 Random random = new Random();
		 KeyValue kVRotate = new KeyValue(rect.rotateProperty(), random.nextInt(360) + 180);
	     KeyValue kVArcHeight = new KeyValue(rect.arcHeightProperty(), 60);
	     KeyValue kVArcWidth = new KeyValue(rect.arcWidthProperty(), 60);
		
		
		// Create the KeyFrame with the KeyValue and the time of 2 sec
		KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), keyValuex, keyValuey, kVRotate);

		// Create the timeline and add the KeyFrame
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(keyFrame);

		// Add the rectangle to a layout container and create a scene
		Pane pane = new Pane();
		pane.getChildren().add(rect);

		Scene scene = new Scene(pane, 800, 600);

		primaryStage.setTitle("Animation");
		primaryStage.setScene(scene);
		primaryStage.show();

		// Play the timeline
		timeline.play();

	}
}
