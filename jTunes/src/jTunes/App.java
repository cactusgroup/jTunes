package jTunes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;              // window
import javax.swing.JPanel;              // interior panel
import javax.swing.SwingUtilities;      // 

import jTunes.gui.*;
import javafx.application.Application;
import javafx.application.Platform;
import jTunes.database.*;

/**
 * This class creates our graphical interface to jTunes.
 * 
 * It was designed with a purely procedural architecture to support its role of
 * mediator between our content view panels and the music player in the footer
 * of the application.
 * 
 * @author joshuachu
 * @modded carlcolena
 */
public class App {
    public  static final String SUGGESTIONS_PANEL = "suggest";
    public  static final String MENU_PANEL = "menu";
    private static final String VersionID = "2.0";     // Version #
    private static final Dimension APP_SIZE =
            new Dimension(350, 600);                    // App window dimensions
    
    private static HeaderPanel headerPanel;                  // top bar    
    private static BodyPanel bodyPanel;                      // middle are
    private static FooterPanel footerPanel;                  // bottom bar 
    
    private static boolean AppRun = false;
    private static SearchResultPanel resultsPanel;           // search results
    private static String songName = "";        // This is for sending song name to MP3Player
    private static String AlbArt = "";          // This is for sending albumname to find albumart.
    private static Menu menu;                   // Menu object, for MySQL database connection and song searching
   
    // This function sets up our UI and returns a JPanel with all elements initialized.
    public static JPanel generateUI() throws IOException {
        menu = new Menu();
        
        // initialize our main App panel
        JPanel appPanel = new JPanel();
        
        // set up layout, size, and background color
        appPanel.setLayout(new BoxLayout(appPanel, BoxLayout.PAGE_AXIS));
        appPanel.setPreferredSize(APP_SIZE);
        appPanel.setBackground(Color.LIGHT_GRAY);
        
        // initialize our structure panels
        headerPanel = new HeaderPanel("Genres");  // Initializing header panel with "Genres"          
        bodyPanel = new BodyPanel();                
        footerPanel = new FooterPanel("");  // This initalizes FooterPanel text to be empty. Playing:
        // the FooterPanel gets a Player to itself
        
        // initialize our content panels
        resultsPanel = new SearchResultPanel();
        
        // add content panels to the "body" structure panel
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(resultsPanel);
        
        // add our structure panels to the main App panel 
        appPanel.add(headerPanel);
        appPanel.add(bodyPanel);
        appPanel.add(footerPanel);
        
        return appPanel;
    }
    
    // This is our main function that will run everything.
    public static void main(String[] args) {
        // Invoking Swing for GUI 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes " + VersionID); // Making a new JFrame with label 'jTunes + Version ID'
                try {
                    f.add(generateUI());                // Calling generateUI to generate our User Interface
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                f.pack();                               // fits f to its contents
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                // shutdown hooks and listeners: support for Cmd-W
                f.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent ke) {
                        int kCode = ke.getKeyCode() ; 
                        if (kCode == KeyEvent.VK_W && ke.isMetaDown())
                            System.exit(0);
                    }
                });
                
                // shutdown support for close button
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                
                f.setVisible(true); // Make our elements visible.
                
                query(ValueType.genre);  // Set up the query for Genre.
            }
        });
       
        
        // Shutdown hook to make sure all elements and connections are properly closed before shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            final Thread mainThread = Thread.currentThread();  // Saving main thread for shutdown
            public void run() {
                try {
                    menu.closeConnection();
                    MP3Player_GUI.close();
                    System.out.println("Resources closed.");
                    mainThread.join();
                    System.out.println("Shutting down.");
                } catch (InterruptedException e) {
                    System.out.println("We couldn't join our threads.");
                    System.out.println("We were interrupted.");
                    e.printStackTrace();
                }
            }
        });
    }
    // Function that sets the title to string t. 
    public static void setTitle(String t) {
        headerPanel.setTitle(t);
    }
    // Function for querys. This will be the GUI version of song searching.
    public static void query(ValueType type, String... constraints) {
        resultsPanel.clearSearchResults(); // Clearing the list of search results
        resultsPanel.setVisible(true);  // Setting this to be visible (over the albumart)
        
        List<String> list = new ArrayList<>(15);
        
        switch(type) { // This switch is used to select which menu function we take our values from and store them into a list.
        case genre:
            System.out.println("----Query Genre----");
            list = menu.getValues(type);
            break;
        case artist:
            System.out.println("----Query Artist----");
            list = menu.getArtistsInGenre(constraints[0]);
            break;
        case album:
            System.out.println("----Query Albums----");
            list = menu.getAlbumsByArtistInGenre(constraints[0], constraints[1]);
            break;
        case song:
            System.out.println("----Query Songs----");
            list = menu.getSongsInAlbumByArtistInGenre(constraints[0]);
            break;
        }
        // Adding the list of entries from menu into searchResults.
        List<SearchResult> searchResults = new ArrayList<>(15);
        for (String s : list) {
            searchResults.add(new SearchResult(type, s));
        }
        
        // add the search results we've gotten
        // with callback at the ready
        resultsPanel.addSearchResults(searchResults, new QueryResponse() {
            public void respond(String response) {
                ValueType prev = type;
                String[] prevConstraints =
                        Arrays.copyOf(constraints, constraints.length);
                
                // handles the stepping from genre -> artist -> album
                // (this is the recursive case)
                if (prev.next() != prev) {
                    switch (prev.ordinal()) {
                    case 0:
                        // will respond with genre choice
                        System.out.println("----Genre selected----");
                        query(prev.next(), response);
                        headerPanel.setTitle("Artists");
                        break;
                    case 1:
                        // will respond with artist choice (genre choice saved)
                        System.out.println("----Artist selected----");
                        query(prev.next(), prevConstraints[0], response);
                        headerPanel.setTitle("Albums");
                        break;
                    case 2:
                        // will respond with album choice
                        System.out.println("----Album selected----");
                        query(prev.next(), response);
                        AlbArt = response;
                        headerPanel.setTitle("Songs");
                        break;
                    }
                    
                    return;
                }
                
                // will respond with song choice
                // (this is the base case)
                System.out.println("----Song selected----");
                resultsPanel.setVisible(false); // Make the queries invisible so we can see album art.
                
                songName = response;  //Setting the response into songName for usage in MP3Player_GUI
                if(AppRun) { // If the JavaFX Application is running, do NOT launch again. instead load files directly into it.
                    try {
                        if(MP3Player_GUI.isPlaying()){ // If a song is playing while a new song was selected,
                        MP3Player_GUI.loadNewMP3File(songName); // load the new song and
                        MP3Player_GUI.play();  // have it immediately play. 
                        }
                        else{ // Otherwise
                            MP3Player_GUI.loadNewMP3File(songName); // Just load the new song. 
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            else{ // If the JavaFX application was never started, we launch it with our song choice.
                    AppRun = true; // Set AppRun to true so subsequent calls to this function will NOT come here again.
                    ThreadRunner runn = new ThreadRunner(); // Enable a new thread runner.
                    runn.RunMe(songName);               // Run the new thread for JavaFX. 
            }

                
                System.out.println("----Song loaded----");
                footerPanel.setCurrentSong(response); // Setting song name on footer so we can see what song we are playing.
                
                // This triggers the album art loader.
                try {
                    bodyPanel.SetAlbumArt(AlbArt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
