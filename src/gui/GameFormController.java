package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.GameDao;
import model.entities.Game;
import model.exceptions.ValidationException;
import model.services.GameService;

public class GameFormController implements Initializable {
	
	GameDao userDao = DaoFactory.createGameDao();
	
	private Game entity;
	
	private GameService service;
	
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtPrice;
	
	@FXML
	private DatePicker dpReleaseDate;
	
	@FXML
	private Label labelErrorId;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorReleaseDate;
	
	@FXML
	private Label labelErrorPrice;
	
	@FXML
	private Label labelErrorUser;
	
	@FXML
	private Label labelErrorCondition;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Button btEdit;
	
	
	public void setGameService(GameService service) {
		this.service = service;
	}
	
	public void setGame(Game entity) {
		this.entity = entity;
	}
	
	public void subscribeDataChangeListeners(DataChangeListeners listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveGame(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		}
		catch (ValidationException e ) {
			setErrorMessages(e.getErros());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtEditAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.updateGame(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		}
		catch (ValidationException e ) {
			setErrorMessages(e.getErros());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {

		for(DataChangeListeners listener : dataChangeListeners) {
			listener.ondataChanged();
		}
	}

	private Game getFormData() {
		Game obj = new Game();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		
		if(txtId.getText() == null || txtId.getText().trim().equals("")) {
			exception.addError("nullId", "Field can´t be empty");
		}
		obj.setId(Utils.tryparseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can´t be empty");
		}
		obj.setGameName(txtName.getText());
		obj.setGameprice(Double.parseDouble(txtPrice.getText()));
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldMaxLength(txtPrice, 30);
		Constraints.setTextFieldDouble(txtPrice);
		Utils.formatDatePicker(dpReleaseDate, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getGameName());
		Locale.setDefault(Locale.US);
		txtPrice.setText(String.format("%.2f", entity.getGameprice()));
		if(entity.getReleaseDate() != null) {
			dpReleaseDate.setValue(LocalDate.ofInstant(entity.getReleaseDate().toInstant(), ZoneId.systemDefault()));

		}
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}

		if (fields.contains("nullId")) {
			labelErrorId.setText(errors.get("nullId"));
		}
		
	}

	
}
