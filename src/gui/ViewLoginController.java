package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewLoginController implements Initializable {

	@FXML
	private TextField txtNameUser;
	
	@FXML
	private TextField txtPasswordUser;
	
	@FXML
	private Button btLogin;
	
	@FXML
	private Button btSign;
	
	
	@FXML
	public void onBtLogin() {
		tradeScene("/gui/MainView.fxml");
	}
	
	@FXML
	public void onBtSign() {
		
	}
	
	public void tradeScene(String trade) {
		try {
			Parent loader = FXMLLoader.load(getClass().getResource(trade));
			Scene entranceScene = new Scene(loader);
			Stage primalStage = Main.getPrimalStage();
			primalStage.setScene(entranceScene);
		}
		catch (IOException e ) {
			e.getMessage();
		}
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}

	
}
