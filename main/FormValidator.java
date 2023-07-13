package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Michael Totaro
 * @date 4/16/2023
 * @section CSC 331 - 002
 * @purpose These methods validate information
 *          entered by user in the sign in page,
 *          create account page, and registration page.
 */
public class FormValidator {
	
	/** Number of letters currently iterated over in string */
    private int letterCount;
    
    /** Number of integers currently iterated over in string */
    private int numberCount;
    
    /** Instance of a letter state */
    private State letterState = new LetterState();
    
    /** Instance of a number state */
    private State numberState = new NumberState();
    
    /** Instance of a special state */
    private State specialCharState = new SpecialCharState();
    
    /** The current state */
    private State currentState;

    /** Message explaining what a valid string is to the user */
    private final static String PW_MESSAGE = "\nPW format: (4+ Letters)(3+ digits)(1 special char (!,%,$,*))";

    //Password must start with 4 or more letters, then 3 or more Numbers, then one special character
    
    /**
     * This is the "driver" method of the finite state machine. Iterates over the 
     * string and sets the state to an onLetter() state if the character is a letter.
     * Sets the state to onNumber if the character is a digit. Sets the state to specialCharState
     * if the character is any of the following characters '!', '%', '$', '*'. If not a letter, digit,
     * or valid special char the state is set to onOther. Initializes letterCount, numberCount, and the 
     * currentState.
     * @param password The password to be validated
     * @return true if the password was successfully iterated over.
     * @throws IllegalArgumentException is the length of the password is 0
     */
    public boolean isValidPasswordFSM(String password) {
    	
    	if (password.length() == 0) {
    		throw new IllegalArgumentException("Password field cannot be blank");
    	}
    	
        letterCount = 0;
        numberCount = 0;
        currentState = letterState; 

        for (int i = 0; i < password.length(); i++) {
            char chr = password.charAt(i);

            if (Character.isLetter(chr)) {
                currentState.onLetter();
            } else if (Character.isDigit(chr)) {
                currentState.onNumber();
            } else if (chr == '!' || chr == '%' || chr == '$' || chr == '*') {
                currentState.onSpecialChar();
            } else {
                currentState.onOther(chr);
            }
        }

        return true;
    }


   /**
    * @author FILL THIS IN
    * @purpose Create an abstract class for a character state.
    * 
    */
	public abstract class State {
		
		/** method called if character is a letter */
		public abstract void onLetter();
		
		/** method called if character is a number */
		public abstract void onNumber();
		
		/** method called if character is a special character */
        public abstract void onSpecialChar();

        /**
         * Throws an IllegalArgumentException if there is an invalid character in the String
         * @param badChar Invalid character in string
         * @throws IllegalArgumentException If this method is called at all
         */
        public void onOther(char badChar) {
            throw new IllegalArgumentException("\'" + badChar + "\' is not a character that's allowed in the password");
        }
    }


   /**
    * @author FILL THIS IN
    * @purpose Creates a letter state for the string/FSM
    */
    public class LetterState extends State {
    	
    	/**
    	 * If the current state is a letterstate and this 
    	 * method is called increment letter count
    	 */
        @Override
        public void onLetter() {
            letterCount++;
        }

    	/**
    	 * If the current state is a letterstate and this 
    	 * method is called increment numberCount and set the 
    	 * current state to numberstate. If the letterCount is less
    	 * than 4 throw an exception
    	 * @throws IllegalArgumentException If the letter count of the string is less than 4 and this method is called
    	 */
        @Override
        public void onNumber() {
            if (letterCount < 4) {
                throw new IllegalArgumentException("You need at least 4 letters before a number" + PW_MESSAGE);
            }
            
            numberCount++;
            currentState = numberState;
        }
        
        /**
         * Can't have a special char after a letter
         * @throws IllegalArgumentException If the current state is letter state and this method is called throw an exception.
         */
        @Override
        public void onSpecialChar() {
            throw new IllegalArgumentException("Can't have a special character directly after a letter" + PW_MESSAGE);
        }
    }
    
    /**
     * @author FILL THIS IN
     * @purpose Creates a number state for the string/FSM
     */
    public class NumberState extends State {
    	
    	/**
    	 * Letter can't come after number
    	 * @throws IllegalArgumentException If the current state is numberState and this method is called. 
    	 */
        @Override
        public void onLetter() {
            throw new IllegalArgumentException("Can't have letter after a number" + PW_MESSAGE);
        }
        
        /**
         * If this method is called when the current state is a number state increment numberCount.
         */
        @Override
        public void onNumber() {
            numberCount++;
        }
        
        /**
         * If this method is called when the currentState is a numberState set the currentState to 
         * specialChar state. If the numberCount is less than 3 throw an exception.
         * @throws IllegalArgumentException If the numberCount is less than 3 and this method is called.
         */
        @Override
        public void onSpecialChar() {
            if (numberCount < 3) {
                throw new IllegalArgumentException("Need at least 3 letters before having a special character" + PW_MESSAGE);
            }
            
            currentState = specialCharState;
        }
        
    }

