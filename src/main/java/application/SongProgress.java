package application;

import java.util.TimerTask;

import javafx.scene.media.MediaPlayer.Status;

public class SongProgress extends TimerTask {

	private static double songProgress;
	
	public static double getSongProgress() {
		return songProgress;
	}
	
	private static String currentTime;
	
	public static String getCurrentTime() {
		return currentTime;
	}
	
	int temp;	
	
	@Override
	public void run() {
		if (ChooseFile.getPlayer().getStatus() == Status.PLAYING) {
			songProgress = (ChooseFile.getPlayer().getCurrentTime().toSeconds()) / (ChooseFile.getPlayer().getStopTime().toSeconds());
			temp = (int)ChooseFile.getPlayer().getCurrentTime().toSeconds();
			currentTime = String.valueOf(temp/60) + ':';
			if (temp%60 >= 10)	// to avoid printing "4:7" instead of "4:07"
				  currentTime += String.valueOf(temp%60);
			else
				  currentTime += '0' + String.valueOf(temp%60);
		}
	}
	
	
	public SongProgress() {
		
	}

}
