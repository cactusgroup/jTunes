package jTunes.database;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.application.Platform;

public class MenuTest {
    public static void main(String[] args) throws SQLException, URISyntaxException {
        final String underline = "=================================";
        final String select = "Please select a ";
        
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String savedInput = "";
        String savedGenre = "";
        
        Menu menu = new Menu();
        System.out.println();
        System.out.println(underline);
        
        boolean exitBool = false;
        
        System.out.println("Welcome to your playlist!");
        while(!exitBool){
        	// Genre Loop, will loop if input is incorrect. 
        	while(true){
        	        System.out.println(underline);
        	        menu.getValues(ValueType.genre);           // prints list of genres
        	        System.out.println();
        	        
        	        System.out.print(select + "genre (type its name) or type 'quit' to exit: ");
        	        input = scanner.nextLine();
        	        if(input.contentEquals("quit") || input.contentEquals("Quit")) {
        	        	exitBool = true;
        	        	break;
        	        }
        	        System.out.println(underline);
        	        if(menu.getArtistsInGenre(input).isEmpty()){
        	        	System.out.println("Invalid Choice.");
        	        	continue;
        	        }
        	        else break;
        		
        	}
        if (exitBool) break; // Quitting the application if exitBool is triggered.
        savedInput = input;
        savedGenre = savedInput;
        
        while(true){
        	 System.out.println();
             System.out.print(select + "artist (type its name): ");
             input = scanner.nextLine();
             System.out.println(underline);
             if(menu.getAlbumsByArtistInGenre(savedGenre, input).isEmpty()){
            		System.out.println("Invalid Choice.");
            		System.out.println(underline);
            		menu.getArtistsInGenre(savedInput);
    	        	continue;
    	        }
    	        else break;	
        	}
        savedInput = input;
        
        while(true){
        	System.out.println();
        	System.out.print(select + "album (type its name): ");
        	input = scanner.nextLine();
        	System.out.println(underline);
        	if(menu.getSongsInAlbumByArtistInGenre(input).isEmpty()){
        		System.out.println("Invalid Choice.");
        		System.out.println(underline);
        		menu.getAlbumsByArtistInGenre(savedInput, savedGenre);
	        	continue;
        	}
        	else break;
        }
        savedInput = input;
        
        while(true){
        	System.out.println();
        	System.out.print("Please select a song or type quit to exit: ");
        	input = scanner.nextLine();
        	if(input.contentEquals("quit") || input.contentEquals("Quit")) {
	        	exitBool = true;
	        	break;
	        }
        	System.out.println(underline);
        	/*
        	if (!menu.activateSong(input)){
        		System.out.println(underline);
        		menu.getSongsInAlbumByArtistInGenre(savedInput);
        		continue;
        	}
        	else break;
        	*/
        	menu.activateSong(input);
        	break;
        	}        	
        }
        System.out.println("Exiting, goodbye!");
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
                System.out.println("Exiting, goodbye!");
                scanner.close();
                menu.closeConnection();
                Platform.exit();
        	}
        });
    }
}