    /**
     * @author FILL THIS IN
     * @purpose Creates a special char state for the string/FSM
     */
    public class SpecialCharState extends State {

    	/**
    	 * If any character comes after the 1 special char thrown exception
    	 * @throws IllegalArgumentException if a letter comes after special char
    	 */
        @Override
        public void onLetter() {
            throw new IllegalArgumentException("Can't have letter after special character" + PW_MESSAGE);
        }

    	/**
    	 * If any character comes after the 1 special char thrown exception
    	 * @throws IllegalArgumentException if a number comes after special char
    	 */
        @Override
        public void onNumber() {
            throw new IllegalArgumentException("Can't have number after special character" + PW_MESSAGE);
        }

    	/**
    	 * If any character comes after the 1 special char thrown exception
    	 * @throws IllegalArgumentException if a special char comes after special char
    	 */
        @Override
        public void onSpecialChar() {
            throw new IllegalArgumentException("Can't have more than one special character" + PW_MESSAGE);
        }
        
    }


    //THESE METHODS ARE NOT PART OF THE FINITE STATE MACHINE

    /**
     * Validates a phone number
     * @param phoneNumber PhoneNumber to validated in the create account page / register page.
     * @throws IllegalArgumentException If phone number is not formated xxx-xxx-xxxx. Where x is an integer,
     * 								    and there are 10 x's and 2 dashes.
     */
    public void checkPhoneNumber(String phoneNumber) {

        int numCount = 0;
        int dashCount = 0;

        if (phoneNumber.length() != 12) {
            throw new IllegalArgumentException("Phone number should be formatted: xxx-xxx-xxxx");
        }

        for (int i = 0; i < phoneNumber.length(); i++) {
            char currChar = phoneNumber.charAt(i);

            if (Character.isDigit(currChar)) {
                numCount++;
            } else if (currChar == '-') {
                dashCount++;
            } else {
                throw new IllegalArgumentException("Phone number should be formatted: xxx-xxx-xxxx");
            }

            if (numCount == 4 && dashCount != 1) {
                throw new IllegalArgumentException("Phone number should be formatted: xxx-xxx-xxxx");
            } else if (numCount == 7 && dashCount != 2) {
                throw new IllegalArgumentException("Phone number should be formatted: xxx-xxx-xxxx");
            } else if (numCount > 10 || dashCount > 2) {
                throw new IllegalArgumentException("Phone number should be formatted: xxx-xxx-xxxx");
            }

        }
    }

    /**
     * Validates an email address. Must contain the "@" symbol and ".com" to be considered valid
     * @param email Email to be validated in the create account page.
     * @throws IllegalArgumentException if email doesn't contain the @ symbol or the substring .com
     */
    public void checkEmail(String email) {
        if (!(email.contains("@") && email.contains(".com"))) {
            throw new IllegalArgumentException("Email should be formatted: name@website.com");
        }
    }

