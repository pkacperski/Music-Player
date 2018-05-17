/** Class containing implemented methods for opening files from disk */

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ChooseFile {
	static File file;	
	private static String audioFile = null;
	private static Boolean fileinuse = false;
	private static MediaPlayer player;
	
	static File getFile() {
		return file;
	}
	
	static String getAudioFile() {
	    	return audioFile;
	}
	
	static Boolean getFileInUse() {
    	return fileinuse;
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
        //FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select your mp3 file", "*.mp3");
        //chooser.getExtensionFilters().add(filter); // necessary?!?  
        
        if (file != null){      	
            Interface.getMusicLabel().setText(file.getName());
            audioFile = file.toURI().toURL().toString();
            // try to read ID3 tag info when the file is succesfully open
            GetID3TagInfo.getID3TagInfo();
        }else{
            Interface.getMusicLabel().setText("Invalid Filename");
            audioFile = null;
        }
        // if the music is playing and you select another track from disk, stop playing the current track
        if (fileinuse == true) {
        	player.stop();
        	fileinuse = false;
        }
    }
	
	
	 public static void playFile(){
         if(fileinuse == true){
             player.play();
         }else{
         	 Media pick = new Media(audioFile);
             player = new MediaPlayer(pick);
             /**
              * Project modifications - added functionality of changing playback speed and
              * setting start and end time for the playback
              */
             player.setRate(1.0); // default playback speed; may be changed to any value from [0.0, 8.0]
             Duration d = new Duration(1000); // duration of one second
             player.setStartTime(d.multiply(27.671)); // ... multiplied by the exact number of seconds when we want to start the playback!
             player.setStopTime(d.multiply(62.254)); // ... and stop the playback!
             player.play(); 
         }
         fileinuse = true;
	 }
 
	 public static void pauseFile(){
		 	/*String currTime = new String();
		 	currTime = player.getCurrentTime().toString();
		 	System.out.println(currTime);*/  // just to see how getCurrentTime() method works
	 		player.pause();
	 }
	 
	 public static void stopFile(){
	 		player.stop();
	 }
	 
}
