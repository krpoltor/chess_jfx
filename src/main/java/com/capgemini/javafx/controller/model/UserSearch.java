package com.capgemini.javafx.controller.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.javafx.dataprovider.data.UserVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class UserSearch {

	private final StringProperty userId = new SimpleStringProperty();
	private final StringProperty userFirstName = new SimpleStringProperty();
	private final StringProperty userLastName = new SimpleStringProperty();
	private final StringProperty userEmail = new SimpleStringProperty();
	
	private final ListProperty<UserVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getUserId() {
		return userId.get();
	}

	public final void setUserId(String value) {
		userId.set(value);
	}

	public StringProperty userIdProperty() {
		return userId;
	}

	public final String getUserFirstName() {
		return userFirstName.get();
	}

	public final void setUserFirstName(String value) {
		userFirstName.set(value);
	}

	public StringProperty userFirstNameProperty() {
		return userFirstName;
	}

	public final String getUserLastName() {
		return userLastName.get();
	}

	public final void setUserLastName(String value) {
		userLastName.set(value);
	}

	public StringProperty userLastNameProperty() {
		return userLastName;
	}

	public final String getUserEmail() {
		return userEmail.get();
	}

	public final void setUserEmail(String value) {
		userEmail.set(value);
	}

	public StringProperty userEmailProperty() {
		return userEmail;
	}
	
	public final List<UserVO> getResult() {
		return result.get();
	}

	public final void setResult(List<UserVO> value) {
		result.setAll(value);
	}

	public ListProperty<UserVO> resultProperty() {
		return result;
	}

}
