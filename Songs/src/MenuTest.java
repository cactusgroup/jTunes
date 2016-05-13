import java.sql.SQLException;
import java.util.Scanner;

public class MenuTest {
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);
		String input;
		
		System.out.println("Welcome to your playlist!");
		System.out.println("=================================");
		
		Menu menu = new Menu();
		System.out.println();
		System.out.print("Please type the genre name: ");
		
		input = scanner.nextLine();
		System.out.println("=================================");
		menu.getArtists(input);
		System.out.println();
		System.out.print("Please type the artist name: ");
		
		input = scanner.nextLine();
		System.out.println("=================================");
		menu.getAlbums(input);
		System.out.println();
		System.out.print("Please type the album name: ");
		
		input = scanner.nextLine();
		System.out.println("=================================");
		menu.getSongs(input);
		System.out.println();
		System.out.print("Please type the song name: ");
		
		input = scanner.nextLine();
		// Invoke methods that will play the song
		scanner.close();
	}	
}
