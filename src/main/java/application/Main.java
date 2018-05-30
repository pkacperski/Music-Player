package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	public static Stage primaryStage;
	
	public static MyControler control = new MyControler();
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane p = FXMLLoader.load(getClass().getClassLoader().getResource("SampleGridPane.fxml"));	
			
			Scene scene = new Scene(p); //(p,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Music Player");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}
}
