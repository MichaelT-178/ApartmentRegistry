package main;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
* @author Michael Totaro
* @date 4/16/2023
* @section CSC 331 - 002
* @purpose This class was made to make the MainPageController less cluttered
*/
public class MainPageHelper {

	/**
	 * Displays additional information for the selected apartment 
	 * in the TableView. This box is displayed when the "See Room Info"
	 * button is clicked.
	 * @param apartment The selected apartment from the TableView
	 */
	public void displayMoreInfoCard(Apartment apartment) {

		Stage accountCreatedBox = new Stage();
		accountCreatedBox.initModality(Modality.APPLICATION_MODAL);

		Label apartmentName = new Label(apartment.getName());

		String theLocation = apartment.getLocation();
		String theDistance = String.valueOf(apartment.getDistanceFromCampus()) + " miles";
		String roomAmenities = apartment.getRoomAmenities().replace("|", ", ");
		String totalRooms = String.valueOf(apartment.getTotalRooms()) + " rooms";
		String roomsAvailable = String.valueOf(apartment.getAvailableRooms()) + " rooms";
		String phoneNumber = apartment.getPhoneNumber();
		String email = apartment.getEmail();

		String[] infoText = { "Location: ", "Distance from Campus: ", "Room Amenities: ", "Total Rooms: ",
				"Available Rooms: ", "Phone Number: ", "Email: " };
		String[] infoAns = { theLocation, theDistance, roomAmenities, totalRooms, roomsAvailable, phoneNumber, email };

		TextFlow[] labels = new TextFlow[7];

		for (int j = 0; j < 7; j++) {
			Text theText = new Text(infoText[j] + " ");
			Text theAnswer = new Text(infoAns[j]);
			theAnswer.setFill(Color.web("DEAA00"));
			labels[j] = new TextFlow(theText, theAnswer);
		}

		apartmentName.setFont(new Font(22));
		Button doneButton = new Button("Done");

		VBox infoBox = new VBox(labels[0], labels[1], labels[2], labels[3], labels[4], labels[5], labels[6]);
		infoBox.setAlignment(Pos.CENTER_LEFT);
		infoBox.setSpacing(10);
		infoBox.setPadding(new Insets(10, 0, 15, 10)); // top, right, bottom, left

		VBox layout = new VBox(apartmentName, infoBox, doneButton);
		// layout.setSpacing(12);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 236, 276); // layout, width, height

		accountCreatedBox.setTitle("More Info");
		accountCreatedBox.setScene(scene);

		// Gets rid of users green resizable button
		accountCreatedBox.resizableProperty().setValue(false);
		accountCreatedBox.show();

		//Hide box when user clicks done button
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				accountCreatedBox.hide();
			}
		};

		doneButton.setOnAction(event);

	}

	/**
	 * This pop up box is displayed when the "Filter by criteria" button is clicked.
	 * Creates and ApartmentList object and calls the appropriate filter methods based
	 * on which button the user selected. The apartmentListStr is set to the return string 
	 * value of the apartmentList filter methods and the listener is the MainPageController
	 * filterButtonPressed updates the TableView accordingly. Will display error message box
	 * if exception is thrown.
	 * @param apartmentListStr String to be set to filtered results. Listener in MainPageController
	 *                         keeps track of changes. Will be a list of apartment object seperated by
	 *                         the "%%%" substring 
	 */
	public void filterPopUpBox(final SimpleStringProperty apartmentListStr) {

		Stage accountCreatedBox = new Stage();
		accountCreatedBox.initModality(Modality.APPLICATION_MODAL);

		Label apartmentName = new Label("Filter Results");
		apartmentName.setFont(new Font(20));

		ToggleGroup buttons = new ToggleGroup();

		RadioButton r1 = new RadioButton("Filter by Distance");
		r1.setToggleGroup(buttons);
		RadioButton r2 = new RadioButton("Filter by Price");
		r2.setToggleGroup(buttons);
		RadioButton r3 = new RadioButton("Filter by Start Date");
		r3.setToggleGroup(buttons);
		RadioButton r4 = new RadioButton("Filter by Cleanliness"); //
		r4.setToggleGroup(buttons);
		RadioButton r5 = new RadioButton("Filter by Square Feet"); // Int
		r5.setToggleGroup(buttons);
		RadioButton r6 = new RadioButton("Remove All Filters");
		r6.setToggleGroup(buttons);

		Label input = new Label("Input: ");
		input.setFont(new Font(14));
		input.setPadding(new Insets(2, 1, 0, 0));

		TextField textField = new TextField();
		HBox inputInfo = new HBox(input, textField);
		textField.setPrefWidth(80);

		DatePicker datePicker = new DatePicker();
		datePicker.setPrefWidth(110);

		VBox radioButtons = new VBox(r1, r2, r3, r4, r5, r6, inputInfo);
		radioButtons.setAlignment(Pos.CENTER_LEFT);
		radioButtons.setSpacing(8);
		radioButtons.setPadding(new Insets(10, 0, 7, 10)); // top, right, bottom, left

		Button submitButton = new Button("Apply");

		inputInfo.setVisible(false);

		r1.addEventHandler(ActionEvent.ACTION, (e) -> {
			inputInfo.setVisible(true);
			input.setText("Distance (In Miles): ");
			// input.setStyle("-fx-text-fill: #DEAA00");
			textField.setText("");

			if (inputInfo.getChildren().contains(datePicker)) {
				inputInfo.getChildren().remove(datePicker);
				inputInfo.getChildren().add(textField);

			}
		});

		r2.addEventHandler(ActionEvent.ACTION, (e) -> {
			inputInfo.setVisible(true);
			input.setText("Average Price:  $");
			textField.setText("");

			if (inputInfo.getChildren().contains(datePicker)) {
				inputInfo.getChildren().remove(datePicker);
				inputInfo.getChildren().add(textField);

			}
		});

		r3.addEventHandler(ActionEvent.ACTION, (e) -> {
			input.setText("Start date: ");
			inputInfo.getChildren().remove(textField);
			inputInfo.getChildren().add(datePicker);

			if (inputInfo.getChildren().contains(textField)) {
				inputInfo.getChildren().remove(textField);
				inputInfo.getChildren().add(datePicker);
			}

			inputInfo.setVisible(true);
		});

		r4.addEventHandler(ActionEvent.ACTION, (e) -> {
			inputInfo.setVisible(false);
		});

		r5.addEventHandler(ActionEvent.ACTION, (e) -> {
			inputInfo.setVisible(true);
			input.setText("Square Feet: ");
			textField.setText("");

			if (inputInfo.getChildren().contains(datePicker)) {
				inputInfo.getChildren().remove(datePicker);
				inputInfo.getChildren().add(textField);
			}
		});

		r6.addEventHandler(ActionEvent.ACTION, (e) -> {
			inputInfo.setVisible(false);
		});

		VBox layout = new VBox(apartmentName, radioButtons, submitButton);
		// layout.setSpacing(12);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 236, 265); // layout, width, height

		accountCreatedBox.setTitle("Table Filter");
		accountCreatedBox.setScene(scene);

		// Gets rid of users green resizable button
		accountCreatedBox.resizableProperty().setValue(false);
		accountCreatedBox.show();

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				RadioButton selectedButton;
				String buttonText;

				try {
					selectedButton = (RadioButton) buttons.getSelectedToggle();
					buttonText = selectedButton.getText();
				} catch (NullPointerException ex) {
					return;
				}

				String filteredApartmentList = null;
				ApartmentList apartmentList = new ApartmentList();

				try {
					if (buttonText.contains("Distance")) { //If filter by distance 
						double distanceInMiles = Double.parseDouble(textField.getText());
						filteredApartmentList = apartmentList.filterByDistance(distanceInMiles);

					} else if (buttonText.contains("Price")) {//If filter by Price
						double price = Double.parseDouble(textField.getText());
						filteredApartmentList = apartmentList.filterByPrice(price);
						
					} else if (buttonText.contains("Start")) { //If filter by start date
						LocalDate date = datePicker.getValue();
						filteredApartmentList = apartmentList.filterByDate(date);
						
					} else if (buttonText.contains("Clean")) { //If filter by cleanliness
						filteredApartmentList = apartmentList.filterByCleanlines();
						
					} else if (buttonText.contains("Square")) { //If filter by Square feet
						int squareFeet = Integer.parseInt(textField.getText());
						filteredApartmentList = apartmentList.filterBySquareFt(squareFeet);

					} else if (buttonText.contains("Remove")) { //If filtered my remove all filters
						filteredApartmentList = apartmentList.getEntireApartmentListString();
					}

					else {
						// do Nothing
					}

					accountCreatedBox.hide();
					
					//If no results are found because a method returned null. A message will be displayed
					if (filteredApartmentList != null) {
						apartmentListStr.set(filteredApartmentList);
					} else {
						displayErrorMessage("No Results Found");
					}

				} catch (IllegalArgumentException ex) {
					displayErrorMessage(ex.getMessage());
				}

			}
		};

		submitButton.setOnAction(event);

	}

	/**
	 * Will display users past orders is a table style pop up box
	 * @param user User whose information will be displayed
	 */
	public void displayOrderHistoryBox(User user) {
	    	
	    Stage orderHistoryBox = new Stage();
	    orderHistoryBox.initModality(Modality.APPLICATION_MODAL);
			
			//ALL LABELS SHOULD GO HERE
		Label orderTitle = new Label("Order History");

		
		orderTitle.setFont(new Font(22));
		Button doneButton = new Button("Done");

		
		
		Text orderNum = new Text("Order #");
		Text name = new Text("Name");
		Text tier = new Text("Tier");
		Text cost = new Text("Cost");
		
		orderNum.setFill(Color.web("DEAA00"));
		name.setFill(Color.web("DEAA00"));      
		tier.setFill(Color.web("DEAA00"));      
		cost.setFill(Color.web("DEAA00"));      
		
		orderNum.setUnderline(true);
		name.setUnderline(true);
		tier.setUnderline(true);
		cost.setUnderline(true);
		
		orderNum.setFont(new Font(18));
		name.setFont(new Font(18));
		tier.setFont(new Font(18));
		cost.setFont(new Font(18));
		
		
		AnchorPane headers = new AnchorPane();
		headers.getChildren().addAll(orderNum, name, tier, cost);
	
		AnchorPane.setLeftAnchor(orderNum, 27.0);
		AnchorPane.setLeftAnchor(name, 158.0);
		AnchorPane.setLeftAnchor(tier, 281.5);
		AnchorPane.setLeftAnchor(cost, 365.0);
	
		
		VBox allOrders = new VBox();
		
		if (user.getOrderHistory() != null) {
			String[] allOrdersArray = user.getOrderHistory().split("%%");
					
			for (int i = 0; i < allOrdersArray.length; i++) {
				
				String[] anOrder = allOrdersArray[i].split("%");
				
				AnchorPane row = new AnchorPane();
			
				double aptPrice = Double.parseDouble(anOrder[2]);
				
				Label num = new Label(getIntAsString(i + 1));
				Label aptName = new Label(anOrder[0]);
				Label roomTier = new Label(anOrder[1]);
				Label price = new Label("$" + String.format("%,.1f", aptPrice));
				
				num.setFont(new Font(14));
				aptName.setFont(new Font(14));
				roomTier.setFont(new Font(14));
				
				row.getChildren().addAll(num, aptName, roomTier, price);
				
				AnchorPane.setLeftAnchor(num, 36.0);
				AnchorPane.setLeftAnchor(aptName, 115.0);
				AnchorPane.setLeftAnchor(roomTier, 270.0);
				AnchorPane.setLeftAnchor(price, 357.0);
			
				row.setPadding(new Insets(0, 0, 7, 0));
				
				allOrders.getChildren().add(row);
			}
		}
		
		
		VBox headersAndOrders = new VBox();
		headersAndOrders.getChildren().addAll(headers, allOrders);
		headersAndOrders.setSpacing(5);
		headersAndOrders.setPadding(new Insets(28, 0, 0, 4));
		
		
		AnchorPane yLayout = new AnchorPane();
		yLayout.getChildren().addAll(orderTitle, headersAndOrders, doneButton);
	
		AnchorPane.setTopAnchor(orderTitle, 20.0);
		AnchorPane.setLeftAnchor(orderTitle, 159.0);
		AnchorPane.setTopAnchor(headersAndOrders, 50.0);
		AnchorPane.setBottomAnchor(doneButton, 28.0);
		AnchorPane.setLeftAnchor(doneButton, 197.5);
		
		 
		Scene scene = new Scene(yLayout, 440, 330);  //layout, width, height
		
		orderHistoryBox.setTitle("Past Orders");
		orderHistoryBox.setScene(scene);
		
		//Gets rid of users green resizable button 
		orderHistoryBox.resizableProperty().setValue(false);
		orderHistoryBox.show();
			
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				orderHistoryBox.hide();
			}
		};
		
		doneButton.setOnAction(event);
	}
	
	/**
	 * Log out box will be displayed when the user clicks the "Log Out" button
	 * asking if they want to cancel or sign out. If they chose out they'll be 
	 * navigated back to the sign in page.
	 * @param mainPageRootPane The main page root pane where the box will be displayed
	 */
	public void displayLogOutBox(Pane mainPageRootPane) {

	    	
	    Stage signOutBox = new Stage();
	    signOutBox.initModality(Modality.APPLICATION_MODAL);
			
			//ALL LABELS SHOULD GO HERE
		Label apartmentName = new Label("Log out?");
			
		
		apartmentName.setFont(new Font(22));
		
		HBox optionButtons = new HBox();
		Button signOutButton = new Button("Log Out");
		Button cancelButton = new Button("Cancel");
		
		optionButtons.getChildren().add(cancelButton);
		optionButtons.getChildren().add(signOutButton);
		optionButtons.setAlignment(Pos.CENTER);
		optionButtons.setSpacing(16);
		
		Text message = new Text("Are you sure you want to sign out?");
		message.setFont(new Font(15));
		
		VBox layout = new VBox(apartmentName, message, optionButtons);
		//layout.setSpacing(12);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(10);
		 
		Scene scene = new Scene(layout, 250, 140);  //layout, width, height
		
		signOutBox.setScene(scene);
		
		//Gets rid of users green resizable button 
		signOutBox.resizableProperty().setValue(false);
		signOutBox.show();
			
		EventHandler<ActionEvent> logOutEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
		    	Parent signInPane = null;
				try {
					signInPane = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	mainPageRootPane.getChildren().setAll(signInPane);
		    	signOutBox.hide();
			}
		};
		
		EventHandler<ActionEvent> cancelEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				signOutBox.hide();
			}
		};
		
		signOutButton.setOnAction(logOutEvent);
		cancelButton.setOnAction(cancelEvent);
	}

	/**
	 * If an exception is thrown this pop up box will be displayed
	 * @param message Message to displayed in pop up box
	 */
	private static void displayErrorMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setContentText(message);
		alert.show();
	}
	
	/**
	 * Helper function for the displayOrderHistoryBox method. Will return a
	 * string of the int parameter
	 * @param idx The passed integer 
	 * @return The string representation of the integer 
	 * @throws IllegalArgumentException If the integer is bigger than 9
	 */
	private static String getIntAsString(int idx) {
		if (idx > 9) {
			throw new IllegalArgumentException("Can't have more than 10 past orders");
		}
		
		String[] arr = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
		return arr[idx];
	}

}