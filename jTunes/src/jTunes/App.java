package jTunes;

import java.util.*;
import javax.swing.*;

public class App extends JPanel {
    private static final String VersionID = "1.01";
    
    private List<String> choices;
    
    public App() {
        choices = OurDb.getGenres();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes" + VersionID);
                f.add(new App());
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
