package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class OpenCutDialog {
	
	public static Optional<Pair<String, String>> openNewDialogForCutFragment() {
		/**
		 * Open a dialog in which the user will enter start and stop time, read that values
		 * and use them as arguments for setStartTime() and setStopTime()
		 */			
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Cut song fragment");
		dialog.setHeaderText("Choose a fragment of the song to be played\nby giving its start and stop time (in seconds)");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Set fragment", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the start time and stop time labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField startTimeField = new TextField();
		TextField stopTimeField = new TextField();

		grid.add(new Label("Start time:"), 0, 0);
		grid.add(startTimeField, 1, 0);
		grid.add(new Label("Stop time:"), 0, 1);
		grid.add(stopTimeField, 1, 1);
		dialog.getDialogPane().setContent(grid);

		// Request focus on the start time field by default.
		Platform.runLater(() -> startTimeField.requestFocus());

		// Convert the result to a start-stop-time-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(startTimeField.getText(), stopTimeField.getText());
		    }
		    return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		return result;		
	}	
	
	public OpenCutDialog() {
		try {
			openNewDialogForCutFragment();
		} catch(Exception e) {
			System.out.println("Exception in OpenCutDialog class constructor: " + e);
		}
	}

}