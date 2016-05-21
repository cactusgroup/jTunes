package jTunes.database;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuTest {
    public static void main(String[] args) throws SQLException {
        String underline = "=================================";
        String select = "Please select a ";
        
        Scanner scanner = new Scanner(System.in);
        String input;
        
        Menu menu = new Menu();
        System.out.println();
        System.out.println(underline);
        
        System.out.println("Welcome to your playlist!");
        System.out.println(underline);
        menu.getValues(Menu.ValueType.genre);           // prints list of genres
        System.out.println();
        
        System.out.print(select + "genre (type its name): ");
        input = scanner.nextLine();
        System.out.println(underline);
        menu.getArtistsInGenre(input);
        System.out.println();
        
        String genre = input;
        System.out.print(select + "artist (type its name): ");
        input = scanner.nextLine();
        System.out.println(underline);
        menu.getAlbumsByArtistInGenre(input, genre);
        System.out.println();
        
        System.out.print(select + "album (type its name): ");
        input = scanner.nextLine();
        System.out.println(underline);
        menu.getSongsInAlbumByArtistInGenre(input);
        System.out.println();
        
        System.out.print("Please select a song: ");
        input = scanner.nextLine();
        System.out.println(underline);
        menu.activateSong(input);
        scanner.close();
    }
}
