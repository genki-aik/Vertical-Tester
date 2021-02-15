package application;
	
import java.io.File; 
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VerticalTester extends Application {
	
	double start;
	double stop;
	double hangTime;
	double vertical;
	double takeOff;
	double landing;
	//double duration;
	Duration duration;
	Media media;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	
	Controls controls = new Controls();
	Label playTime;
	Label volumeLabel;
	Slider slider;
	Slider volumeSlider;
	
	@Override
	public void start(Stage primaryStage) {
		String path = "/Users/genki/Downloads/VerticalNew1.mp4";
		
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
		
		
		mediaPlayer.setAutoPlay(true);
		buttonHandlers();
		
		
		duration = media.getDuration();

		//System.out.println(duration);
		slider = new Slider();
		volumeSlider = new Slider();
		HBox.setHgrow(slider, Priority.ALWAYS);
		
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(30);
		slider.setMinWidth(50);
		slider.setMaxWidth(Double.MAX_VALUE);
		
		playTime = new Label();
		
		volumeLabel = new Label();
		
		
		/*
		mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
			if (!slider.isValueChanging()) {
				slider.setValue(newTime.toSeconds());
			}
		});
		*/
		
		/*
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
		*/
		/*
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateValues();
			}
		});
		*/
		
		// Update the slider as well as time label
		mediaPlayer.currentTimeProperty().addListener(ov -> {
			updateValues();
		});
		
		slider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (slider.isValueChanging()) {
					mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
				} // if
			} // invalidated
		});
		
		volumeSlider.valueProperty().addListener(ov -> {
			if (volumeSlider.isValueChanging()) {
				mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
			} // if
		});
		
		mediaPlayer.setOnReady(new Runnable() {
			public void run() {
				duration = mediaPlayer.getMedia().getDuration();
				updateValues();
			} // run
		});
		/*
		slider.maxProperty().bind(Bindings.createDoubleBinding(
				() -> mediaPlayer.getTotalDuration().toSeconds(),
				mediaPlayer.totalDurationProperty()));
		*/
	
		BorderPane root = new BorderPane();
		Pane pane = new Pane();
		VBox vbox = new VBox();
		HBox hbox = new HBox();
	
		/*
		StackPane.setAlignment(startButton, Pos.TOP_LEFT);
		StackPane.setAlignment(play, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(pause, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(stopButton, Pos.TOP_RIGHT);
		root.getChildren().addAll(mediaView, startButton, play, pause, stopButton, calculate);
		*/

		pane.getChildren().add(mediaView);
		pane.setStyle("-fx-background-color: black;");
		hbox.getChildren().addAll(slider, playTime, volumeLabel, volumeSlider);
		vbox.getChildren().addAll(hbox, controls);
		root.setCenter(pane);
		root.setBottom(vbox);
		
		
		Scene scene = new Scene(root, 600, 600);
		
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
		Button extraVert;
		
		public Controls() {
			super(5);
			extraVert = new Button("How much vertical do I need?");
			startButton = new Button("Take Off");
			stopButton = new Button("Feet Lands");
			calculate = new Button("Calculate Vertical");
			play = new Button(">");
			pause = new Button("||");
			forward = new Button(">>");
			backward = new Button("<<");
			
			
			this.getChildren().addAll(extraVert, startButton, stopButton, calculate, play, pause, backward, forward);
			this.setAlignment(Pos.CENTER);
		}
	} // Controls
	
	private void buttonHandlers() {
		controls.startButton.setOnAction(e -> {
			System.out.println("Take off: " + mediaPlayer.getCurrentTime().toSeconds());
			takeOff = mediaPlayer.getCurrentTime().toSeconds();
			start = mediaPlayer.getCurrentTime().toSeconds();
		});
		
		controls.stopButton.setOnAction(e -> {
			System.out.println("Feet Lands: " + mediaPlayer.getCurrentTime().toSeconds());
			landing = mediaPlayer.getCurrentTime().toSeconds();
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
		
		controls.extraVert.setOnAction(e -> {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter standing reach in INCHES: ");
			double reach = scanner.nextDouble();
			System.out.println("Your standing reach is: " + reach);
			
			System.out.println("Extra reach needed to dunk basketball: average is 6 inches");
			System.out.println("Enter extra reach needed to dunk a basketball in INCHES: ");
			double extraReach = scanner.nextDouble();
			System.out.println("Your extra reach needed to dunk is: " + extraReach);
			
			double vertNeeded = 120 - reach;
			double dunkVert = vertNeeded + extraReach;
			
			System.out.println("In order to dunk on a 10ft rim, you need a vert of: " + dunkVert + "in");
			scanner.close();
		});
	} // buttonHandlers
	
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
	
	protected void updateValues() {
		if (playTime != null && slider != null && volumeSlider != null) {
			Platform.runLater(new Runnable() {
				
				public void run() {
					Duration currentTime = mediaPlayer.getCurrentTime();
					playTime.setText(formatTime(currentTime, duration));
					slider.setDisable(duration.isUnknown());
					if (!slider.isDisabled() && duration.greaterThan(Duration.ZERO) && !slider.isValueChanging()) {
						slider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
					} // if
					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
					} // if
				} // run
			});
		} // if
	} // updateValues
	
}
