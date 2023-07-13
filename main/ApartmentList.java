package main;

import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/**
* @author Michael Totaro
* @date 4/16/2023
* @section CSC 331 - 002
* @purpose Creates an ApartmentList object. Which 
*          Is used in the main page to filter data, 
*          get apartments by their name and index,
*          and to convert a string to apartment object.
*/
public class ApartmentList {
	
	/** Array of apartment objects */
	private Apartment[] apartmentList;
	
	/**
	 * Reads in apartment objects from the ApartmentData.txt
	 * file, then constructs an apartmentList object by setting the
	 * apartmentList array equal to the array of objects created 
	 * from the file. 
	 */
	public ApartmentList() {
		this.apartmentList = getApartmentsFromFile();
	}
	
	/**
	 * Return the full array of all 8 apartment objects 
	 * @return The full array of apartment objects 
	 */
	public Apartment[] getApartmentList() {
		return this.apartmentList;
	}
	
	/**
	 * Return a string that contains all the apartment objects that
	 * have a distanceFromCampus value which is less than or equal to 
	 * the parameter. If no objects are found which match the criteria
	 * return null.
	 * @param distanceInMiles The maximum distance the user wants an apartment 
	 * 						  to be from campus
	 * @return A string of apartment objects seperated by the string "%%%",
	 *         which will be split into an Apartment array in the MainPageHelper
	 *         class. Return null if no objects match the criteria.
	 */
	public String filterByDistance(double distanceInMiles) {
		String s1 = "";
		
		for (Apartment apartment : apartmentList) {
			
			if (apartment.getDistanceFromCampus() <= distanceInMiles) {
				s1 += apartment.toString() + "%%%"; 
			}
			
		}
		
		return s1.equals("") ? null : s1.substring(0, s1.length() - 3);
	}
	
	/**
	 * Return a string that contains all the apartment objects that
	 * have an average price which is less than or equal to 
	 * the parameter. If no objects are found which match the criteria
	 * return null.
	 * @param price The maximum average price the user wants an apartment to have
	 * @return A string of apartment objects seperated by the string "%%%",
	 *         which will be split into an Apartment array in the MainPageHelper
	 *         class. Return null if no objects match the criteria.
	 */
	public String filterByPrice(double price) {
		String s2 = "";
		
		for (Apartment apartment : apartmentList) {
			
			if (apartment.getAvgPrice() <= price) {
				s2 += apartment.toString() + "%%%"; 
			}
			
		}
		
		return s2.equals("") ? null : s2.substring(0, s2.length() - 3);
	}
	
	/**
	 * Return a string that contains all the apartment objects that
	 * have a start date which is earlier or the same day as the 
	 * date passed as the parameter. If no objects are found which 
	 * match the criteria return null.
	 * @param startDate The latest start date the user wants an 
	 * 						  apartment to have
	 * @return A string of apartment objects seperated by the string "%%%",
	 *         which will be split into an Apartment array in the MainPageHelper
	 *         class. Return null if no objects match the criteria.
	 * @throws IllegalArgumentException If the start date is null.
	 */
	public String filterByDate(LocalDate startDate) {
		
		if (startDate == null) {
			throw new IllegalArgumentException("Please choose a valid start date");
		}
		String s3 = "";
		
		for (Apartment apartment : apartmentList) {
			
			LocalDate aptStartDate = apartment.getStartDate();

			if (aptStartDate.isBefore(startDate) || aptStartDate.isEqual(startDate)) {
				s3 += apartment.toString() + "%%%"; 
			}
			
		}
		
		return s3.equals("") ? null : s3.substring(0, s3.length() - 3);
	
	}
	
	/**
	 * Return a string that contains all the apartment objects that
	 * clean. If no objects are found which match the criteria return null.
	 * @return A string of apartment objects seperated by the string "%%%",
	 *         which will be split into an Apartment array in the MainPageHelper
	 *         class. Return null if no objects match the criteria.
	 */
	public String filterByCleanlines() {
		String s4 = "";
		
		for (Apartment apartment : apartmentList) {
			if (apartment.getIsClean()) {
				s4 += apartment.toString() + "%%%"; 
			}
		}
		
		return (s4.equals("")) ? null : s4.substring(0, s4.length() - 3);
		
	}
	
	/**
	 * Return a string that contains all the apartment objects that
	 * have a square feet value which is greater than or equal to the 
	 * parameter. If no objects are found which match the criteria return null.
	 * @param squareFeet The minimum number of square feet the user 
	 * 					 wants a room to be.
	 * @return A string of apartment objects seperated by the string "%%%",
	 *         which will be split into an Apartment array in the MainPageHelper
	 *         class. Return null if no objects match the criteria.
	 * @throws IllegalArgumentException If the parameter is negative.
	 */
	public String filterBySquareFt(int squareFeet) {
		
		if (squareFeet < 0) {
			throw new IllegalArgumentException("Square feet can't be negative");
		}
		
		String s5 = "";
		
		for (Apartment apartment : apartmentList) {
			if (apartment.getSquareFeet() >= squareFeet) {
				s5 += apartment.toString() + "%%%"; 
			}
		}
		
		return (s5.equals("")) ? null : s5.substring(0, s5.length() - 3);
		
	}
	