    /**
     * Validates a firstname/lastname/name field. Must be all letters and have a length
     * greater than 0 to be considered valid. 
     * @param name Name to be validated that was entered in the create account page.
     * @throws IllegalArgumentException If name contains characters that aren't letters 
     * 									or has length less than 0.
     */
    public void checkName(String name) {

        boolean isNotValid = false;

        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                isNotValid = true;
            }
        }
        
        isNotValid = name.length() < 1;
        
        if (isNotValid) {
            throw new IllegalArgumentException("Names should contain only letters");
        }
    }



    /**
     * If user enters a first name, last name, username, or password
     * that's already in the database an exception is thrown. Returns 
     * number of users in database to create a new Id.
     * @param fName Firstname user entered
     * @param lName Lastname user entered
     * @param username Username uset entered 
     * @param pw Password user entered in the create account page.
     * @return Number of users in the database. 
     * @throws IllegalArgumentException If user enters a first name, last name, username, or password
     * 									that's already in the database 
     */
    public int checkForCredentialDuplicates(String fName, String lName, String username, String pw) {

        File file;
        Scanner input = null;
        
        int numOfUsers = 0;
        
        try {
            file = new File("src/main/UserDB.txt");
            input = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.out.println("This should never be throw. File missing");
        }

        while (input.hasNextLine()) {
            String currLine = input.nextLine();
            String[] s = currLine.split(",");
            
            //s[1] is first name, s[2] is last name, s[4] is username, s[5] is password
            if (fName.equals(s[1]) || lName.equals(s[2]) || username.equals(s[4]) || pw.equals(s[5])) {
                throw new IllegalArgumentException("Credentials are already in our database");
            }
            
            numOfUsers++;
            
        }
        
        input.close();
        
        return numOfUsers;
    }

    /**
     * Checks if password and re-entered password are the same.
     * @param pw1 Password user entered in the create account page.
     * @param pw2 Reentered password in the create account page.
     */
    public void isEqual(String pw1, String pw2) {
        if (!pw1.equals(pw2)) {
            throw new IllegalArgumentException("Your passwords do not match");
        }
    }

    /**
     * Checks that the user entered a username or email and password that exist in our 
     * database. If the username field is null then the email is validated,
     * if the email is null, then the username is validated. Returns User with matching credentials.
     * @param username Username entered on sign in page.
     * @param email Email entered on sign in page.
     * @param pw Password entered on sign in page.
     * @return User with matching credentials 
     * @throws IllegalArgumentException If both the username and email are null or the pw is null.
     * @throws IllegalArgumentException If credentials weren't found in database.
     */
    public User checkForValidCredentials(String username, String email, String pw) {

        File file;
        Scanner input = null;

        try {
        	file = new File("src/main/UserDB.txt");
            input = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.out.println("This should never be throw. File missing");
        }


        if ((username == null && email == null) || pw == null) {
        	throw new IllegalArgumentException("Please enter your credentials");
        }
        
        boolean wasFound = false;
        boolean searchingForEmail = (username == null) ? true : false;

        User signedInUser = null;
        
        while (input.hasNextLine()) {
            String currLine = input.nextLine();

            String[] splitStr = currLine.split(",");

            //splitStr[4] is username, splitStr[3] is email, splitStr[5] is password
            if (searchingForEmail) {
                if (email.equals(splitStr[3]) && pw.equals(splitStr[5])) {
                    wasFound = true;
                    signedInUser = stringToUser(currLine);
                    break;
                }
            } 
            else {
                if (username.equals(splitStr[4]) && pw.equals(splitStr[5])) {
                    wasFound = true;
                    signedInUser = stringToUser(currLine);
                    break;
                }
            }  
            
        }

        if (!wasFound) {
            String credential = (username == null) ? "email" : "username";
            throw new IllegalArgumentException("Invalid password or " + credential);
        } 
        
        input.close();
        
        return signedInUser;
    }
    
    /**
     * Validates dates on register page 
     * @param moveInDate Move in date entered on register page 
     * @param moveOutDate Move out date entered on register page 
     * @param apartment Apartment selected on main page 
     * @throws IllegalArgumentException If moveInDate or moveOutDate are null
     * @throws IllegalArgumentException If move in date is before apartment start date
     * @throws IllegalArgumentException If move in date is after apartment end date 
     * @throws IllegalArgumentException If move in date is before apartment start date 
     * @throws IllegalArgumentException If move out date is before apartment start date
     * @throws IllegalArgumentException If move out date is after apartment end date 
     * @throws IllegalArgumentException If move out date is before move in date 
     * @throws IllegalArgumentException If move out date and move in date are the same day
     */
    public void checkRegistrationFormDates(LocalDate moveInDate, LocalDate moveOutDate, Apartment apartment) {
    	
    	if (moveInDate == null) {
    		throw new IllegalArgumentException("Please select a Move-in date");
    	}
    	
    	
    	if (moveOutDate == null) {
    		throw new IllegalArgumentException("Please select a Move-out date");
    	}
    			
    	if (moveInDate.isBefore(apartment.getStartDate())) {
    		throw new IllegalArgumentException("Move-in date cannot be before apartment's first available start date (" + apartment.getStartDateString().get()  + ")");
    	}
    	
    	if (moveInDate.isAfter(apartment.getEndDate())) {
    		throw new IllegalArgumentException("Move-in date cannot be after apartment's end date (" + apartment.getEndDateString().get() + ")");
    	}
    	
    	if (moveOutDate.isBefore(apartment.getStartDate())) {
    		throw new IllegalArgumentException("Move-out date cannot be before apartment's start date (" + apartment.getStartDateString().get() + ")");
    	}
    	
    	if (moveOutDate.isAfter(apartment.getEndDate())) {
    		throw new IllegalArgumentException("Move-out date cannot be after apartment's end date (" + apartment.getEndDateString().get() + ")");
    	}
    	
    	if (moveOutDate.isBefore(moveInDate)) {
    		throw new IllegalArgumentException("Move-out date cannot be before move-in date.");
    	}
    	
    	if (moveOutDate.isEqual(moveInDate)) {
    		throw new IllegalArgumentException("Move-out date cannot be the same day as move-in date.");
    	}
    	
    }
    
    /**
     * Takes a line which should be values separated by commas.
     * @param line Line to converted to User object
     * @return User created from string
     * @throws IllegalArgumentException If string not formatted correctly.
     */
    public User stringToUser(String line) {
    	
    	User user = null;
    	
    	try {
        	String[] s = line.split(",");
        	int userId = Integer.parseInt(s[0]);
        	
        	String currApt = (s[7].contains("null")) ? null : s[7];
            String currRoom = (s[8].contains("null")) ? null : s[8];
            String ordHst = (s[9].contains("null")) ? null : s[9].stripTrailing();
        	
        	user = new User(userId, s[1], s[2], s[3], s[4], s[5], s[6], currApt, currRoom, ordHst);
        	
    	} catch (InputMismatchException ex) {
    		throw new IllegalArgumentException("User string no formatted correctly");
    	}
    	
    	return user;
    	
    }
    
}
