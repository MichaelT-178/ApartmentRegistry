package main;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.BackgroundFill;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jalen Wilson
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose Controller class for the register pane. Validates fields.
 */
public class RegisterController {
	
	/** Register for 'Apartment name' Apartment */
	@FXML 
	private Label theTitle;
		
	@FXML
	private DatePicker moveInDatePicker;
	
	@FXML
	private DatePicker moveOutDatePicker;
	
	@FXML
	private TextField amenitiesTextField;
	
	/** The room type selection component */
	@FXML
	private HBox roomTypeHBox;
	
	@FXML
	private TextField phoneNumberTextField;
	
	@FXML
	private Pane registerRootPane;
	
	/** Cancel hyperlink */
    @FXML
    private Hyperlink cancelHyperLinkField;
    
    /** Apartment being checked out */
    private Apartment apartment;
    
    /** Currently signed in user */
    private User user;
    
    /** Whether the user agreed to lease or not */
    private boolean userAgreedToLease;
	
    /**
     * Validates all fields using if statements and the FormValidator class.
     * If exception not thrown display signature box. If an exception is thrown display 
     * an error box.
     * @param event Button that says "Register" pressed"
     * @throws IOException Thrown if error occurs during navigation
     * @throws IllegalArgumentException If fields contains invalid information
     */
	@FXML
	private void registerButtonPressed(ActionEvent event) throws IOException {
		
		try {
			
			LocalDate moveInDate = moveInDatePicker.getValue();
			LocalDate moveOutDate = moveOutDatePicker.getValue();
			String amenities = amenitiesTextField.getText();
			String phoneNumber = phoneNumberTextField.getText();
		    
		    FormValidator formValidator = new FormValidator();
		    
		    formValidator.checkRegistrationFormDates(moveInDate, moveOutDate, apartment);
		    
		    if (amenities.equals("")) { 
		    	throw new IllegalArgumentException("Please add at least one amenity"); 
		    }
		    
		    //Check tier 
			RadioButton r1 = (RadioButton) roomTypeHBox.getChildren().get(0);
			RadioButton r2 = (RadioButton) roomTypeHBox.getChildren().get(1);
			RadioButton r3 = (RadioButton) roomTypeHBox.getChildren().get(2);
			
			int price = 0;
			String tier = "";
			
			if (r1.isSelected()) {
				price = (int) apartment.getMinPrice();
				tier = "Standard";
			} 
			else if (r2.isSelected()) {
				price = (int) apartment.getMiddlePrice();
				tier = "Deluxe";
			}
			else if (r3.isSelected()) {
				price = (int) apartment.getMaxPrice();
				tier = "Gold";
			} else {
				throw new IllegalArgumentException("Please select a room type");
			}
			
			//Check phone number
		    formValidator.checkPhoneNumber(phoneNumber);
	
		    
		    String order = apartment.getName() + "%" + tier + "%" + price;
		    
		    System.out.println("User's Name: " + user.getFullName());
		    
		    displayLeaseSignature(order);
			
		} catch (IllegalArgumentException ex) {
			displayErrorMessage(ex.getMessage());
		}

	}
	
	/**
	 * If "Cancel" hyperlink pressed the User will be navigated back to the page
	 * @param event Cancel hyperlink pressed.
	 * @throws IOException If error occurs during navigation
	 */
    @FXML
    private void cancelLinkPressed(ActionEvent event) throws IOException {
    	
    	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainPage.fxml"));
		Parent mainPagePane = loader.load();
		
		MainPageController mainPageController = loader.getController();
		mainPageController.setUserOnMainPage(user);
		
		registerRootPane.getChildren().setAll(mainPagePane);
    }
    
    /**
     * Sets the text that Says 'Register for 'Apartment Name' Apartment'
     * @param apartment Apartment selected from TableView
     */
    public void setTitleText(Apartment apartment) {
    	theTitle.setMinWidth(600.0);
    	theTitle.setAlignment(Pos.CENTER);
    	
    	String ending = apartment.getName().contains("Apartment") ? "" : " Apartment";
    			
    	String title = "Register for " + apartment.getName() + ending;
		theTitle.setText(title);
		
		this.apartment = apartment;
    }
    
