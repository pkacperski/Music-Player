/** Class containing implemented methods for opening files from disk */

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

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
	/*
	static void mediaPickPlay() {
		Media pick = new Media(audioFile);
		player = new MediaPlayer(pick); // ???
		player.play(); 
	}
	*/
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
        //chooser.getExtensionFilters().add(filter); // Do czego to sluzy?!?  
        
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
 		//String audioFile = ChooseFile.getAudioFile();
         if(fileinuse == true){
             player.play();
         }else{
         	 Media pick = new Media(audioFile);
             //MediaPlayer player = ChooseFile.getPlayer(); // czy to czegos nie schrzani?!? nie powinienem korzystac wciaz z tego samego playera???
             player = new MediaPlayer(pick);
             player.play();   
         }
         fileinuse = true;
	 }
 
	 public static void pauseFile(){
	 		player.pause();
	 }
	 
	 public static void stopFile(){
	 		player.stop();
	 }
	 
}
