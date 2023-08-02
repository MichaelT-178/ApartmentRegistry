package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;

/**
 * @author Matthew Salas
 * @date 4/16/2023
 * @section CSC 331-002
 * @purpose Controller class for the sign in page.
 */
public class SignInController {
	
    @FXML
    private TextField usernameOrEmailTextField;
    
    @FXML
    private PasswordField passwordTextField;

    /** Create account Hyperlink */
    @FXML
    private Hyperlink hyperLinkField;
    
    /** For navigation Purposes */
    @FXML 
    private AnchorPane signInRootPane;
   
    /**
     * When the "Sign In" button is pressed the database
     * will be checked for the given field values. If the credentials
     * are found the user will be signed in and will be navigated to 
     * the main page using the goToMainPage function. Displays error 
     * box if exception is thrown.
     * @param event Sign in button clicked.
     * @throws IllegalArgumentException If credentials are invalid.
     */
    @FXML
    private void signInButtonPressed(ActionEvent event) {
    	try {
    		String usernameOrEmail = usernameOrEmailTextField.getText();
    		String password = passwordTextField.getText(); 
    		
    		
    		String email = null;
    		String username = null;
    		
            if (usernameOrEmail.contains("@")) {
                email = usernameOrEmail;
            } else {
                username = usernameOrEmail;
            }
    		
            FormValidator validator = new FormValidator();
            validator.checkForValidCredentials(username, email, password);
            User signedInUser = validator.checkForValidCredentials(username, email, password);
           
    		goToMainPage(signedInUser); 
    		
    	} catch (IllegalArgumentException ex) {
    		displayErrorMessage(ex.getMessage());
    	}
    }
    
    /**
     * If the "Don't Have an Account? Sign Up" hyperlink is pressed the user will 
     * be navigated to the Create Account Page
     * @param event Hyperlink pressed 
     * @throws IOException If an error occurs during navigation.
     */
    @FXML
    private void createAccountLink(ActionEvent event) throws IOException {
    	Parent createAccountPane = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
    	signInRootPane.getChildren().setAll(createAccountPane);
    }
    
    /**
     * This function will navigate the user to the main page after thier
     * credentials are validated.
     * @param signedInUser The user that signed in
     */
    private void goToMainPage(User signedInUser) {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainPage.fxml"));
		
		Parent mainPage = null;
		
		try {
			mainPage = loader.load();
		} catch (IOException e) {
			System.out.println("THIS SHOULD NEVER BE DISPLAYED SIGNIN CONTROLLER");
		}
		
		MainPageController mainPageController = loader.getController();
		
		mainPageController.setUserOnMainPage(signedInUser);
	
    	signInRootPane.getChildren().setAll(mainPage);
    }
    
    /**
     * Error box that's displayed if an exception is thrown.
     * @param message Error message displayed in box
     */
    private static void displayErrorMessage(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Invalid Credentials");
    	alert.setContentText(message);
    	alert.show();
    }
}


