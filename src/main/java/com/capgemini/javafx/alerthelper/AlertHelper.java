package com.capgemini.javafx.alerthelper;

import java.util.Optional;

import com.capgemini.javafx.alerthelper.impl.AlertHelperImpl;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public interface AlertHelper {

	/**
	 * Instance of this interface.
	 */
	AlertHelper INSTANCE = new AlertHelperImpl();

	Optional<ButtonType> showConfirmationAlert(String title, String header, String content);

	void showErrorAlert(String title, String header, String content);

	void showInformationAlert(String title, String header, String content);

	void showCustomTypeAlert(AlertType alertType, String title, String header, String content);

}
