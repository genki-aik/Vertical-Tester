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
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VerticalTester extends Application {
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
		
		
		
		
		Media media = new Media(new File(path).toURI().toString());

		MediaPlayer mediaPlayer = new MediaPlayer(media);
		
		MediaView mediaView = new MediaView(mediaPlayer);
		
		DoubleProperty width = mediaView.fitWidthProperty();
		DoubleProperty height = mediaView.fitHeightProperty();
		
		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		Button startButton = new Button("First Jump");
		Button play = new Button("PLAY");
		Button pause = new Button("PAUSE");
		
		startButton.setOnAction(e -> {
			System.out.println(mediaPlayer.getCurrentTime());
		});
		
		play.setOnAction(e -> mediaPlayer.play());
		
		pause.setOnAction(e -> mediaPlayer.pause());
			
		
		mediaPlayer.setAutoPlay(true);
		
		//Group root = new Group();
		StackPane root = new StackPane();
		StackPane.setAlignment(startButton, Pos.TOP_LEFT);
		StackPane.setAlignment(play, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(pause, Pos.BOTTOM_RIGHT);
		root.getChildren().addAll(mediaView, startButton, play, pause);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Playing Video");
		primaryStage.show();
		
	}
	
}
