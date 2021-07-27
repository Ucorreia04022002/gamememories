package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.GameService;
import model.services.UserService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemToPlay;
	
	@FXML
	private MenuItem menuItemAlredyPlayed;
	
	@FXML
	private MenuItem menuItemUserList;
	
	
	@FXML
	public void onMenuItemToPlayAction() {
		loadView("/gui/GameList.fxml", (GameListController controller) -> {
			controller.setGameService(new GameService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAlredyPlayedAction() {
		loadView("/gui/GameListCondition.fxml", (GameListConditionController controller) -> {
			controller.setGameService(new GameService());
			controller.updateTableView();
		});
	}	
	
	@FXML
	public void onMenuItemUserListAction() {
		loadView("/gui/UserList.fxml", (UserListController controller) -> {
			controller.setUserService(new UserService());
			controller.updateTableView();
		});
	}
	
	private synchronized <T> void loadView(String trade, Consumer<T> initializingAction) {
		 try {
			 	FXMLLoader loader = new FXMLLoader(getClass().getResource(trade));
				VBox newVBox = loader.load();
				
				Scene mainScene = Main.getMainScene();
				VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
				
				Node mainMenu = mainVBox.getChildren().get(0);
				mainVBox.getChildren().clear();
				mainVBox.getChildren().add(mainMenu);
				mainVBox.getChildren().addAll(newVBox.getChildren());
				
				T controller = loader.getController();
				initializingAction.accept(controller);
		 }
		 catch (IOException e ) {
			 Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		 }
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	
}