	/**
	 * Get all apartment objects from the file as string 
	 * seperated by the string "%%%".
	 * @return All apartment objects from the file 
	 * 		   as string seperated by the string "%%%".
	 */
	public String getEntireApartmentListString() {
		String s6 = "";
		
		for (Apartment apartment : apartmentList) { 
			s6 += apartment.toString() + "%%%"; 
		}
		
		return s6.substring(0, s6.length() - 3);
	}
	
	/**
	 * Get an apartment from the array by idx
	 * @param i The index of the apartment to be returned
	 * @return The apartment at the given index
	 */
	public Apartment getByIdx(int i) {
		return apartmentList[i];
	}
	
	/**
	 * Get an apartment from the array by its name
	 * @param name The name of the apartment to be returned
	 * @return The apartment with the given name. If 
	 *         an apartment with the given name is not found 
	 *         return null;
	 */
	public Apartment getByName(String name) {
		
		for (Apartment apartment : apartmentList) { 
			if (apartment.getName().equals(name)) {
				return apartment;
			}
		}
		
		return null;
	}
	
	/**
	 * Create and return an array of apartment objects
	 * from the ApartmentData.txt file.
	 * @return An array of apartment objects
	 * 		   from the ApartmentData.txt file.
	 */
    public static Apartment[] getApartmentsFromFile() {
        
        Apartment[] apartmentList = new Apartment[8];
        int idx = 0;

        try (Scanner input = new Scanner(new File("src/main/ApartmentData.txt"))) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

            while (input.hasNextLine()) {
                String[] line = input.nextLine().split(",");
                int p0 = Integer.parseInt(line[0]); //int id
                String p1 = line[1]; //String name 
                int p2 = Integer.parseInt(line[2]); //int squareFeet
                boolean p3 = Boolean.parseBoolean(line[3]); //boolean isAvailable
                String p4 = line[4]; //String location 
                double p5 = Double.parseDouble(line[5]); //double distanceFromCampus 
                String p6 = line[6]; //String roomAmenities
                int p7 = Integer.parseInt(line[7]); //int totalRooms
                int p8 = Integer.parseInt(line[8]); //int availableRooms
                String p9 = line[9]; // String phoneNumber 
                String p10 = line[10]; //String email
                double p11 = Double.parseDouble(line[11]); //double minPrice
                double p12 = Double.parseDouble(line[12]); //double middlePrice
                double p13 = Double.parseDouble(line[13]); //double maxPrice
                LocalDate p14 = LocalDate.parse(line[14], format); //LocalDate startDate
                LocalDate p15 = LocalDate.parse(line[15], format); //LocalDate endDate
                boolean p16 = Boolean.parseBoolean(line[16]); //boolean isClean
                boolean p17 = Boolean.parseBoolean(line[17].stripTrailing()); //boolean isSafe

                apartmentList[idx] = new Apartment(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
                idx++;
            }
        } catch (IOException e) {
            System.out.println("THIS SHOULD NEVER BE DISPLAYED 1");
        }

        return apartmentList;
    }
    
    /**
	 * Return an apartment object from a comma seperated string.
	 * @param apartmentString
	 * @return The apartment created from the string. If invalid 
	 *         data was passed print and error and return null.
	 */
    public Apartment stringToApartment(String apartmentString) {
    	try {
    		DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");
    		 
            String[] line = apartmentString.split(",");

            int p0 = Integer.parseInt(line[0]); //int id
            String p1 = line[1]; //String name 
            int p2 = Integer.parseInt(line[2]); //int squareFeet
            boolean p3 = Boolean.parseBoolean(line[3]); //boolean isAvailable
            String p4 = line[4]; //String location 
            double p5 = Double.parseDouble(line[5]); //double distanceFromCampus 
            String p6 = line[6]; //String roomAmenities
            int p7 = Integer.parseInt(line[7]); //int totalRooms
            int p8 = Integer.parseInt(line[8]); //int availableRooms
            String p9 = line[9]; // String phoneNumber 
            String p10 = line[10]; //String email
            double p11 = Double.parseDouble(line[11]); //double minPrice
            double p12 = Double.parseDouble(line[12]); //double middlePrice
            double p13 = Double.parseDouble(line[13]); //double maxPrice
            LocalDate p14 = LocalDate.parse(line[14], format); //LocalDate startDate
            LocalDate p15 = LocalDate.parse(line[15], format); //LocalDate endDate
            boolean p16 = Boolean.parseBoolean(line[16]); //boolean isClean
            boolean p17 = Boolean.parseBoolean(line[17]); //boolean isSafe
            
            return new Apartment(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
            
    	} catch (InputMismatchException e) {
    		System.out.println("THIS SHOULD NEVER BE DISPLAYED APARTMENTLIST");
    	}

    	return null;
    	
    }
    
}