package main;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.event.EventHandler;

/**
* @author Glenn Johnson
* @date 4/16/2023
* @section CSC 331 - 002
* @purpose Creates a User object and add it to the database
*          using user input.
*/
public class CreateAccountController {
	
	@FXML 
	private TextField firstNameTextField;
	
	@FXML 
	private TextField lastNameTextField;
	
	@FXML 
	private TextField emailTextField;
	
	@FXML 
	private TextField phoneNumberTextField;
	
	@FXML 
	private TextField usernameTextField;
	
	@FXML 
	private TextField passwordTextField;
	
	@FXML 
	private TextField reEnteredPWTextField;
	
	/** Used for navigation purposes */
	@FXML 
	private AnchorPane createAccountRootPane;
	
    @FXML
    private Hyperlink cancelHyperLinkFieldOne;

	/**
	 * This method is associated with the "Sign Up button" on the 
	 * create account page. Validates all fields before adding the 
	 * user to the database and display the "Account created" success 
	 * box. If an exception is thrown during validation an error message
	 * box will appear.
	 * @param event Sign up button clicked.
	 */
	@FXML
	private void createAccountPressed(ActionEvent event) {
		try {
			String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			String email = emailTextField.getText();
			String phoneNumber = phoneNumberTextField.getText();
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			String reEnteredPW = reEnteredPWTextField.getText();
			
            FormValidator validator = new FormValidator();
            
            // Check for valid credentials using the FormValidator class
            // An exception will be thrown from one of these classes if the 
            // parameter is invalid 
            validator.checkName(firstName);
            validator.checkName(lastName);
            
            validator.checkEmail(email);
            validator.checkPhoneNumber(phoneNumber);
            
            
            validator.isValidPasswordFSM(password);
            validator.isEqual(password, reEnteredPW);
            
            int numOfUsers = validator.checkForCredentialDuplicates(firstName, lastName, username, password);
            int newId = numOfUsers + 1;
            
            // id,firstName,LastName,Email,Username,Password,PhoneNumber,CurrentApartment,CurrentRoom,OrderHistory
			User user = new User(newId, firstName, lastName, email, username, password,phoneNumber, null, null, null);
            
            //Add object to database 
			addUserToDatabaseFile(user);
            
			displayAccountCreatedPopUpBox();
			
		} catch (Exception ex) {
			displayErrorMessage(ex.getMessage());
		} 
	}
	
	/**
	 * This is box that is displayed when a user is successfully 
	 * created. It has green text that says "Account created" and 
	 * a "done" button that will take you back to the sign in page.
	 * @throws IOException If error occurs during navigation
	 */
	@FXML
	private void displayAccountCreatedPopUpBox() throws IOException {
        
        Stage accountCreatedBox = new Stage();
        accountCreatedBox.initModality(Modality.APPLICATION_MODAL);
         
        Label label = new Label("Account Created!");
        label.setTextFill(Color.color(0.34, 0.64, 0.5));
        label.setFont(new Font(24));
        Button button = new Button("Done");
        
        VBox layout = new VBox(label, button);
        layout.setSpacing(12);
        layout.setAlignment(Pos.CENTER);
         
        Scene scene = new Scene(layout, 225, 100);  //layout, width, height
    
        accountCreatedBox.setTitle("Message");
        accountCreatedBox.setScene(scene);
        
        //Gets rid of users green resizable button 
        accountCreatedBox.resizableProperty().setValue(false);
        
        //Gets rid of the three buttons
        accountCreatedBox.initStyle(StageStyle.UNDECORATED);
        
        accountCreatedBox.show();
        
		//Asynchronous function that executes when the user pressed the done button
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            
        	public void handle(ActionEvent e) {

            	AnchorPane signInPane = null;
            	
				try {
					accountCreatedBox.hide();
					signInPane = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
				} catch (IOException ex) {
					System.out.println("THIS SHOULD NEVER BE THROWN");
				}
				
        		createAccountRootPane.getChildren().setAll(signInPane);
            }
        	
        };
  
        
        button.setOnAction(event);
	}
    
	/**
	 * If the user presses the blue "cancel" hyperlink at the bottom 
	 * this function will execute and send the user back to the sign in page.
	 * @param event Cancel hyperlink pressed
	 * @throws IOException If error occurs during navigation.
	 */
    @FXML
    private void signInPageCancelPressed(ActionEvent event) throws IOException {
    	Parent signInPage = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
    	createAccountRootPane.getChildren().setAll(signInPage);
    }
    
    /**
	 * Writes a user object to the end of the database file.
	 * @param newUser User to be written to database
	 * @throws IllegalArgumentException if file not found.
	 */
    private void addUserToDatabaseFile(User newUser) {
        try (FileWriter fw = new FileWriter("src/main/UserDB.txt", true); 
             BufferedWriter bw = new BufferedWriter(fw); 
             PrintWriter out = new PrintWriter(bw)) {

            out.println(newUser.toString());

        } catch (IOException ex) {
        	throw new IllegalArgumentException("User Couldn't be written to file");
        }
    }
    
	/**
	 * Alert box which is displayed whenever an exception is thrown
	 * @param message The exception message to be displayed in the 
	 * 				  alert box.
	 */
    private static void displayErrorMessage(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Invalid Credentials");
    	alert.setContentText(message);
    	alert.show();
    }
    
}
    