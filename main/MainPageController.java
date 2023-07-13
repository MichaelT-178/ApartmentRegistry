package main;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Parent;


/**
* @author Leo Doak
* @date 4/16/2023
* @section CSC 331 - 002
* @purpose Controller for the buttons and TableView on the main page
*/
public class MainPageController {
	
	/** Table View */
	@FXML
	private TableView<Apartment> apartmentTableView;
	
	/** String binding for name column */
	@FXML
	private TableColumn<Apartment, String> nameColumn;
	
	/** String binding for square feet column */
	@FXML
	private TableColumn<Apartment, String> sqFtColumn;
	
	/** String binding for price column */
	@FXML
	private TableColumn<Apartment, String> priceColumn;
	
	/** String binding for available column */
	@FXML
	private TableColumn<Apartment, String> availableColumn;
	
	/** String binding for start date column */
	@FXML
	private TableColumn<Apartment, String> startDateColumn;
	
	/** String binding for end date column */
	@FXML
	private TableColumn<Apartment, String> endDateColumn;
	
	/** For navigation purposes */
	@FXML 
	private Pane mainPageRootPane;
	
	/** User passed from sign in page */
	private User user;
	
	/**
	 * When the "Room Info" button is pressed, pass the selected apartment to the main
	 * page helper to display the pop up box which displays the apartments information. 
	 * If no apartment is selected from the table view display an error box.
	 * @param event Room Info button clicked
	 */
	@FXML
    private void roomInfoButtonPressed(ActionEvent event) {
		MainPageHelper mainPageHelper = new MainPageHelper();
		Apartment option = apartmentTableView.getSelectionModel().getSelectedItem();
		
		if (option != null) {
			mainPageHelper.displayMoreInfoCard(option);
			
		} else {
			displayErrorMessage("Please select an apartment from the menu to see more information.");
		}
    }
    
	/**
	 * Creates and passes an SimpleStringProperty called apartments and passes
	 * it to the filterPopUpBox method in the mainPageHelper. The apartments string 
	 * will be a string of apartment objects separated by the substring "%%%". When 
	 * the string is modified in the filterBoxUpBox method the string will be split, 
	 * iterated over, and the objects in the string will be added to the tableView. 
	 * @param event Filter by Criteria button pressed.
	 */
	@FXML
    private void filterButtonPressed(ActionEvent event) {
		MainPageHelper mainPageHelper = new MainPageHelper();
		SimpleStringProperty apartments = new SimpleStringProperty();
		
		ApartmentList helper = new ApartmentList();
		
		//Lister event is asynchronous, meaning it doesn't run with the main thread. When user clicks "Apply"
		//the string will change.
		apartments.addListener(new ChangeListener<String>() {
			
		    public void changed(ObservableValue<? extends String> obs, String oldValue, String apartmentString) { 
		        
		    	
				ObservableList<Apartment> tableViewApartments = FXCollections.observableArrayList();
				
				for (String apartmentStr : apartmentString.split("%%%")) {

					tableViewApartments.add(helper.stringToApartment(apartmentStr));
					
				}
				
				apartmentTableView.setItems(tableViewApartments);
				
		    }
		    
		});
		
		mainPageHelper.filterPopUpBox(apartments);
    }
    
	/**
	 * When "View on Map" button pressed User will be navigated to map view.
	 * @param event Map button pressed
	 * @throws IOException If error occurs during navigation
	 */
	@FXML
    private void mapButtonPressed(ActionEvent event) throws IOException {
    	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MapView.fxml"));
		Parent mapViewPane = loader.load();
		
		MapViewController mapViewController = loader.getController();
		mapViewController.setUserOnMapView(user); //Pass user object to map view 
		
		mainPageRootPane.getChildren().setAll(mapViewPane);
    }
	
	
	/**
	 * When order history button pressed the displayOrderHistoryBox method
	 * will be called on the MainPageHelper class to display the pop up box.
	 * @param event Order history button pressed
	 */
	@FXML
    private void orderHistoryButtonPressed(ActionEvent event) { 
			
		MainPageHelper mainPageHelper = new MainPageHelper();
		mainPageHelper.displayOrderHistoryBox(user);
		
    }
	
	/**
	 * When log out button pressed a pop up box will be displayed using 
	 * the mainPage helper and the if the user clicks "log out" they will 
	 * navigate back to the sign in page.
	 * @param event Log Out button clicked
	 * @throws IOException If error occurs during navigation.
	 */
	@FXML
    private void logOutButtonPressed(ActionEvent event) throws IOException {
		
    	MainPageHelper mainPageHelper = new MainPageHelper();
    	mainPageHelper.displayLogOutBox(mainPageRootPane);
    	
    }
	
	/**
	 * When register button pressed navigate to the Register page. If exception occurs 
	 * display error box.
	 * @param event "Register" button pressed
	 * @throws IOException If error occurs during navigation
	 * @throws IllegalArgumentException If the user hasn't selected an option from the menu
	 * @throws IllegalArgumentException If the selected apartment is unavailable 
	 */
	@FXML
    private void registerButtonPressed(ActionEvent event) throws IOException {

		Apartment option = apartmentTableView.getSelectionModel().getSelectedItem();
		
		try {
			if (option == null) { 
				throw new IllegalArgumentException("Please select an apartment from the menu to register"); 
			}
			
			if (!option.getIsAvailable()) {
				throw new IllegalArgumentException("This apartment does not currently have any available rooms."); 
			}
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Register.fxml"));
			Parent registerPane = loader.load();
			
			main.RegisterController registerController = loader.getController();
			registerController.setTitleText(option);
			registerController.setUserOnRegisterPage(user);
			
			mainPageRootPane.getChildren().setAll(registerPane);
			
		} catch (IllegalArgumentException e) {
			displayErrorMessage(e.getMessage());
		}
		
    }
	
	/**
	 * Set all the data in the table view when the user lands on the main page.
	 * All apartments will initially be displayed in the table.
	 */
	public void initialize() {
		
		//Get the data 
		nameColumn.setCellValueFactory(data -> data.getValue().getNameString());
		sqFtColumn.setCellValueFactory(data -> data.getValue().getSquareFeetString());
		priceColumn.setCellValueFactory(data -> data.getValue().getPriceRange());
		availableColumn.setCellValueFactory(data -> data.getValue().getIsAvailableString());
		startDateColumn.setCellValueFactory(data -> data.getValue().getStartDateString());
		endDateColumn.setCellValueFactory(data -> data.getValue().getEndDateString());
		
		//Populate the table with the apartment data
		apartmentTableView.setItems(getApartments());

	}
	
	/**
	 * Creates and returns an observable list of all 8 aparmtents to be displayed
	 * in the TableView.
	 * @return An ObservableList of all 8 apartments 
	 */
	private ObservableList<Apartment> getApartments() {

		ObservableList<Apartment> apartments = FXCollections.observableArrayList();
		
		//Populate the observable list
		ApartmentList apartmentList = new ApartmentList();
				
		for (Apartment apartment : apartmentList.getApartmentList()) {
			apartments.add(apartment);
		}
		
		return apartments;
	}
	
	/**
	 * Displays an error message box
	 * @param message Error message to be displayed in box.
	 */
    private static void displayErrorMessage(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("No Option Selected");
    	alert.setContentText(message);
    	alert.show();
    }
    
    
    /**
     * Set the user that was passed to the page.
     * @param user User that is currently signed in.
     */
	public void setUserOnMainPage(User user) {
		this.user = user;
	}
	
}