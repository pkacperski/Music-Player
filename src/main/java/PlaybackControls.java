import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlaybackControls {
	
	public static void playFile(){
		if(ChooseFile.fileInUse == true)
        	playFunction();
        else {
         	 Media pick = new Media(ChooseFile.audioFile);
             ChooseFile.player = new MediaPlayer(pick);
             playFunction();
        }
	 }
	
	public static void playFunction() {
		 if (ChooseFile.pauseFlag == false) {
	    	 if (CutSongFragment.getWereStartStopTimesChanged() == true) // remember the desired start and stop time values
	    		 CutSongFragment.setStartStopTimes();
	    	 // do not set anything if the user did not give start and stop times
		 }
		 else { // only set stop time if start time is set by pause button
			if (CutSongFragment.getWereStartStopTimesChanged() == true) {
	    		CutSongFragment.setStopTime();
	    		if (ChooseFile.prevStartTime == null) // only if it was not previously overwritten!
	    			ChooseFile.prevStartTime = ChooseFile.player.getStartTime();
	    		ChooseFile.player.setStartTime(ChooseFile.startTimeAfterPause);
			}
			else
				ChooseFile.player.setStartTime(ChooseFile.startTimeAfterPause);
			ChooseFile.pauseFlag = false;
		 }
	 ChooseFile.player.setRate(ChooseFile.tempRate);
	 ChooseFile.player.play();
	}
	
	 public static void pauseFile(){
		 	ChooseFile.pauseFlag = true;
		 	ChooseFile.wasPausedFlag = true;
		 	ChooseFile.startTimeAfterPause = ChooseFile.player.getCurrentTime();
		 	ChooseFile.tempRate = ChooseFile.player.getRate();
		 	//player.setStartTime(player.getCurrentTime()); // to start where we paused after clicking "play"!
		 	ChooseFile.player.pause();
	 }
	 
	 public static void stopFile(){
		 	ChooseFile.tempRate = ChooseFile.player.getRate();
		 	ChooseFile.player.stop();
	 		
	 		if (ChooseFile.wasPausedFlag == false) { // if pause was not pressed, it is OK to leave start and stop times as they were before
		 		if (CutSongFragment.getWereStartStopTimesChanged() == true) { // remember the desired start and stop time values
		 				ChooseFile.player.setStartTime(ChooseFile.d.multiply(CutSongFragment.startTime));
		 				ChooseFile.player.setStopTime(ChooseFile.d.multiply(CutSongFragment.stopTime));
		 		} // no need for else here - if there was no pause and no start or stop time change, stop returns to the beggining of the file by default		
		 	}
	 		else { // pause was pressed - start from the set startTime, not from the moment of last pause!
	 			if (CutSongFragment.getWereStartStopTimesChanged() == true) { // remember the desired start and stop time values
		 			if (ChooseFile.prevStartTime != null)
		 				ChooseFile.player.setStartTime(ChooseFile.prevStartTime);
		 			ChooseFile.player.setStopTime(ChooseFile.d.multiply(CutSongFragment.stopTime));
	 			}
	 			else { // the user did not give startTime or stopTime and pause was pressed - start playing file from the beginning
	 				ChooseFile.player.setStartTime(ChooseFile.d.multiply(0.0));
	 			}
	 		}
	 }
	 
		
	
	public PlaybackControls() {
		try{
			playFile();
		} catch (Exception e){
			System.out.println("Error in PlaybackControl class constructor: " + e);
		}
	}
	
	
}
