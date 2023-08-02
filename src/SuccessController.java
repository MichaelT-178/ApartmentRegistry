package main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;

/**
 * @author Jalen Wilson
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose Controller for the Success Page. Writes the new information 
 *          to the ApartmentData.txt file and the UserDB.txt file.
 */
public class SuccessController {
	/** Text that says "You have successfully register for 'apartment name' */
	@FXML
	private Label theMessage;
	
	/** Hyperlink that will take you back to the main page */
	@FXML
	private Hyperlink mainHyperLinkField;
	
	/** For navigation purposes */
	@FXML
	private Pane successRootPane;

	/** Apartment user is going to rent */
	private Apartment apartment;

	/** Currently signed in user */
	private User user;

	/**
	 * When user clicks the hyperlink that says "Go Back to Main Page" the
	 * user will continue to be signed in and navigate back to the main page.
	 * @param event Hyperlink pressed
	 * @throws IOException Thrown if error occurs during navigation
	 */
	@FXML
	private void mainPageLink(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainPage.fxml"));
		Parent mainPage = loader.load();

		MainPageController mainPageController = loader.getController();
		mainPageController.setUserOnMainPage(user);

		successRootPane.getChildren().setAll(mainPage);
	}

	/**
	 * Sets the text that says "You have successfully register for *apartment name*" to 
	 * display the correct apartment name.
	 * @param apartment The apartment the user rented.
	 */
	public void setMessage(Apartment apartment) {
		theMessage.setMinWidth(600.0);
		theMessage.setAlignment(Pos.CENTER);

		String message = "You have successfully registered for " + apartment.getName() + "!";
		theMessage.setText(message);

		this.apartment = apartment;

	}

	/**
	 * Will call the appropriate methods to change the users currentAparment, currentRoom, and 
	 * orderHistory in the UserDB.txt file database. Will also change the available rooms in 
	 * both the Users old and new apartment as appropriate.
	 * @param order The users order
	 */
	public void updateOrderHistory(String order) {

		String oldAptName = "";
		String allOrders = "";
		
		if (user.getOrderHistory() != null) {
			String[] orderHistoryArr = user.getOrderHistory().split("%%");
			String[] lastOrder = orderHistoryArr[orderHistoryArr.length - 1].split("%");
			oldAptName = lastOrder[0];
			
			allOrders = user.getOrderHistory() + "%%" + order;
		} else {
			allOrders = order;
		}
				

		int roomNum = (apartment.getTotalRooms() - apartment.getAvailableRooms()) + 1;

		String room = "Room " + roomNum;

		user.setCurrentApartment(apartment.getName());
		user.setOrderHistory(allOrders);
		user.setCurrentRoom(room);

		try {
			modifyUserInDateBaseFile();
		} catch (IllegalArgumentException ex) {
			displayErrorMessage(ex.getMessage());
		}
		
		ApartmentList helper = new ApartmentList();

		Apartment oldApartment = helper.getByName(oldAptName);

		if (oldApartment != null) {
			
			// if the apartments aren't the same increment and decrement room size appropriately
			if (oldApartment.getId() != apartment.getId()) {
				int decrementedAvailableRooms = apartment.getAvailableRooms() - 1;
				int incrementedAvailableRooms = oldApartment.getAvailableRooms() + 1;

				apartment.setAvailableRooms(decrementedAvailableRooms);
				oldApartment.setAvailableRooms(incrementedAvailableRooms);

				if (apartment.getAvailableRooms() == 0) {
					apartment.setIsAvailable(false);
				}

				try {
					modifyApartmentsInDatabaseFile(oldApartment);
				} catch (IllegalArgumentException ex) {
					displayErrorMessage(ex.getMessage());
				}
			}
		} else {
			int decrementedAvailableRooms = apartment.getAvailableRooms() - 1;
			apartment.setAvailableRooms(decrementedAvailableRooms);
			
			if (apartment.getAvailableRooms() == 0) {
				apartment.setIsAvailable(false);
			}

			try {
				modifyApartmentsInDatabaseFile(null);
			} catch (IllegalArgumentException ex) {
				displayErrorMessage(ex.getMessage());
			}
			
		}
	}

	/**
	 * Add the users new currentApartment, currentRoom, and orderHistory to the UserDB.txt
	 * database.
	 */
	private void modifyUserInDateBaseFile() {

		try {
			BufferedReader file = new BufferedReader(new FileReader("src/main/UserDB.txt"));
			StringBuffer inputBuffer = new StringBuffer();
			String line;

			while ((line = file.readLine()) != null) {

				String[] lineArr = line.split(",");

				if (user.getFirstName().equals(lineArr[1]) && user.getLastName().equals(lineArr[2])) {
					line = user.toString();
				}

				inputBuffer.append(line);
				inputBuffer.append('\n');
			}

			file.close();

			FileOutputStream fileOut = new FileOutputStream("src/main/UserDB.txt");
			fileOut.write(inputBuffer.toString().getBytes());
			fileOut.close();

		} catch (Exception e) {
			throw new IllegalArgumentException("Could not write user to file");
		}

	}

	/**
	 * Change the Users old apartment and new current apartment availability as
	 * appropriate in the ApartmentData.txt file.
	 * @param oldApartment The users old apartment
	 * @throws IllegalArgumentException If file can't be written to.
	 */
	private void modifyApartmentsInDatabaseFile(Apartment oldApartment) {

		try {
			
			BufferedReader file = new BufferedReader(new FileReader("src/main/ApartmentData.txt"));
			StringBuffer inputBuffer = new StringBuffer();
			String line;

			while ((line = file.readLine()) != null) {
				
				String[] lineArr = line.split(",");
				
				if (oldApartment != null) {
					if (oldApartment.getName().equals(lineArr[1])) {
						line = oldApartment.toString();
					}
				}

				if (apartment.getName().equals(lineArr[1])) {
					line = apartment.toString();
				}

				inputBuffer.append(line);
				inputBuffer.append('\n');
			}

			file.close();

			FileOutputStream fileOut = new FileOutputStream("src/main/ApartmentData.txt");
			fileOut.write(inputBuffer.toString().getBytes());
			fileOut.close();

		} catch (Exception e) {
			throw new IllegalArgumentException("Could not write to file");
		}
	}

	/**
	 * Keep the user signed in throughout pane navigations
	 * @param user User currently signed in
	 */
	public void setUserOnSuccessPage(User user) {
		this.user = user;
	}

	/**
	 * Display error message if exception is thrown.
	 * @param message Error message to be displayed.
	 */
	private static void displayErrorMessage(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Invalid Credentials");
    	alert.setContentText(message);
    	alert.show();
    }

}