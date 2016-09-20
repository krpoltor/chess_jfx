package com.capgemini.javafx.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.capgemini.javafx.context.Context;
import com.capgemini.javafx.dataprovider.DataProvider;
import com.capgemini.javafx.dataprovider.data.UserVO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserProfileController {

	private static final Logger LOG = Logger.getLogger(EditUserProfileController.class);

	@FXML
	private TextField userIdTextField;
	@FXML
	private TextField userFirstNameTextField;
	@FXML
	private TextField userLastNameTextField;
	@FXML
	private TextField userPasswordTextField;
	@FXML
	private TextArea userAboutMeTextField;
	@FXML
	private TextField userEmailTextField;
	@FXML
	private TextArea userLifeMottoTextField;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private UserVO selectedUser;

	@FXML
	private void initialize() {
		LOG.debug("initialize(): userLogin= " + userIdTextField + "userFirstName" + userFirstNameTextField
				+ "userLastName" + userLastNameTextField + "userPassword" + userPasswordTextField + "userAboutMe"
				+ userAboutMeTextField + "userEmail" + userEmailTextField + "userLifeMotto" + userLifeMottoTextField);

		selectedUser = Context.getInstance().currentUser();

		initializeInputFields();

	}

	private void initializeInputFields() {
		userIdTextField.setText(selectedUser.getLogin());
		userFirstNameTextField.setText(selectedUser.getName());
		userLastNameTextField.setText(selectedUser.getSurname());
		userPasswordTextField.setText(selectedUser.getPassword());
		userAboutMeTextField.setText(selectedUser.getAboutMe());
		userEmailTextField.setText(selectedUser.getEmail());
		userLifeMottoTextField.setText(selectedUser.getLifeMotto());
	}

	@FXML
	private void saveButtonAction(ActionEvent event) throws IOException {
		LOG.debug("'Save' button clicked");

		UserVO updatedUser = new UserVO();
		updatedUser.setId(selectedUser.getId());
		updatedUser.setLogin(userIdTextField.getText());
		updatedUser.setName(userFirstNameTextField.getText());
		updatedUser.setSurname(userLastNameTextField.getText());
		updatedUser.setPassword(userPasswordTextField.getText());
		updatedUser.setEmail(userEmailTextField.getText());
		updatedUser.setAboutMe(userAboutMeTextField.getText());
		updatedUser.setLifeMotto(userLifeMottoTextField.getText());

		dataProvider.updateUser(updatedUser);

		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			Parent root = FXMLLoader.load(
					getClass().getClassLoader().getResource("javafx/view/searchUserProfiles.fxml"), //
					ResourceBundle.getBundle("javafx/bundle/base"));
			Stage stage = new Stage();
			stage.setTitle("Search player profiles");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			// hide this current window (if this is whant you want
			// ((Node)(event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void cancelButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(
					getClass().getClassLoader().getResource("javafx/view/searchUserProfiles.fxml"), //
					ResourceBundle.getBundle("javafx/bundle/base"));
			Stage stage = new Stage();
			stage.setTitle("Search player profiles");
			stage.setScene(new Scene(root, 800, 600));
			stage.show();

			((Node) (event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
