package main;

/**
 * @author Glenn Johnson
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose Creates a User object.
 */
public class User {

	/** User ID which is an integer to identify them */
	private int userId;
	/** Users  */
	private String firstName;
	/** Users last name */
	private String lastName;
	/** Users email */
	private String email;
	/** Users username */
	private String username;
	/** Users password */
	private String password;
	/** Users phone number */
	private String phoneNumber;
	/** Name of apartment user is currently living in */
	private String currentApartment;
	/** Room user is currently renting. Example "Room 12" */
	private String currentRoom;
	/** List of users order history. Orders seperated by %%. Items seperated by % */
	private String orderHistory;

	/**
	 * Constructs a User object
	 */
	public User(int userId, String firstName, String lastName, String email, String username, String password, String phoneNumber, String currentApartment, String currentRoom, String orderHistory) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.currentApartment = currentApartment;
		this.currentRoom = currentRoom;
		this.orderHistory = orderHistory;
	}

	// Getter methods for a User object.
	public int getUserId() { return this.userId; }

	public String getFirstName() { return this.firstName; }

	public String getLastName() { return this.lastName; }
	
	public String getFullName() { return this.firstName + " " + this.lastName; }

	public String getEmail() { return this.email; }

	public String getUsername() { return this.username; }

	public String getPassword() { return this.password; }

	public String getPhoneNumber() { return this.phoneNumber; }

	public String getCurrentApartment() { return this.currentApartment; }

	public String getCurrentRoom() { return this.currentRoom; }

	public String getOrderHistory() { return this.orderHistory; }
	
	//Mutator methods for a user object
	public void setUserId(int userId) { this.userId = userId; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public void setEmail(String email) { this.email = email; }

	public void setUsername(String username) { this.username = username; }

	public void setPassword(String password) { this.password = password; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public void setCurrentApartment(String currentApartment) { this.currentApartment = currentApartment; }

	public void setCurrentRoom(String currentRoom) { this.currentRoom = currentRoom; }

	public void setOrderHistory(String orderHistory) { this.orderHistory = orderHistory; }
	
	/**
	 * Returns a string representation of a User object
	 * Format: id,firstName,LastName,Email,Username,Password,PhoneNumber,CurrentApartment,CurrentRoom,OrderHistory
	 * @return A string representation of a User
	 */
	@Override
	public String toString() {
		return userId + "," + firstName + "," + lastName + "," + email + "," +
		       username + "," + password + "," + phoneNumber + "," + currentApartment + "," +
			   currentRoom + "," + orderHistory;
	}
	

}