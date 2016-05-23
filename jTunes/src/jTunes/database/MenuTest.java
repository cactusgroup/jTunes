package jTunes.database;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.application.Platform;
/*
 * The MenuTest class tests and runs the Menu class application on the console
*/
public class MenuTest {
    public static void main(String[] args) throws SQLException, URISyntaxException {
        final String underline = "=================================";  // used for formatting
        final String select = "Please select a "; // will be used to prompt the user
        
        Scanner scanner = new Scanner(System.in);
        String input;  // stores the input from the user
        String savedInput;  // saves the last input received
        String savedGenre;	// saves the genre name in order to retrieve the albums
       
        boolean exitBool = false; // will signal when the program should end
        Menu menu = new Menu();  // Creating a menu object
        
        System.out.println();
        System.out.println(underline);
        System.out.println("Welcome to your playlist!");
      
        while(!exitBool) {
  
        	// Genre Loop, will loop if input is incorrect. 
        	while(true) {
        	        System.out.println(underline);
        	        menu.getValues(ValueType.genre);  // prints list of genres
        	        System.out.println();
        	        System.out.print(select + "genre (type its name) or type 'quit' to exit: ");
        	        input = scanner.nextLine();
        	        if(input.contentEquals("quit") || input.contentEquals("Quit")) {  // checks if user wants to quit
        	        	exitBool = true;
        	        	break; // breaks out of the genre loop
        	        }
        	        System.out.println(underline);
        	        if(menu.getArtistsInGenre(input).isEmpty()) {  // artists are printed if input is valid
        	        	System.out.println("Invalid Choice.");
        	        	continue; // will continue the loop until input is valid
        	        }
        	        else break; // will break out of loop if the artist list is not empty (i.e. valid input)
        	}
        	
        	if (exitBool) break; // Quitting the application if exitBool is triggered.
        	savedInput = input; // savedInput is now storing the genre name
        	savedGenre = savedInput;  // saves the genre
        
        	// Artist Loop, will loop if input is incorrect. 
        	while(true) {
        		System.out.println();
        		System.out.print(select + "artist (type its name) or type 'quit' to exit: ");
        		input = scanner.nextLine();
        		if(input.contentEquals("quit") || input.contentEquals("Quit")) {  
        			exitBool = true;
        			break;  // breaks out of artist loop if input is "quit"
        		}
        		System.out.println(underline);
        		if(menu.getAlbumsByArtistInGenre(savedGenre, input).isEmpty()) {  // prints albums and checks if input is valid
            		System.out.println("Invalid Choice.");
            		System.out.println(underline);
            		menu.getArtistsInGenre(savedInput); // prints the artists again if input is incorrect
    	        	continue;  // continues with the artist loop
    	        }
    	        else break;	// will break out of loop if the album list is not empty (i.e. valid input)
        	}
        
        	if(exitBool) break;  // quits the application if exitBool is triggered
        	savedInput = input;  // savedInput is now storing the artist name
        
        	// Album Loop, will loop if input is incorrect
        	while(true) {
        		System.out.println();
        		System.out.print(select + "album (type its name) or type 'quit' to exit: ");
        		input = scanner.nextLine();
        		if(input.contentEquals("quit") || input.contentEquals("Quit")) {
        			exitBool = true;
        			break;  // breaks out of album loop if input is "quit"
        		}
        		System.out.println(underline);
        		if(menu.getSongsInAlbumByArtistInGenre(input).isEmpty()) {  // prints songs and checks if input is valid
        			System.out.println("Invalid Choice.");
        			System.out.println(underline);
        			menu.getAlbumsByArtistInGenre(savedGenre, savedInput); // prints albums again if input is incorrect
        			continue;  // continues with the album loop
        		}
        		else break;  // will break out of loop if the song list is not empty (i.e. valid input)
        	}
        
        	if(exitBool) break;  // quits the application if exitBool is triggered
        	savedInput = input;  // savedInput is now storing the album name
        
        	// Song Loop, will loop if input is incorrect
        	while(true) {
        		System.out.println();
        		System.out.print("Please select a song or type quit to exit: ");
        		input = scanner.nextLine();
        		if(input.contentEquals("quit") || input.contentEquals("Quit")) {
        			exitBool = true;
        			break; // breaks out of the song loop if input is "quit"
        		}
        		if(MenuTest.class.getResource("/mp3tracks/" + input + ".mp3") != null) { // checks if an mp3 file of the song name exists
        			menu.activateSong(input);
        			break;  // breaks out of the song loop if the file exists
        		}
        		else {
        			System.out.println(underline);
        			System.out.println("Invalid Choice or file doesn't exist");
        			System.out.println(underline);
        			menu.getSongsInAlbumByArtistInGenre(savedInput);  // prints the songs list again if input is incorrect
        			continue;  // continues with the song loop
        		
        		}
        	}   // end of song loop   	
        }  // end of application
        
        System.exit(0);  // Terminates the program 
        Runtime.getRuntime().addShutdownHook(new Thread() {  // runs when the program is terminated
        	public void run() {
                System.out.println("Exiting, goodbye!");
                scanner.close();   // closing the scanner object
                menu.closeConnection();  // closes the database connection 
                Platform.exit();  // closes the platform of the application
        	}
        });
    }
}
