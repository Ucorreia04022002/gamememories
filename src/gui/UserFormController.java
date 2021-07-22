package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.exceptions.ValidationException;
import model.services.UserService;

public class UserFormController implements Initializable {
	
	UserDao userDao = DaoFactory.createUserDao();
	
	private User entity;
	
	private UserService service;
	
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtPassword;
	
	@FXML
	private TextField txtComfirm;
	
	@FXML
	private Label labelErrorId;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorComfirm;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Button btEdit;
	
	
	public void setUserService(UserService service) {
		this.service = service;
	}
	
	public void setUser(User entity) {
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
			service.saveUser(entity);
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
			service.updateUser(entity);
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

	private User getFormData() {
		User obj = new User();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		
		if(txtId.getText() == null || txtId.getText().trim().equals("")) {
			exception.addError("nullId", "Field can´t be empty");
		}
		obj.setId(Utils.tryparseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can´t be empty");
		}
		obj.setNameUser(txtName.getText());
		obj.setPasswordUser(txtPassword.getText());
		
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
		Constraints.setTextFieldMaxLength(txtPassword, 30);
		Constraints.setTextFieldMaxLength(txtComfirm, 30);

	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getNameUser());
		txtPassword.setText(entity.getPasswordUser());;
		
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
