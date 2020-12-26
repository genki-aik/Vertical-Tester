package application;
	
import java.io.File; 
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VerticalTester extends Application {
	
	double start;
	double stop;
	double hangTime;
	double vertical;
	
	Media media;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	
	Controls controls = new Controls();
	
	@Override
	public void start(Stage primaryStage) {
		String path = "/Users/genki/Downloads/ShortVideo.mp4";
		
		/*
		StackPane root = new StackPane();
		
		MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("ShortVideo.mp4").toExternalForm()));
		MediaView mediaView = new MediaView(player);
		
		root.getChildren().add(mediaView);
		
		Scene scene = new Scene(root, 1024, 768);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		player.play();
		*/
		
		// TODO Add sliders
		// TODO Equation to calculate vertical with time in air
		// TODO Think about design layout
		// Vertical Jump Height = 0.5 * 9.81 m/s^2 * (hang time / 2) ^2
		// d = Vi * t + (1/2) * a * t^2
		
		media = new Media(new File(path).toURI().toString());

		mediaPlayer = new MediaPlayer(media);
		
		mediaView = new MediaView(mediaPlayer);
		
		DoubleProperty width = mediaView.fitWidthProperty();
		DoubleProperty height = mediaView.fitHeightProperty();
		
		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		/*
		Button startButton = new Button("First Jump");
		Button stopButton = new Button("Feet lands");
		Button calculate = new Button("Calculate Vert!");
		Button play = new Button("PLAY");
		Button pause = new Button("PAUSE");
		
		startButton.setOnAction(e -> {
			System.out.println("Take off: " + mediaPlayer.getCurrentTime().toSeconds());
			start = mediaPlayer.getCurrentTime().toSeconds();
		});
		
		stopButton.setOnAction(e -> {
			System.out.println("Feet Lands: " + mediaPlayer.getCurrentTime().toSeconds());
			stop = mediaPlayer.getCurrentTime().toSeconds();
		});
		
		calculate.setOnAction(e -> {
			hangTime = stop - start;
			vertical = 0.5 * 9.81 * Math.pow((hangTime / 2), 2);
			vertical = vertical * 39.37;
			System.out.printf("%.2f", vertical);
			System.out.println(" inches");
		});
		
		play.setOnAction(e -> mediaPlayer.play());
		
		pause.setOnAction(e -> mediaPlayer.pause());
		*/
		
		mediaPlayer.setAutoPlay(true);
		buttonHandlers();
		
		//Group root = new Group();
		StackPane root = new StackPane();
		/*
		StackPane.setAlignment(startButton, Pos.TOP_LEFT);
		StackPane.setAlignment(play, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(pause, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(stopButton, Pos.TOP_RIGHT);
		root.getChildren().addAll(mediaView, startButton, play, pause, stopButton, calculate);
		*/
		StackPane.setAlignment(controls, Pos.BOTTOM_CENTER);
		root.getChildren().addAll(mediaView, controls);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Playing Video");
		primaryStage.show();
		
	} // start
	
	private class Controls extends HBox {
		//HBox controlBox = new HBox(5);
		
		Button startButton;
		Button stopButton;
		Button calculate;
		Button play;
		Button pause;
		
		public Controls() {
			super();
			startButton = new Button("Take Off");
			stopButton = new Button("Feet Lands");
			calculate = new Button("Calculate Vertical");
			play = new Button("PLAY");
			pause = new Button("PAUSE");
			
			//buttonHandlers();
			
			this.getChildren().addAll(startButton, stopButton, calculate, play, pause);
			this.setAlignment(Pos.BOTTOM_CENTER);
		}
	}
	
	private void buttonHandlers() {
		controls.startButton.setOnAction(e -> {
			System.out.println("Take off: " + mediaPlayer.getCurrentTime().toSeconds());
			start = mediaPlayer.getCurrentTime().toSeconds();
		});
		
		controls.stopButton.setOnAction(e -> {
			System.out.println("Feet Lands: " + mediaPlayer.getCurrentTime().toSeconds());
			stop = mediaPlayer.getCurrentTime().toSeconds();
		});
		
		controls.calculate.setOnAction(e -> {
			hangTime = stop - start;
			vertical = 0.5 * 9.81 * Math.pow((hangTime / 2), 2);
			vertical = vertical * 39.37;
			System.out.printf("%.2f", vertical);
			System.out.println(" inches");
		});
		
		controls.play.setOnAction(e -> mediaPlayer.play());
		
		controls.pause.setOnAction(e -> mediaPlayer.pause());
	}
	
}
