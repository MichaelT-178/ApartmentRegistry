package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
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
 * @author Leo Doak
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose Controller for the MapView Pane. Will display the information cards.
 */
public class MapViewController {
	
	/** The purple button. The Creek */
	@FXML
	private Button buttonOne;
	
	/** The light blue button. Seahawk Cove */
	@FXML
	private Button buttonTwo;
	
	/** The green button. Quad Apartments */
	@FXML
	private Button buttonThree;
	
	/** The orange button. The Lofts */
	@FXML
	private Button buttonFour;
	
	/** The red button. Campus Edge */
	@FXML
	private Button buttonFive;
	
	/** The yellow button. Wilmington Commons */
	@FXML
	private Button buttonSix;
	
	/** The dark blue button. Seahawk Retreat */
	@FXML
	private Button buttonSeven;
	
	/** The pink button. Cypress Pointe */
	@FXML
	private Button buttonEight;
	
	/** "Go Back" arrow will take you back to main page */
    @FXML
    private Hyperlink backHyperLink;
    
    /** For navigation purposes */
	@FXML 
	private Pane mapViewRootPane;
	
	/** To keep user signed in */
	private User user;
	
	/**
	 * Will initialize button actions on landing to display the correct information
	 */
    public void initialize() {
    			
    	buttonOne.setOnAction((e) -> { displayCard(1); });
    	buttonTwo.setOnAction((e) -> { displayCard(2); });
    	buttonThree.setOnAction((e) -> { displayCard(3); });
    	buttonFour.setOnAction((e) -> { displayCard(4); });
    	buttonFive.setOnAction((e) -> { displayCard(5); });
    	buttonSix.setOnAction((e) -> { displayCard(6); });
    	buttonSeven.setOnAction((e) -> { displayCard(7); });
    	buttonEight.setOnAction((e) -> { displayCard(8); });
    	
    }
	
	/**
	 * Will go back to main page when "Go Back" text clicked.
	 * @param event "Go Back" text clicked
	 * @throws IOException If error occurs during navigation
	 */
    @FXML
    private void backToMainLinkPressed(ActionEvent event) throws IOException {
    	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MainPage.fxml"));
		Parent mainPagePane = loader.load();
		
		MainPageController mainPageController = loader.getController();
		mainPageController.setUserOnMainPage(user);
		
		mapViewRootPane.getChildren().setAll(mainPagePane);
    }
    
    /**
     * Will display the card with the given id.
     * @param i The Id of the apartment that's information will be displayed
     */
    private void displayCard(int id) {
    	
    	ApartmentList apartmentListHelper = new ApartmentList();
    	int idx = id - 1;
    	Apartment apartment = apartmentListHelper.getByIdx(idx);
    	
    	Stage accountCreatedBox = new Stage();
		accountCreatedBox.initModality(Modality.APPLICATION_MODAL);
		
		//ALL LABELS SHOULD GO HERE
		Label apartmentName = new Label(apartment.getName()); 
		
		String theLocation = apartment.getLocation();
		String theDistance = apartment.getDistanceFromCampus() + " miles"; 
		String isSafe = apartment.getIsSafe() ? "Yes" : "No";
		String roomsAvailable = apartment.getAvailableRooms() + " rooms"; 
				
		String[] infoText = {"Location: ", "Distance from Campus: ", "Is Safe: ", "Available Rooms: "};
		String[] infoAns = {theLocation, theDistance, isSafe, roomsAvailable};
		
		TextFlow[] labels = new TextFlow[4];
				
		for (int j = 0; j < 4; j++) {
			Text theText = new Text(infoText[j]);
			Text theAnswer = new Text(infoAns[j]); 
			theText.setFont(new Font(14));
			theAnswer.setFont(new Font(14));
			theAnswer.setFill(Color.BLUE);
			labels[j] = new TextFlow(theText, theAnswer);
		}
		
		apartmentName.setFont(new Font(22));
		Button doneButton = new Button("Done");
		
		VBox infoBox = new VBox(labels[0], labels[1], labels[2], labels[3]);
		infoBox.setAlignment(Pos.CENTER_LEFT);
		infoBox.setSpacing(14);
		infoBox.setPadding(new Insets(12, 0, 19, 11)); //top, right, bottom, left
		
		FileInputStream inputstream = null;
		
		try {
			String path = "src/main/pics/pic" + apartment.getId() + ".jpg";
			inputstream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("THIS SHOULD NEVER BE DISPLAYED MAP VIEW ");
		} 
		

		Image image = new Image(inputstream); 
		ImageView apartmentView = new ImageView(image);
		
		apartmentView.setFitHeight(130); 
		apartmentView.setFitWidth(238); 
	
		
		HBox top = new HBox();
		top.getChildren().add(apartmentView);
		top.setStyle("-fx-border-color: black; -fx-border-insets:10 15 10 15; -fx-border-width:1.7");
		
		
		VBox layout = new VBox(apartmentName, top, infoBox, doneButton);
		//layout.setSpacing(12);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(0);
		 
		Scene scene = new Scene(layout, 268, 380);  //layout, width, height
		
		accountCreatedBox.setTitle("Information");
		accountCreatedBox.setScene(scene);
		
		//Gets rid of users green resizable button 
		accountCreatedBox.resizableProperty().setValue(false);
		accountCreatedBox.show();
	
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				accountCreatedBox.hide();
			}
		};
		
		doneButton.setOnAction(event);
	}
    
    /**
     * Sets the user on this page to keep them signed in.
     * @param user User currently signed in
     */
    public void setUserOnMapView(User user) {
    	this.user = user;
    }
}
    