    /**
     * When user lands of page set the textfield's highlight to be gold when not focused
     * and create the room type selection component. Also sets the userAgreeToLease field
     * as false.
     */
    public void initialize() {
		
    	setUserAgreedToLease(false);
    	
    	moveInDatePicker.focusedProperty().addListener((o1, o2, focused1) -> { 
    		String formatTxt = "-fx-border-color: " + (focused1 ? "null;" : "#DEAA00;");
    		moveInDatePicker.setStyle(formatTxt);
    	});
    	
    	moveOutDatePicker.focusedProperty().addListener((o1, o2, focused2) -> { 
    		String formatTxt = "-fx-border-color: " + (focused2 ? "null;" : "#DEAA00;");
    		moveOutDatePicker.setStyle(formatTxt);
    	});
    	
    	amenitiesTextField.focusedProperty().addListener((o1, o2, focused3) -> { 
    		String formatTxt = "-fx-border-color: " + (focused3 ? "null;" : "#DEAA00;");
    		amenitiesTextField.setStyle(formatTxt);
    	});
    	
    	phoneNumberTextField.focusedProperty().addListener((o1, o2, focused4) -> { 
    		String formatTxt = "-fx-border-color: " + (focused4 ? "null;" : "#DEAA00;");
    		phoneNumberTextField.setStyle(formatTxt);
    	});
    
    	
		ToggleGroup buttons = new ToggleGroup();
		
		
		RadioButton r1 = new RadioButton("Standard");
		r1.setToggleGroup(buttons);
		r1.setPadding(new Insets(6.5, 8, 0, 10)); //top, right, bottom, left
		RadioButton r2 = new RadioButton("Deluxe");
		r2.setToggleGroup(buttons);
		r2.setPadding(new Insets(6.5, 8, 0, 0));
		RadioButton r3 = new RadioButton("Gold");
		r3.setToggleGroup(buttons);
		r3.setPadding(new Insets(6.5, 8, 0, 0));
		
		roomTypeHBox.getChildren().add(r1);
		roomTypeHBox.getChildren().add(r2);
		roomTypeHBox.getChildren().add(r3);
		
		
		Button questionMarkButton = new Button();
		
		questionMarkButton.setText("?");
		questionMarkButton.setFont(new Font(15));
		questionMarkButton.setStyle("-fx-background-radius: 0 12 12 0; -fx-background-color: black; -fx-text-fill: #DEAA00;");
		
		questionMarkButton.setPrefHeight(31);
		questionMarkButton.setPrefWidth(27);
		questionMarkButton.setOnAction(e -> { displayTierPricingCard(); });

		roomTypeHBox.getChildren().add(questionMarkButton);
		roomTypeHBox.setSpacing(14.5);
		
		
    }
	
