import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

/**
 * Project modification - added functionality of changing playback speed
 */

public class SetPlaybackSpeed {
	public static void setSpeed(){
		/**
		 * Open a dialog in which the user will enter playback speed, read that value
		 * and use it as an argument for setRate()
		 * Correct speed belongs to [0.0, 8.0], where 0.0 - pause, 1.0 - normal speed
		 */
		String currentSpeed = Double.toString(ChooseFile.getPlayer().getRate());
		TextInputDialog dialog = new TextInputDialog(currentSpeed);
		dialog.setTitle("Playback speed");
		dialog.setHeaderText("You may change playback speed (rate) from 0.0 (pause) to 8.0 here");
		dialog.setContentText("Enter the desired playback speed: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String realResult = result.get();
			if (Double.parseDouble(realResult) < 0 || Double.parseDouble(realResult) > 8) { // if the result is incorrect
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wrong input value");
				alert.setHeaderText("Incorrect playback speed value!");
				alert.setContentText("Playback speed (rate) must not\nbe lower than 0 or greater than 8.\nCorrect the input and try again.");
				alert.showAndWait();
			}
			else { // if the result is correct
				ChooseFile.getPlayer().setRate(Double.parseDouble(realResult));
				ChooseFile.setTempRate(Double.parseDouble(realResult));
			}
		} 
		// The Java 8 way to get the response value (with lambda expression).
		//Optional<String> result = dialog.showAndWait();
		//result.ifPresent(name -> ChooseFile.getPlayer().setRate(Double.parseDouble(name)));
	}
	
	public SetPlaybackSpeed() {
		try {
			setSpeed();
		} catch(Exception e) {
			System.out.println("Error in SetPlaybackSpeed class constructor: " + e);
		}
	}
}
