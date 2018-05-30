package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

public class MyControler {

	MediaPlayer player;

	@FXML
	private Button openFileBtn;
	@FXML
	private Label nowPlayingLabel;
	@FXML
	private Button playButton;
	@FXML
	private Button pauseButton;
	@FXML
	private Button stopButton;
	@FXML
	private Button speedButton;
	@FXML
	private Button loopFragmentButton;
	@FXML
	private Label titleLabel;
	@FXML
	private Label artistLabel;
	@FXML
	private Label albumLabel;
	@FXML
	private ImageView cover;
		
	@FXML
	private Label currentTimeLabel;
	@FXML
	private Label songLengthLabel;
	@FXML
	private ProgressBar progressBar;	
	
	
	@FXML
	private void openFile(ActionEvent e) throws MalformedURLException { // MUST BE 'PRIVATE VOID' !!!!!
		
		//blad Radzia
		//ChooseFile.chooseFile(this);
		
		ChooseFile.chooseFile();
		nowPlayingLabel.setText("Now playing:  " + ChooseFile.getFileName());
		
		GetID3TagInfo.getID3TagInfo();
		titleLabel.setText(GetID3TagInfo.getTitleInfo());
		artistLabel.setText(GetID3TagInfo.getArtistInfo());
		albumLabel.setText(GetID3TagInfo.getAlbumInfo());
		cover.setImage(GetID3TagInfo.getAlbumCover());
		songLengthLabel.setText(GetID3TagInfo.getSongLength());
				
		Timer timer = new Timer();
		timer.schedule(new SongProgress(), 0, 1000);
		
		Timer timer2 = new Timer();
		timer2.schedule(new TimerTask() {
			@Override
			 public void run() {
				 if (ChooseFile.getPlayer().getStatus() == Status.PLAYING) {
				 	progressBar.setProgress(SongProgress.getSongProgress());
				 	Platform.runLater(() -> currentTimeLabel.setText(SongProgress.getCurrentTime()));
				 }
		     }
		}, 0, 1000);
	}
	
	@FXML
	private void playMusic(ActionEvent e) {
		PlaybackControls.playFile();
	}
	
	@FXML
	private void pausePlayback(ActionEvent e) {
		//player.pause(); // null pointer exception
		PlaybackControls.pauseFile();
	}
	
	@FXML
	private void stopPlayback(ActionEvent e) {
		PlaybackControls.stopFile();
	}
	
	@FXML
	private void setSpeed(ActionEvent e) {
//		SetPlaybackSpeed.setSpeed(); //pom
	}
	
	@FXML
	private void loopFragment(ActionEvent e) {
//		CutSongFragment.cutFragment();
	}
	
	public MyControler() {
		
	}

}
