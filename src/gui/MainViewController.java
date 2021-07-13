package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemToPlay;
	
	@FXML
	private MenuItem menuItemAlredyPlayed;
	
	@FXML
	private MenuItem menuItemUserList;
	
	
	@FXML
	public void onMenuItemToPlayAction() {
		System.out.println("Enter");
	}
	
	@FXML
	public void onMenuItemAlredyPlayedAction() {
		System.out.println("Enter");
	}
	
	@FXML
	public void onMenuItemUserListAction() {
		loadView("/gui/UserList.fxml");
	}
	
	private synchronized void loadView(String trade) {
		 try {
			 	FXMLLoader loader = new FXMLLoader(getClass().getResource(trade));
				VBox newVBox = loader.load();
				
				Scene mainScene = Main.getMainScene();
				VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
				
				Node mainMenu = mainVBox.getChildren().get(0);
				mainVBox.getChildren().clear();
				mainVBox.getChildren().add(mainMenu);
				mainVBox.getChildren().addAll(newVBox.getChildren());
		 }
		 catch (IOException e ) {
			 Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		 }
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	
}