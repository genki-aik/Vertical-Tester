package application;
	
import java.io.File; 
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VerticalTester extends Application {
	
	double start;
	double stop;
	double hangTime;
	double vertical;
	double duration;
	
	Media media;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	
	//Slider slider;
	
	Controls controls = new Controls();
	
	@Override
	public void start(Stage primaryStage) {
		String path = "/Users/genki/Downloads/VerticalTest3.mp4";
		
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
		
		/*
		DoubleProperty width = mediaView.fitWidthProperty();
		DoubleProperty height = mediaView.fitHeightProperty();
		
		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		*/
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
		
		
		duration = media.getDuration().toSeconds();
		/*
		slider = new Slider(0, duration, 1);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		*/
		System.out.println(duration);
		Slider slider = new Slider();
		HBox.setHgrow(slider, Priority.ALWAYS);
		slider.setMinWidth(50);
		slider.setMaxWidth(Double.MAX_VALUE);
		
		Label playTime = new Label();
		playTime.setPrefWidth(130);
		playTime.setMinWidth(50);
		
		//slider.setMin(0);
		//slider.setMax(media.getDuration().toSeconds());
		
		/*
		mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
			if (!slider.isValueChanging()) {
				slider.setValue(newTime.toSeconds());
			}
		});
		*/
		
		InvalidationListener sliderChangeListener = o -> {
			Duration seekTo = Duration.seconds(slider.getValue());
			mediaPlayer.seek(seekTo);
		};
		slider.valueProperty().addListener(sliderChangeListener);
		
		mediaPlayer.currentTimeProperty().addListener(l -> {
			slider.valueProperty().removeListener(sliderChangeListener);
			
			Duration currentTime = mediaPlayer.getCurrentTime();
			double value = currentTime.toSeconds();
			slider.setValue(value);
			
			slider.valueProperty().addListener(sliderChangeListener);
		});
		
		slider.maxProperty().bind(Bindings.createDoubleBinding(
				() -> mediaPlayer.getTotalDuration().toSeconds(),
				mediaPlayer.totalDurationProperty()));
		
		//Group root = new Group();
		BorderPane root = new BorderPane();
		Pane pane = new Pane();
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		//StackPane root = new StackPane();
		//VBox vbox = new VBox(3);
		/*
		StackPane.setAlignment(startButton, Pos.TOP_LEFT);
		StackPane.setAlignment(play, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(pause, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(stopButton, Pos.TOP_RIGHT);
		root.getChildren().addAll(mediaView, startButton, play, pause, stopButton, calculate);
		*/
		//StackPane.setAlignment(controls, Pos.BOTTOM_CENTER);
		//StackPane.setAlignment(slider,  Pos.TOP_CENTER);

		//root.getChildren().addAll(mediaView, controls);
		pane.getChildren().add(mediaView);
		pane.setStyle("-fx-background-color: black;");
		hbox.getChildren().addAll(slider, playTime);
		vbox.getChildren().addAll(hbox, controls);
		root.setCenter(pane);
		root.setBottom(vbox);
		
		//vbox.getChildren().addAll(mediaView, controls);
		Scene scene = new Scene(root, 600, 600);
		
		//Scene scene = new Scene(vbox, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Playing Video");
		primaryStage.show();
		
	} // start
	
	private class Controls extends HBox {
		//HBox controlBox = new HBox(5);
		//Slider slider = new Slider();
		
		Button startButton;
		Button stopButton;
		Button calculate;
		Button play;
		Button pause;
		Button forward;
		Button backward;
		
		//Slider slider;
		
		public Controls() {
			super(5);
			startButton = new Button("Take Off");
			stopButton = new Button("Feet Lands");
			calculate = new Button("Calculate Vertical");
			play = new Button(">");
			pause = new Button("||");
			forward = new Button(">>");
			backward = new Button("<<");
			
			
			this.getChildren().addAll(startButton, stopButton, calculate, play, pause, backward, forward);
			this.setAlignment(Pos.CENTER);
		}
	} // Controls
	
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
			System.out.printf("Vertical: %.2f", vertical);
			System.out.println(" inches");
			System.out.printf("Hang Time: %.2f", hangTime);
			System.out.println(" seconds");
		});
		
		controls.play.setOnAction(e -> mediaPlayer.play());
		
		controls.pause.setOnAction(e -> mediaPlayer.pause());
		
		controls.forward.setOnAction(e -> {
			Duration fastForward = mediaPlayer.getCurrentTime();
			mediaPlayer.seek(fastForward.add(new Duration(50)));
		});
		
		controls.backward.setOnAction(e -> {
			Duration rewind = mediaPlayer.getCurrentTime();
			mediaPlayer.seek(rewind.subtract(new Duration(50)));
		});
	}
	
	private static String formatTime(Duration elapsed, Duration duration) {
		int elapsedTime = (int) Math.floor(elapsed.toSeconds());
		
		//Calculates how many hours elapsed
		int elapsedHours = elapsedTime / (60 * 60);
		
		if (elapsedHours > 0) {
			//Subtract hours from elapsedTime to display correct minutes later on
			elapsedTime = elapsedTime - elapsedHours * 60 * 60;
		} // if
		
		int elapsedMinutes = elapsedTime / 60;
		//Subtract elapsedMinutes to display accurate elapsedSeconds
		int elapsedSeconds = elapsedTime - elapsedMinutes * 60;
		
		if (duration.greaterThan(Duration.ZERO)) {
			int totalTime = (int) Math.floor(duration.toSeconds());
			int totalHours = totalTime / (60 * 60);
			
			if (totalHours > 0) {
				totalTime = totalTime - totalHours * 60 * 60;
			} // if
			
			int totalMinutes = totalTime / 60;
			int totalSeconds = totalTime - totalMinutes * 60;
			
			if (totalHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, totalHours, totalMinutes, totalSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, totalMinutes, totalSeconds);
			} // if
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			} // if
			
		} // if
	} // formatTime
	
}
