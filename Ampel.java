package javafxprogramme;

import eth.MyGame;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Ampel extends Application {

	public Circle circle_green = new Circle(); 
	public Circle circle_yellow = new Circle(); 
	public Circle circle_red = new Circle(); 
	public int displayX = 800;
	public int displayY = 600;
	
	public static void main(String[] args) {
        launch(args);
    }	
	
	 @Override
	    public void start(Stage primaryStage) {
		 
			// Displayauflösung ermitteln
			displayX = (int) Screen.getPrimary().getVisualBounds().getWidth();
			displayY = (int) Screen.getPrimary().getVisualBounds().getHeight();
			
			System.out.println("X: " + displayX + " Y: " + displayY);
		 
			// Ampelknöpfe definieren
	        circle_green.setFill(Color.GREEN);
	        circle_green.setCenterX(displayX/2);
	        circle_green.setCenterY((0*displayY)+((displayY/3)/2));
	        circle_green.setRadius(displayY/10);
	        circle_green.setStroke(Color.BLACK);
	        
	        circle_yellow.setFill(Color.YELLOW);
	        circle_yellow.setCenterX(displayX/2);
	        circle_yellow.setCenterY((1*displayY)+((displayY/3)/2));
	        circle_yellow.setRadius(displayY/10);
	        circle_yellow.setStroke(Color.BLACK);
	        
	        
	        circle_red.setFill(Color.RED);
	        circle_red.setCenterX(displayX/2);
	        circle_red.setCenterY((2*displayY)+((displayY/3)/2));
	        circle_red.setRadius(displayY/10);
	        circle_red.setStroke(Color.BLACK);
	        
	        VBox vbox = new VBox();
	        vbox.setMinSize(displayX, displayY);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.setSpacing(((displayY/3)/4));
	        vbox.setStyle("-fx-background-color: BLACK;");
	        vbox.getChildren().addAll(circle_green, circle_yellow, circle_red);
	        Scene scene = new Scene(vbox, displayX, displayY);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Ampel");
	        primaryStage.show();
 	        
	 
	 
	    //Circle Klick und Touch Effekte
	    circle_green.onMouseClickedProperty()
		.set(new EventHandler<MouseEvent>()
		        {
		          @Override
		          public void handle(MouseEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: GREEN;");
		          }
		 });
	    
	    circle_green.onTouchPressedProperty()
		.set(new EventHandler<TouchEvent>()
		        {
		          @Override
		          public void handle(TouchEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: GREEN;");
		          }
		 });
	    
	    circle_yellow.onMouseClickedProperty()
		.set(new EventHandler<MouseEvent>()
		        {
		          @Override
		          public void handle(MouseEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: YELLOW;");
		          }
		 });
	    
	    circle_yellow.onTouchPressedProperty()
		.set(new EventHandler<TouchEvent>()
		        {
		          @Override
		          public void handle(TouchEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: YELLOW;");
		          }
		 });
	    
	    circle_red.onMouseClickedProperty()
		.set(new EventHandler<MouseEvent>()
		        {
		          @Override
		          public void handle(MouseEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: RED;");
		          }
		 });

	    circle_red.onTouchPressedProperty()
		.set(new EventHandler<TouchEvent>()
		        {
		          @Override
		          public void handle(TouchEvent arg0)
		          {
		        	  vbox.setStyle("-fx-background-color: RED;");
		          }
		 });
	 
	 
	 }
}
