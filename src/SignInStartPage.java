package main;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Matthew Salas
 * @date 4/16/2023
 * @section CSC 331-002
 * @purpose Sets up and launches the ApartmentRegistry application
 */
public class SignInStartPage extends Application {
	
	
    /**
     * Sets up the stage for the Sign In Page
     * @param stage The stage (window) of the application
     */
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Apartment Registry");
		stage.setScene(scene);
		stage.show();
	}
	
    /**
     * Launches the ApartmentRegistry app
     * @param args Unused command line arguments
     */
	public static void main(String[] args) {
		launch(args);
	}
}