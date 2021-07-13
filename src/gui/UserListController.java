package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.User;
import model.services.UserService;

public class UserListController implements Initializable {

	private UserService userService;
	
	
	@FXML
	private TableView<User> tableViewUser;
	
	@FXML
	private TableColumn<User, Integer> tableColumnId;
	
	@FXML
	private TableColumn<User, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<User> obsUserList;
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();
	}


	private void initializeNode() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nameUser"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUser.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (userService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<User> list = userService.findAll();
		obsUserList = FXCollections.observableArrayList(list);
		tableViewUser.setItems(obsUserList);
	}
	
	
}
