/** Main class, containing an implemented interface */

import java.io.*;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Interface extends Application {
    private static Stage stage; // ????
    //private static String audioFile = null;
    //private MediaPlayer player;
    static Label musicLabel = new Label("No File");
    Label nowPlaying = new Label("Now playing:\n");
    //Label songDetails = new Label("Title: Kate Moss\nArtist: Organek\nAlbum: Głupi");
    private static Label titleInfo = new Label("Title: ");
    private static Label artistInfo = new Label("Artist: ");
    private static Label albumInfo = new Label("Album: ");
    Label time = new Label("\nTime: 0:31/2:46");
    static File file;
    
    static Stage getStage() {
    	return stage;
    }
    
    static Label getMusicLabel() {
    	return musicLabel;
    }
    
    static Label getTitleInfo() {
    	return titleInfo;
    }
    
    static Label getArtistInfo() {
    	return artistInfo;
    }
    
    static Label getAlbumInfo() {
    	return albumInfo;
    }

    @Override
    public void start(Stage primaryStage) {
        
        musicLabel.setWrapText(true);
        //this.stage = primaryStage;
        stage = primaryStage;
        StackPane root = new StackPane();
        
        Button openButton = new Button("Open File");
        openButton.setStyle("-fx-font-size: 16px;");
        Image playButtonImage =	new Image(getClass().getResourceAsStream("play.png"));
        Button playButton = new Button();
        playButton.setGraphic(new ImageView(playButtonImage));
        Image pauseButtonImage = new Image(getClass().getResourceAsStream("pause.png"));
        Button pauseButton = new Button();
        pauseButton.setGraphic(new ImageView(pauseButtonImage));
        Image stopButtonImage = new Image(getClass().getResourceAsStream("stop.png"));
        Button stopButton = new Button();
        stopButton.setGraphic(new ImageView(stopButtonImage));
        Image albumCoverImage = new Image(getClass().getResourceAsStream("glupi.jpg"));
        Button albumCover = new Button();
        albumCover.setGraphic(new ImageView(albumCoverImage));
        Button equalizerButton = new Button("Equalizer");
        equalizerButton.setStyle("-fx-font-size: 14px;");
        Button newPlaylistButton = new Button("New playlist");
        newPlaylistButton.setStyle("-fx-font-size: 14px;");
        
        HBox hbox0 = new HBox();
        hbox0.getChildren().addAll(equalizerButton, newPlaylistButton);
        HBox hbox1 = new HBox(60.0);
        hbox1.getChildren().addAll(openButton, playButton, pauseButton, stopButton, hbox0);
        Text hbox2 = new Text("Now playing:");
        hbox2.setTextAlignment(TextAlignment.LEFT);
        HBox hbox3 = new HBox();
        hbox3.getChildren().add(musicLabel);
        HBox hbox4 = new HBox();
        hbox4.getChildren().add(titleInfo);
        HBox hbox5 = new HBox();
        hbox5.getChildren().add(artistInfo);
        HBox hbox6 = new HBox();
        hbox5.getChildren().add(albumInfo);
        HBox hbox7 = new HBox();
        hbox7.getChildren().add(albumCover);
        HBox hbox8 = new HBox();
        hbox8.getChildren().add(time);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbox5,hbox6,hbox7,hbox8);
        root.relocate(20, 0);
        
        
        openButton.setOnAction((ActionEvent e) -> {
            try {
                //this.ChooseFile.chooseFile();
                ChooseFile.chooseFile();
            } catch (MalformedURLException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
        
        playButton.setOnAction((ActionEvent e) -> {
            ChooseFile.playFile();
        });
        
        pauseButton.setOnAction((ActionEvent e) -> {
            ChooseFile.pauseFile();
        	//ChooseFile.player.pause();
        });
        
        stopButton.setOnAction((ActionEvent e) -> {
            ChooseFile.stopFile();
        });
        
    
        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.TOP_RIGHT);
        
        Scene scene = new Scene(root, 800, 420);
        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
  }
    
  public static void main(String[] args) {
    launch(args);
  }
  
}