    /**
     * When the user clicks the gold question mark in the black box next 
     * to the room type selection HBox this card will be displayed that 
     * shows the various tier pricings.
     */
	public void displayTierPricingCard() {
		
		Stage pricingTierBox = new Stage();
		pricingTierBox.initModality(Modality.APPLICATION_MODAL);

		Label theTitle = new Label("Pricing");
		
		String standardPrice = String.format("%,.1f", apartment.getMinPrice());
		String deluxePrice = String.format("%,.1f", apartment.getMiddlePrice());
		String goldPrice = String.format("%,.1f", apartment.getMaxPrice());
				
		String[] infoText = {"Standard Tier: ", "Deluxe Tier: ", "Gold Tier: "};
		String[] infoAns = {standardPrice, deluxePrice, goldPrice};
		
		TextFlow[] labels = new TextFlow[3];
				
		for (int j = 0; j < 3; j++) {
			Text theText = new Text(infoText[j]);
			Text theAnswer = new Text("$" + infoAns[j]); 
			theAnswer.setFill(Color.BLUE); 
			theText.setFont(new Font(14));
			theAnswer.setFont(new Font(14));
			labels[j] = new TextFlow(theText, theAnswer);
		}
		
		theTitle.setFont(new Font(20));
		Button doneButton = new Button("Done");
		
		VBox infoBox = new VBox(labels[0], labels[1], labels[2]);
		infoBox.setAlignment(Pos.CENTER_LEFT);
		infoBox.setSpacing(10);
		infoBox.setPadding(new Insets(10, 0, 15, 12)); //top, right, bottom, left
		
		VBox layout = new VBox(theTitle, infoBox, doneButton);
		//layout.setSpacing(12);
		layout.setAlignment(Pos.CENTER);
		 
		Scene scene = new Scene(layout, 232, 170);  //layout, width, height
		
		pricingTierBox.setTitle("Room Tiers");
		pricingTierBox.setScene(scene);
		pricingTierBox.resizableProperty().setValue(false);
		pricingTierBox.show();
	
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				pricingTierBox.hide();
			}
		};
		
		doneButton.setOnAction(event);
		
	}
	
	/**
	 * Will display box that has a field for users signature and a hyperlink to view the lease.
	 * @param order The users current order. Looks like aptName%tier%price
	 * @throws IllegalArgumentException If the user tries to sign before viewing lease
	 * @throws IllegalArgumentException If the entered name doesn't match the name of the currently 
	 * 									signed in user
	 * @throws IllegalArgumentException If the entered name isn't formatted correctly.
	 */
	private void displayLeaseSignature(String order) {
		
		Stage leaseBox = new Stage();
		leaseBox.initModality(Modality.APPLICATION_MODAL);

		Label theTitle = new Label(apartment.getName() + " Lease");
	
		
		theTitle.setFont(new Font(20));
		
		HBox signField = new HBox();
		
		//Sign Field
		TextField nameField = new TextField();
		Button signButton = new Button("Sign");
		
		signField.getChildren().add(nameField);
		signField.getChildren().add(signButton);
		signField.setSpacing(13);
		signField.setPadding(new Insets(0, 5, 0, 28)); //top, right, bottom, left
		
		Hyperlink viewLink = new Hyperlink("View Lease");
		viewLink.setStyle("-fx-border-width: 0px");
		viewLink.setUnderline(true);
		viewLink.setFont(new Font(13.75));
	
		VBox layout = new VBox(theTitle, signField, viewLink);
		
		layout.setSpacing(13);
		layout.setAlignment(Pos.CENTER);
		 
		Scene scene = new Scene(layout, 270, 146);  //layout, width, height
		
		leaseBox.setTitle("Signature");
		leaseBox.setScene(scene);
		leaseBox.resizableProperty().setValue(false);
		leaseBox.show();
	
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				try {
					
					if (!userAgreedToLease) {
						throw new IllegalArgumentException("Please view and agree to the lease before signing");
					}
					
					String name = nameField.getText();
					System.out.println("Name " + name);
					System.out.println("Sign Button Pressed");
					
					
					String[] nameArr = name.split(" ");
					String firstName = null;
					String lastName = null;
					
					if (nameArr.length == 3) { //If has a middle initial
						firstName = nameArr[0];
						lastName = nameArr[2];
					} else if (nameArr.length == 2) {
						firstName = nameArr[0];
						lastName = nameArr[1];
					} else {
						throw new IllegalArgumentException("Please format name correctly (First Name Last Name)");
					}
					
					
					if (!user.getFirstName().equals(firstName) || !user.getLastName().equals(lastName)) {
						throw new IllegalArgumentException("The name you entered doesn't match user information");
					}
					
					
					leaseBox.hide();
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("Success.fxml"));
					Parent successPage = loader.load();
					
					SuccessController successController = loader.getController();
					successController.setUserOnSuccessPage(user);
					successController.setMessage(apartment);
					successController.updateOrderHistory(order);
				
					System.out.println(order);
					
					registerRootPane.getChildren().setAll(successPage);
				} catch (IllegalArgumentException | IOException ex) {
					displayErrorMessage(ex.getMessage());
				}

			}
		};
		
		EventHandler<ActionEvent> viewEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				viewLease(leaseBox);
			}
		};
		
		signButton.setOnAction(event);
		viewLink.setOnAction(viewEvent);
		
		
	}
	
	/**
	 * View the lease. Will have picture of lease, a red decline button,
	 * and a green Agree button. When the user clicks agree the userAgreedToLease 
	 * field will be set to true.
	 * @param leaseBoxStage The signature stage that will be closed along with the viewLease 
	 *        stage if the user clicks "decline"
	 */
	private void viewLease(Stage leaseBoxStage) {
   
		Stage leaseViewBox = new Stage();
		leaseViewBox.initModality(Modality.APPLICATION_MODAL);


		FileInputStream inputstream = null;
		
		try {
			String path = "src/main/leases/lease" + apartment.getId() + ".jpg";
			inputstream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("THIS SHOULD NEVER BE DISPLAYED 1");
		} 
		
		
		
		HBox imageBox = new HBox();
		
		Image leaseImage = new Image(inputstream); 
		ImageView leaseImageView = new ImageView(leaseImage);

		//THIS CHANGES width and height of image
		leaseImageView.setFitHeight(450); 
		leaseImageView.setFitWidth(350); 
		
		imageBox.getChildren().add(leaseImageView);
		imageBox.setPadding(new Insets(1.5, 0, 1.5, 0)); //top, right, bottom, left. Increase top and bottom increases bottom and top border 
		imageBox.setAlignment(Pos.CENTER);
		
		VBox finalImageBox = new VBox();
		
		finalImageBox.getChildren().add(imageBox);
																			//Increasing right and left decreases borders black sides
		finalImageBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0, 48.5, 0, 48.5)))); 
		
		HBox buttons = new HBox();
		
		Button agreeButton = new Button("Agree");
		Button declineButton = new Button("Decline");
		agreeButton.setStyle("-fx-background-color: #0A9000; -fx-text-fill: white");
		declineButton.setStyle("-fx-background-color: #C30000; -fx-text-fill: white");
		
		buttons.getChildren().add(declineButton);
		buttons.getChildren().add(agreeButton);
		
		buttons.setSpacing(20);
		buttons.setPadding(new Insets(0, 0, 0, 153));
		
		
		VBox layout = new VBox(finalImageBox, buttons);
		layout.setSpacing(10);
		layout.setAlignment(Pos.CENTER);
		
		//This changes the height and width of box. Do NOT change layout
		Scene scene = new Scene(layout, 450, 510);  //layout, width, height
		
		leaseViewBox.setTitle("Lease Agreement");
		leaseViewBox.setScene(scene);
		leaseViewBox.resizableProperty().setValue(false);
		leaseViewBox.show();
	
		EventHandler<ActionEvent> agreeEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				setUserAgreedToLease(true);
				leaseViewBox.hide();
			}
		};
		
		EventHandler<ActionEvent> declinedEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				leaseViewBox.hide();
				leaseBoxStage.hide();
			}
		};
	
	
		agreeButton.setOnAction(agreeEvent);
		declineButton.setOnAction(declinedEvent);
		
	}
	
	/**
	 * Keeps the user logged in while navigating through panes
	 * @param user The signed in user
	 */
	public void setUserOnRegisterPage(User user) {
		this.user = user;
	}
	
	/**
	 * Whether the user viewed the lease of not. Set to false initially 
	 * and set to true if the user clicks the link
	 * @param viewed Whether the user viewed the lease of not
	 */
	public void setUserAgreedToLease(boolean viewed) {
		this.userAgreedToLease = viewed;
	}
	
	/**
	 * Error box that gets displayed if exception is thrown.
	 * @param message Error message that will be displayed in box.
	 */
    private static void displayErrorMessage(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Invalid Input");
    	alert.setContentText(message);
    	alert.show();
    }
    
}