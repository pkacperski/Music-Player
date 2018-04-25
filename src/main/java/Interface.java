// Class containing an implemented interface

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Interface extends Application {

    private Stage stage;
    private String audioFile = null;
    private MediaPlayer player;
    private Boolean fileinuse = false;
    Label musicLabel = new Label("No File");
    Label nowPlaying = new Label("Now playing:\n");
    Label songDetails = new Label("Title: Kate Moss\nArtist: Organek\nAlbum: Głupi");
    Label time = new Label("\nTime: 0:31/2:46");
    
    public void chooseFile() throws MalformedURLException{
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("select your mp3 file", "*.mp3");
            chooser.getExtensionFilters().add(filter);
            File file = chooser.showOpenDialog(this.stage);
            if ( file !=null){
                musicLabel.setText(file.getName());
                this.audioFile = file.toURI().toURL().toString();
            }else{
                musicLabel.setText("Invalid Filename");
                this.audioFile = null;
            }
            fileinuse = false;
            player.stop();
    }
    
    public void playFile(){
            if(fileinuse == true){
                player.play();
            }else{
                Media pick = new Media(this.audioFile);
                player = new MediaPlayer(pick);
                player.play();   
            }
            fileinuse = true;
    }
    
    public void pauseFile(){
            player.pause();
    }
    
    public void stopFile(){
            player.stop();
    }

    @Override
    public void start(Stage primaryStage) {
        
        musicLabel.setWrapText(true);
        this.stage = primaryStage;
        StackPane root = new StackPane();
        
        Button openButton = new Button("Open File");
        Image playButtonImage =	new Image(getClass().getResourceAsStream("play.png"));
        openButton.setStyle("-fx-font-size: 16px;");
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
        
        HBox hbox1 = new HBox(80.0);
        hbox1.getChildren().addAll(openButton, playButton, pauseButton, stopButton);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(nowPlaying);
        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(musicLabel);
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(songDetails);
        HBox hbox5 = new HBox();
        hbox5.getChildren().addAll(albumCover);
        HBox hbox6 = new HBox();
        hbox6.getChildren().addAll(time);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbox5,hbox6);
        root.relocate(20, 0);
        
        openButton.setOnAction((ActionEvent e) -> {
            try {
                this.chooseFile();
            } catch (MalformedURLException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
        
        playButton.setOnAction((ActionEvent e) -> {
            this.playFile();
        });
        
        pauseButton.setOnAction((ActionEvent e) -> {
            this.pauseFile();
        });
        
        stopButton.setOnAction((ActionEvent e) -> {
            this.stopFile();
        });

        root.getChildren().add(vbox);
     
        Scene scene = new Scene(root, 720, 405);
        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
  }
    
  public static void main(String[] args) {
    launch(args);
  }
  
}