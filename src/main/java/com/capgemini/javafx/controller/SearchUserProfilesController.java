package com.capgemini.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.capgemini.javafx.controller.model.UserSearch;
import com.capgemini.javafx.dataprovider.DataProvider;
import com.capgemini.javafx.dataprovider.data.UserVO;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchUserProfilesController {

	private static final Logger LOG = Logger.getLogger(SearchUserProfilesController.class);

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField userIdInputText;
	@FXML
	private TextField userFirstNameInputText;
	@FXML
	private TextField userLastNameInputText;
	@FXML
	private Button searchButton;
	@FXML
	private Button deleteProfileButton;
	@FXML
	private Button editProfileButton;
	@FXML
	private TableView<UserVO> foundUsersTable;
	@FXML
	private TableColumn<UserVO, String> userIdColumn;
	@FXML
	private TableColumn<UserVO, String> userFirstNameColumn;
	@FXML
	private TableColumn<UserVO, String> userLastNameColumn;
	@FXML
	private TableColumn<UserVO, String> userEmailColumn;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final UserSearch model = new UserSearch();

	public SearchUserProfilesController() {
		LOG.debug("Constructor: userLogin = " + userIdInputText);
	}

	@FXML
	private void initialize() {
		LOG.debug("initialize(): userLogin= " + userIdInputText + "userFirstName" + userFirstNameInputText
				+ "userLastName" + userLastNameInputText);

		initializeResultTable();

		userIdInputText.textProperty().bindBidirectional(model.userIdProperty());
		userFirstNameInputText.textProperty().bindBidirectional(model.userFirstNameProperty());
		userLastNameInputText.textProperty().bindBidirectional(model.userLastNameProperty());

		foundUsersTable.itemsProperty().bind(model.resultProperty());

		/*
		 * Make the Search button inactive when the input fields are empty.
		 */
		searchButton.disableProperty()
				.bind(userIdInputText.textProperty().isEmpty().and(userFirstNameInputText.textProperty().isEmpty())
						.and(userLastNameInputText.textProperty().isEmpty()));

	}

	private void initializeResultTable() {

		/*
		 * Define what properties of UserVO will be displayed in different
		 * columns.
		 */
		userIdColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
		userFirstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		userLastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSurname()));
		userEmailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));

		foundUsersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserVO>() {

			@Override
			public void changed(ObservableValue<? extends UserVO> observable, UserVO oldValue, UserVO newValue) {
				LOG.debug(newValue + " selected");

			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");
		searchForUsers();
	}

	/**
	 * The JavaFX runtime calls this method when the <b>EditProfile</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void editProfileButtonAction(ActionEvent event) {
		LOG.debug("'EditProfile' button clicked");
		
		try {
			Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("javafx/view/userProfile.fxml"), //
					ResourceBundle.getBundle("javafx/bundle/base"));
			Stage stage = new Stage();
			stage.setTitle("Edit Profile");
//			stage.setTitle("%label.formTitle");
			stage.setScene(new Scene(root1));
			stage.show();

			// hide this current window (if this is whant you want
//			 ((Node)(event.getSource())).getScene().getWindow().hide();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The JavaFX runtime calls this method when the <b>DeleteProfile</b> button
	 * is clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void deleteProfileButtonAction(ActionEvent event) {
		LOG.debug("'DeleteProfile' button clicked");
	}

	/**
	 * This implementation is correct.
	 * <p>
	 * The {@link DataProvider#findPersons(String, SexVO)} call is executed in a
	 * background thread.
	 * </p>
	 */
	private void searchForUsers() {
		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<UserVO>> backgroundTask = new Task<Collection<UserVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<UserVO> call() throws Exception {
				LOG.debug("call() called");

				/*
				 * Get the data.
				 */
				Collection<UserVO> result = dataProvider.findUsers( //
						model.getUserId(), //
						model.getUserFirstName(), //
						model.getUserLastName());

				/*
				 * Value returned from this method is stored as a result of task
				 * execution.
				 */
				// return null;
				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				/*
				 * Get result of the task execution.
				 */
				Collection<UserVO> result = getValue();

				/*
				 * Copy the result to model.
				 */
				model.setResult(new ArrayList<UserVO>(result));

				/*
				 * Reset sorting in the result table.
				 */
				foundUsersTable.getSortOrder().clear();
			}
		};

		/*
		 * Start the background task. In real life projects some framework
		 * manages background tasks. You should never create a thread on your
		 * own.
		 */
		new Thread(backgroundTask).start();
	}

}
