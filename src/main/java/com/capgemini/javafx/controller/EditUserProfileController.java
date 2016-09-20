package com.capgemini.javafx.controller;

import org.apache.log4j.Logger;

import com.capgemini.javafx.dataprovider.data.UserVO;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditUserProfileController {

	private static final Logger LOG = Logger.getLogger(EditUserProfileController.class);
	
	private UserVO selectedUser = new UserVO();

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
	private void initialize() {
		LOG.debug("initialize(): userLogin= " + userIdTextField + "userFirstName" + userFirstNameTextField
				+ "userLastName" + userLastNameTextField + "userPassword" + userPasswordTextField + "userAboutMe" + userAboutMeTextField + "userEmail"
				+ userEmailTextField + "userLifeMotto" + userLifeMottoTextField);
		
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

}
