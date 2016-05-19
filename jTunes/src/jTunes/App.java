package jTunes;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import jTunes.gui.*;

public class App extends JPanel {
    private static final String VersionID = "1.01";
    
    public App() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(350, 600));
        setBackground(Color.LIGHT_GRAY);
        
        Header h = new Header("Genre");
        
        add(h);
        add(new MenuPanel());
        add(new Footer("Hello"));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes " + VersionID);
                f.add(new App());
                f.setPreferredSize(new Dimension(350, 600));
                f.pack();
                f.setResizable(false);
                f.setVisible(true);
            }
        });
    }
}
