package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage primalStage;
	private static Scene loginScene;
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		primalStage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ViewLogin.fxml"));
			Parent parent = loader.load();
			loginScene = new Scene(parent);
			primaryStage.setScene(loginScene);
			primaryStage.setTitle("Game Memories login");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return loginScene;
	}
	
	public static Stage getPrimalStage() {
		return primalStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
