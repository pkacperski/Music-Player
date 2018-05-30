package application;

/**
 *  Class containing implemented methods for opening files from disk
 */

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ChooseFile {
	static File file;	
	/*private*/ public static String audioFile = null;
	/*private*/ public static Boolean fileInUse = false;
	//private static MediaPlayer player;
	public static MediaPlayer player;
	public static Duration startTimeAfterPause;
	public static Duration d = new Duration(1000); // duration of one second
	public static Duration prevStartTime; // to remember start time set before pause button was clicked
	public static double tempRate = 1.0; // to remember the wanted rate even after pause; by default set to 1.0 (normal speed)
	static Boolean pauseFlag = false; // set to indicate that current startTime was modified due to clicking the pause button
	static Boolean wasPausedFlag = false; // set to indicate whether pause was pressed anytime during the program life cycle
	
	private static String fileName; // TODO: always static....
	private static String filePath;
	
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
	
	public static String getFileName() {
		return fileName;
	}
	
	public static String getFilePath() {
		return filePath;
	}
	

	public static void chooseFile() throws MalformedURLException{
		FileChooser chooser = new FileChooser();
    	file = chooser.showOpenDialog(Main.getPrimaryStage());
    	filePath = file.getPath();   	
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
        	fileName = file.toString();
            fileName = fileName.substring(fileName.lastIndexOf('/')+1);
            audioFile = file.toURI().toURL().toString();
            //GetID3TagInfo.getID3TagInfo(); // try to read ID3 tag info when the file is successfully open TODO: ERROR - filenotfound exception
 /* !!! */  PlaybackControls.playFile();
        } else { // incorrect file chosen
        	fileName = "Incorrect file chosen! Try again and choose a valid file";
            audioFile = null;
        	}
    }
	
	public ChooseFile() {
		try{
			chooseFile();
		} catch (Exception e){
			System.out.println("Error in ChooseFile class constructor: " + e);
		}
	}
	
}