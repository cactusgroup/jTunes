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
 *
 */
public class App {
    public  static final String SUGGESTIONS_PANEL = "suggest";
    public  static final String MENU_PANEL = "menu";
    private static final String VersionID = "1.01";     // Version #
    private static final Dimension APP_SIZE =
            new Dimension(350, 600);                    // App window dimensions
    
    private static HeaderPanel headerPanel;                  // top bar    
    private static BodyPanel bodyPanel;                      // middle are
    private static FooterPanel footerPanel;                  // bottom bar 
    
    private static boolean AppRun = false;
    private static SearchResultPanel resultsPanel;           // search results
    private static String songName = "";
    private static String AlbArt = "";
    private static Menu menu;
    private static volatile MP3Player_GUI player_GUI;
    
    public static JPanel generateUI() throws IOException {
        menu = new Menu();
        
        // initialize our main App panel
        JPanel appPanel = new JPanel();
        
        // set up layout, size, and background color
        appPanel.setLayout(new BoxLayout(appPanel, BoxLayout.PAGE_AXIS));
        appPanel.setPreferredSize(APP_SIZE);
        appPanel.setBackground(Color.LIGHT_GRAY);
        
        // initialize our structure panels
        headerPanel = new HeaderPanel("Genres");            
        bodyPanel = new BodyPanel();                
        footerPanel = new FooterPanel("Hello");
        // the FooterPanel gets a Player to itself
        footerPanel.setPlayer(player_GUI);
        
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes " + VersionID);
                try {
                    f.add(generateUI());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                f.pack();                               // fits f to its contents
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                // shutdown support for Cmd-W
                f.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent ke) {
                        int kCode = ke.getKeyCode() ; 
                        if (kCode == KeyEvent.VK_W && ke.isMetaDown())
                            System.exit(0);
//                        if (ke.getKeyCode() == KeyEvent.VK_F4 && ke.isAltDown())
//                            System.exit(0);
                    }
                });
                
                // shutdown support for close button
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                
                f.setVisible(true);
                
                query(ValueType.genre);
            }
        });
        
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    menu.closeConnection();
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
    
    public static void setTitle(String t) {
        headerPanel.setTitle(t);
    }
    
    public static void query(ValueType type, String... constraints) {
        resultsPanel.clearSearchResults();
        resultsPanel.setVisible(true);
        
        List<String> list = new ArrayList<>(15);
        
        switch(type) {
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
                resultsPanel.setVisible(false);
                
                // stop currently-playing / finished-playing song if needed
                //Application.launch(MP3Player_GUI.class, response);
                
                songName = response;
                
                if(AppRun) {
                    try {
                        if(MP3Player_GUI.isPlaying()){
                        MP3Player_GUI.loadNewMP3File(songName);
                        MP3Player_GUI.play();
                        }
                        else{
                            MP3Player_GUI.loadNewMP3File(songName);
                        }
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            else{
                    AppRun = true;
                    ThreadRunner runn = new ThreadRunner();
                    runn.RunMe(songName);               
            }
                /*if(player.isPlaying()) {
                    player.end();
                    player.load(response);
                    player.play();
                }
                else if(player.isCompleted()){
                    player.end();
                    player.load(response);
                }
                else
                    player.load(response);
                */
                System.out.println("----Song loaded----");
                footerPanel.setCurrentSong(response);
                
                try {
                    bodyPanel.SetAlbumArt(AlbArt);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
