/**
 *  Class containing implemented methods for opening files from disk
 */

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ChooseFile {
	static File file;	
	private static String audioFile = null;
	private static Boolean fileInUse = false;
	private static MediaPlayer player;
	public static Duration startTimeAfterPause;
	public static Duration d = new Duration(1000); // duration of one second
	public static Duration prevStartTime; // to remember start time set before pause button was clicked
	public static double tempRate = 1.0; // to remember the wanted rate even after pause; by default set to 1.0 (normal speed)
	static Boolean pauseFlag = false; // set to indicate that current startTime was modified due to clicking the pause button
	static Boolean wasPausedFlag = false; // set to indicate whether pause was pressed anytime during the program life cycle
	//static Boolean justWokeUp = true;
	
	public static double getTempRate() {
		return tempRate;
	}

	public static void setTempRate(double value) {
		tempRate = value;
	}
	
	static File getFile() {
		return file;
	}
	
	static String getAudioFile() {
	    return audioFile;
	}
	
	static Boolean getFileInUse() {
    	return fileInUse;
    }
	
	static MediaPlayer getPlayer() {
		return player;
	}
	
	public ChooseFile() {
		try{
			chooseFile();
		} catch (Exception e){
			System.out.println("Error in ChooseFile class constructor: " + e);
		}
	}
	
	public static void chooseFile() throws MalformedURLException{
    	FileChooser chooser = new FileChooser();
    	file = chooser.showOpenDialog(Interface.getStage());
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select your mp3 file", "*.mp3");
        chooser.getExtensionFilters().add(filter); // https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.ExtensionFilter.html  
    	
    	// if the music is playing and you select another track from disk, stop playing the current track
        if (fileInUse == true) {
        	player.stop();
        	fileInUse = false;
        }
    	
        // file chosen correctly - get its info from ID3 tag and start playing it
        if (file != null && file.exists() && (file.toString().substring(file.toString().length()-4).equals(".mp3")
        		|| file.toString().substring(file.toString().length()-4).equals(".MP3")) ){
            Interface.getMusicLabel().setText(file.getName());
            audioFile = file.toURI().toURL().toString();
            GetID3TagInfo.getID3TagInfo(); // try to read ID3 tag info when the file is successfully open
            playFile();
        } else { // incorrect file chosen
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid file name");
			alert.setHeaderText("The file you have chosen is not a correct MP3 file!");
			alert.setContentText("Select a correct *.mp3 input file and try again.");
			alert.showAndWait();
            audioFile = null;
        }
        
    }
	
	
	public static void playFile(){
			if(fileInUse == true)
	        	playFunction();
	        else {
	         	 Media pick = new Media(audioFile);
	             player = new MediaPlayer(pick);
	             playFunction();
	        }
	 }
	
	public static void playFunction() {
		 if (pauseFlag == false) {
        	 if (CutSongFragment.getWereStartStopTimesChanged() == true) // remember the desired start and stop time values
        		 CutSongFragment.setStartStopTimes();
        	 // do not set anything if the user did not give start and stop times
    	 }
    	 else { // only set stop time if start time is set by pause button
    		if (CutSongFragment.getWereStartStopTimesChanged() == true) {
        		CutSongFragment.setStopTime();
        		if (prevStartTime == null) // only if it was not previously overwritten!
        			prevStartTime = player.getStartTime();
        		player.setStartTime(startTimeAfterPause);
    		}
    		else
    			player.setStartTime(startTimeAfterPause);
    		pauseFlag = false;
    	 }
     player.setRate(tempRate);
     player.play();
	}
 
	 public static void pauseFile(){
		 	pauseFlag = true;
		 	wasPausedFlag = true;
		 	startTimeAfterPause = player.getCurrentTime();
		 	tempRate = player.getRate();
		 	//player.setStartTime(player.getCurrentTime()); // to start where we paused after clicking "play"!
	 		player.pause();
	 }
	 
	 public static void stopFile(){
		 	tempRate = player.getRate();
	 		player.stop();
	 		
	 		if (wasPausedFlag == false) { // if pause was not pressed, it is OK to leave start and stop times as they were before
		 		if (CutSongFragment.getWereStartStopTimesChanged() == true) { // remember the desired start and stop time values
			 			player.setStartTime(d.multiply(CutSongFragment.startTime));
			 			player.setStopTime(d.multiply(CutSongFragment.stopTime));
		 		} // no need for else here - if there was no pause and no start or stop time change, stop returns to the beggining of the file by default		
		 	}
	 		else { // pause was pressed - start from the set startTime, not from the moment of last pause!
	 			if (CutSongFragment.getWereStartStopTimesChanged() == true) { // remember the desired start and stop time values
		 			if (prevStartTime != null)
	 					player.setStartTime(prevStartTime);
		 			player.setStopTime(d.multiply(CutSongFragment.stopTime));
	 			}
	 			else { // the user did not give startTime or stopTime and pause was pressed - start playing file from the beginning
	 				player.setStartTime(d.multiply(0.0));
	 			}
	 		}
	 }
	 
}
