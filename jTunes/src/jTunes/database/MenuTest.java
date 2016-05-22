package jTunes.database;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuTest {
    public static void main(String[] args) throws SQLException {
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
        	        menu.getValues(Menu.ValueType.genre);           // prints list of genres
        	        System.out.println();
        	        
        	        System.out.print(select + "genre (type its name) or type 'quit' to exit: ");
        	        input = scanner.nextLine();
        	        System.out.println(input);
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
        if (exitBool) break; // Quiting the application if exitBool is triggered.
        savedInput = input;
        savedGenre = savedInput;
        
        while(true){
        	 System.out.println();
             String genre = savedInput;
             System.out.print(select + "artist (type its name): ");
             input = scanner.nextLine();
             System.out.println(underline);
             if(menu.getAlbumsByArtistInGenre(input, genre).isEmpty()){
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
        	if (!menu.activateSong(input)){
        		System.out.println(underline);
        		menu.getSongsInAlbumByArtistInGenre(savedInput);
        		continue;
        	}
        	else break;
        	}
        }
        
        System.out.println("Exiting, goodbye!");
        scanner.close();
    }
}
