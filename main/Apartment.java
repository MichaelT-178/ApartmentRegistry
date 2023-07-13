package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Jalen Wilson
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose Creates an Apartment object.
 */
 public class Apartment {

	/** Unique integer to identify each apartment */
	private int id;
	/** Name of apartment */
	private String name;
	/** How big a room is in the aparmtent. Example: 789 ft*/
	private int squareFeet;
	/** Whether there are rooms available */
	private boolean isAvailable;
	/** Location of the apartment */
	private String location;
	/** Distance apartment is from campus in miles. Examples: 0.8, 2.5, 1.7 */
	private double distanceFromCampus;
	/** List of room amenities seperated by '|' characters */
	private String roomAmenities;
	/** Total rooms the apartment has */
	private int totalRooms;
	/** Rooms available in the apartment */
	private int availableRooms;
	/** Phone number of the apartment */
	private String phoneNumber;
	/** Email of the apartment */
	private String email;
	/** Price for apartments standard tier */
	private double minPrice;
	/** Price for apartments deluxe tier */
	private double middlePrice;
	/** Price for apartments gold tier */
	private double maxPrice;
	/** Start date for lease agreement. Formatted YYYY/MM/DD. Ex. 2023-05-03 */
	private LocalDate startDate;
	/** End date for lease agreement. Formatted YYYY/MM/DD. Ex. 2023-05-03 */
	private LocalDate endDate;
	/** True if the apartment is clean else false. */
	private boolean isClean;
	/** True if the safe is clean else false. */
	private boolean isSafe;

	/**
	 * Constructs an Apartment object
	 */
	public Apartment(int id, String name, int squareFeet, boolean isAvailable, String location, double distanceFromCampus, String roomAmenities, int totalRooms, int availableRooms, String phoneNumber, String email, double minPrice, double middlePrice, double maxPrice, LocalDate startDate, LocalDate endDate, boolean isClean, boolean isSafe) {
		this.id = id;
		this.name = name;
		this.squareFeet = squareFeet;
		this.isAvailable = isAvailable;
		this.location = location;
		this.distanceFromCampus = distanceFromCampus;
		this.roomAmenities = roomAmenities;
		this.totalRooms = totalRooms;
		this.availableRooms = availableRooms;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.minPrice = minPrice;
		this.middlePrice = middlePrice;
		this.maxPrice = maxPrice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isClean = isClean;
		this.isSafe = isSafe;
	}

	// All getter methods for an apartment object.

	public int getId() { return this.id; }

	public String getName() { return this.name; }

	public int getSquareFeet() { return this.squareFeet; }

	public boolean getIsAvailable() { return this.isAvailable; }

	public String getLocation() { return this.location; }

	public double getDistanceFromCampus() { return this.distanceFromCampus; }

	public String getRoomAmenities() { return this.roomAmenities; }

	public int getTotalRooms() { return this.totalRooms; }

	public int getAvailableRooms() { return this.availableRooms; }

	public String getPhoneNumber() { return this.phoneNumber; }

	public String getEmail() { return this.email; }

	public double getMinPrice() { return this.minPrice; }

	public double getMiddlePrice() { return this.middlePrice; }

	public double getMaxPrice() { return this.maxPrice; }

	public LocalDate getStartDate() {  return this.startDate; }

	public LocalDate getEndDate() { return this.endDate; }

	public boolean getIsClean() { return this.isClean; }

	public boolean getIsSafe() { return this.isSafe; }

	/**
	 * Returns the price range as a simple string property to be 
	 * displayed in the TableView. 
	 * Format: Standard Price - Gold Price. Ex: $1,573 - $5,689
	 * @return The price range as a SimpleString property.
	 */
	public SimpleStringProperty getPriceRange() { 
		String range = "$" + String.format("%,d", (int) getMinPrice()) + " - $" + String.format("%,d", (int) getMaxPrice());
		return new SimpleStringProperty(range);
	}

	/**
	 * Calculates and returns the average tier price of an apartment.
	 * @return The average tier price of an apartment as a double
	 */
	public double getAvgPrice() { 
		return (getMinPrice() + getMiddlePrice() + getMaxPrice()) / 3;
	}

	/**
	 * Return the start data as a SimpleStringProperty to put it in the TableView 
	 * on the MainPage. Formatted as M/d/YYYY. Ex. May 3rd would be 5/3/2023.
	 * @return The start data as a SimpleStringProperty
	 */
	public SimpleStringProperty getStartDateString() {
		return new SimpleStringProperty(getStartDate().format(DateTimeFormatter.ofPattern("M/d/yyyy")));
	}
	
	/**
	 * Return the end data as a SimpleStringProperty to put it in the TableView 
	 * on the MainPage. Formatted as M/d/YYYY. Ex. August 6th would be 8/6/2023.
	 * @return The end data as a SimpleStringProperty
	 */
	public SimpleStringProperty getEndDateString() {
		return new SimpleStringProperty(getEndDate().format(DateTimeFormatter.ofPattern("M/d/yyyy")));
	}
	
	/**
	 * Return the the string "Yes" as a SimpleStringProperty if
	 * the apartment is available. Return no if it is not. Purposes
	 * of this method is to display availability in the TableView on the main
	 * page.
	 * @return Return the the string "Yes" as a SimpleStringProperty if the 
	 *         apartment is available. Else return no.
	 */
	public SimpleStringProperty getIsAvailableString() {
		return new SimpleStringProperty(getIsAvailable() ? "Yes" : "No");
	}
	
	/**
	 * Return the sqaure feet of the apartment as a SimpleStringProperty 
	 * to display in the TableView on the main page.
	 * @return The sqaure feet of the apartment as a SimpleStringProperty
	 */
	public SimpleStringProperty getSquareFeetString() {
		return new SimpleStringProperty(String.valueOf(getSquareFeet()));
	}
	
	/**
	 * Return the apartment name as a SimpleStringProperty to display in 
	 * the TableView on the main page.
	 * @return The apartment name as a SimpleStringProperty 
	 */
	public SimpleStringProperty getNameString() {
		return new SimpleStringProperty(getName());
	}
	

	//All mutator methods for an apartment object 
	public void setId(int id) { this.id = id; }

	public void setName(String name) { this.name = name; }

	public void setSquareFeet(int squareFeet) { this.squareFeet = squareFeet; }

	public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

	public void setLocation(String location) { this.location = location; }

	public void setDistanceFromCampus(double distanceFromCampus) { this.distanceFromCampus = distanceFromCampus; }

	public void setRoomAmenities(String roomAmenities) { this.roomAmenities = roomAmenities; }

	public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }

	public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public void setEmail(String email) { this.email = email; }

	public void setMinPrice(double minPrice) { this.minPrice = minPrice; }

	public void setMiddlePrice(double middlePrice) { this.middlePrice = middlePrice; }

	public void setMaxPrice(double maxPrice) { this.maxPrice = maxPrice; }

	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

	public void setIsClean(boolean isClean) { this.isClean = isClean; }

	public void setIsSafe(boolean isSafe) { this.isSafe = isSafe; }

	/**
	 * Return the string of a Apartment object. NOTE: getStartDateString.get() returns the properly formatted date string
	 *  type  int,String,int,  boolean,  String,  double     ,  String  ,  int     ,     int      , String,String, double ,   double   ,  double , LocalDate,LocalDate, boolean, boolean
	 *  Index  0 , 1 ,  2 ,    3      ,   4    ,   5         ,    6     ,    7     ,      8       ,   9  ,  10  ,   11    ,   12           13         14    ,   15   ,   16    , 17 
	 * Format id,name,sqFt,isAvailable,location,dstFromCampus, amenities,totalRooms,availableRooms,phone #,email, minPrice, middlePrice, maxPrice, startDate, endDate, isClean, isSafe
	 * @return the string of a Apartment object
	 */
	@Override
	public String toString() {
		return id + "," + name + "," + squareFeet + "," + isAvailable + "," + location + "," + distanceFromCampus + "," +
		       roomAmenities + "," + totalRooms + "," + availableRooms + "," + phoneNumber + "," +
			   email + "," + minPrice + "," + middlePrice + "," + maxPrice + "," + getStartDateString().get() + "," + 
			   getEndDateString().get() + "," + isClean + "," + isSafe;
	}

}