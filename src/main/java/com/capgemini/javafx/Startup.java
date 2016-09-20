package com.capgemini.javafx;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application entry point.
 *
 * @author Leszek
 */
public class Startup extends Application {

	/**
	 * @see {@link javafx.application.Application#start(Stage)}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		 * Set the default locale based on the '--lang' startup argument.
		 */
		String langCode = getParameters().getNamed().get("lang");
		if (langCode != null && !langCode.isEmpty()) {
			Locale.setDefault(Locale.forLanguageTag(langCode));
		}

		// primaryStage.setTitle("%label.formTitle");
		primaryStage.setTitle("Search User Profiles");

		/*
		 * Load screen from FXML file with specific language bundle (derived
		 * from default locale).
		 */
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/view/searchUserProfiles.fxml"), //
				ResourceBundle.getBundle("javafx/bundle/base"));

		Scene scene = new Scene(root);

		/*
		 * Set the style sheet(s) for application.
		 */
		// scene.getStylesheets().add(getClass().getResource("/com/starterkit/javafx/css/standard.css").toExternalForm());
		// scene.getStylesheets().add(getClass().getResource("/com/starterkit/javafx/css/alternative.css").toExternalForm());

		primaryStage.setScene(scene);

		primaryStage.show();
	}
}
