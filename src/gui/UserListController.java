package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.User;
import model.services.UserService;

public class UserListController implements Initializable, DataChangeListeners {

	private UserService userService;

	@FXML
	private TableView<User> tableViewUser;

	@FXML
	private TableColumn<User, Integer> tableColumnId;

	@FXML
	private TableColumn<User, String> tableColumnName;

	@FXML
	private TableColumn<User, User> tableColumnEDIT;

	@FXML
	private TableColumn<User, User> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<User> obsUserList;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		User obj = new User();
		createDialogForm(obj, "/gui/UserForm.fxml", parentStage);
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
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(User obj, String trade, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(trade));
			Pane pane = loader.load();

			UserFormController controller = loader.getController();
			controller.setUser(obj);
			controller.setUserService(new UserService());
			controller.subscribeDataChangeListeners(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter User Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void ondataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<User, User>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(User obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/UserForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<User, User>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(User obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(User obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Comfirmation", "Are you sure to delete ?");
		
		if (result.get() == ButtonType.OK) {
			if(userService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				userService.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
