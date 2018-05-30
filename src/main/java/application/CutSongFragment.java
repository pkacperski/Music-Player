package application;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import javafx.util.Pair;

/**
  * Project modification - added functionality of setting start and end time for the playback
  * (cutting just a fragment of the song to be played)
  */
public class CutSongFragment {

	static double startTime, stopTime;
	static Boolean wereStartStopTimesChanged = false;
	public static double origStartTime;
	static Boolean startTimeChanged = false;
	
public static void cutFragment(){
		Optional<Pair<String, String>> result = OpenCutDialog.openNewDialogForCutFragment();
		
		if (result.isPresent()){
			String temp = ChooseFile.getPlayer().getStopTime().toString(); // get the previous stop time
			temp = temp.substring(0, temp.length()-3);
			double defaultStopTime = Double.parseDouble(temp);
			String realResult = result.toString();
			// now, get the start and stop time from the input fields
			realResult = realResult.substring(9); // shift to the first numerical value			
			int shift = 1;
			while (realResult.toCharArray()[shift-1] != '=')
				shift++;
			if (realResult.toCharArray()[0] == '=') // if the user gave no start time, set it to 0.0 seconds
				startTime = 0.0;
			else
				startTime = Double.parseDouble(realResult.substring(0, shift-1)); // else set startTime to a given value
			if (startTimeChanged == false) {
				origStartTime = startTime;
				startTimeChanged = true;
			}
			// shift-1 in realResult points to '=', we take everything after this sign until the end
			if (shift == realResult.length()-1) // if the user gave no stop time, simply leave it as it is
				stopTime = defaultStopTime;
			else // else set stopTime to a given value
				stopTime = Double.parseDouble(realResult.substring(shift, realResult.length()-1)); 
			
		if (startTime >= stopTime || startTime < 0 || stopTime <= 0) {
					Alert alert = new Alert(AlertType.ERROR); // pom
					alert.setTitle("Wrong input values");
					alert.setHeaderText("Incorrect start time and/or stop time values!");
					alert.setContentText("Stop time must be greater than start time,\nboth must not be negative.\nCorrect the input and try again");
					alert.showAndWait();
				}
		// both times are correct - set both and start playing the given fragment
			wereStartStopTimesChanged = true;
			setStartStopTimes();
		} 		
	} // cutFragment()
		
	public static void setStartStopTimes() {
		Duration d = new Duration(1000); // duration of one second
		ChooseFile.getPlayer().setStartTime(d.multiply(startTime)); 
		ChooseFile.getPlayer().setStopTime(d.multiply(stopTime));
		ChooseFile.getPlayer().stop();
		ChooseFile.getPlayer().play();
	}
	
	public static void setStopTime() {
		Duration d = new Duration(1000); // duration of one second
		ChooseFile.getPlayer().setStopTime(d.multiply(stopTime));
		ChooseFile.getPlayer().stop();
		ChooseFile.getPlayer().play();
	}
	
	public CutSongFragment() {
		try {
			cutFragment();
		} catch(Exception e) {
			System.out.println("Exception in CutSongFragment class constructor: " + e);
		}
	}
	
	public static Boolean getWereStartStopTimesChanged() {
		return wereStartStopTimesChanged;
	}

}