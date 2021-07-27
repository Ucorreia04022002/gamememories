package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import model.entities.Game;
import model.services.GameService;
import model.services.UserService;

public class GameListConditionController implements Initializable, DataChangeListeners {

	private GameService userService;

	@FXML
	private TableView<Game> tableViewGame;

	@FXML
	private TableColumn<Game, Integer> tableColumnId;

	@FXML
	private TableColumn<Game, String> tableColumnName;

	@FXML
	private TableColumn<Game, Date> tableColumnReleaseDate;
	
	@FXML
	private TableColumn<Game, Double> tableColumnPrice;
	
	@FXML
	private TableColumn<Game, Game> tableColumnEDIT;

	@FXML
	private TableColumn<Game, Game> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Game> obsGameList;

	public void setGameService(GameService userService) {
		this.userService = userService;
	}

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Game obj = new Game();
		createDialogForm(obj, "/gui/GameForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNode();
	}

	private void initializeNode() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("GameName"));
		tableColumnReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
		Utils.formatTableColumnDate(tableColumnReleaseDate, "dd/MM/yyyy");
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("gameprice"));
		Utils.formatTableColumnDouble(tableColumnPrice, 2);

		


		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewGame.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (userService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Game> list = userService.findAllNoCondition();
		obsGameList = FXCollections.observableArrayList(list);
		tableViewGame.setItems(obsGameList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Game obj, String trade, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(trade));
			Pane pane = loader.load();

			GameFormController controller = loader.getController();
			controller.setGame(obj);
			controller.setServices(new GameService(), new UserService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListeners(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Game Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void ondataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Game, Game>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Game obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/GameForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Game, Game>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Game obj, boolean empty) {
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

	private void removeEntity(Game obj) {
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
