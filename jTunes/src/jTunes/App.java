package jTunes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;              // window
import javax.swing.JPanel;              // interior panel
import javax.swing.SwingUtilities;      // 

import jTunes.gui.*;

public class App extends JPanel {
    private static final String VersionID = "1.01";     // Version #
    private static final Dimension APP_SIZE =
            new Dimension(350, 600);                    // App window dimensions
    
    private SearchResultPanel results;                  // search results 
//  private MenuPanel menu;                             // main menu
//  private SearchSuggestionPanel suggestions;          // search suggestions
    
    public App() {
        // set up layout, size, and background color
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(APP_SIZE);
        setBackground(Color.LIGHT_GRAY);
        
        // initialize our structure panels
//      HeaderPanel h = new HeaderPanel("Genre");       // top bar
        BodyPanel b = new BodyPanel();                  // middle area
        FooterPanel f = new FooterPanel("Hello");       // bottom bar
        
        // initialize our content panels
        results = new SearchResultPanel();
        // TODO: Implement these panels
//      menu = new MenuPanel();
//      suggestions = new SearchSuggestionPanel();
        
        b.setLayout(null);
        
        b.add(results);
//      b.add(menu);
//      b.add(suggestions);
        
        Insets insets = b.getInsets();
        Dimension size = results.getPreferredSize();
        results.setBounds(insets.left,
                          insets.top,
                          size.width,
                          size.height);
//      size = menu.getPreferredSize();
//      menu.setBounds(insets.left - size.width,
//                     insets.top,
//                     size.width,
//                     size.height);
//      size = suggestions.getPreferredSize();
//      suggestions.setBounds(insets.left - size.width,
//                            insets.top,
//                            size.width,
//                            size.height);
        
        
//      add(h);
        add(b);
        add(f);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes " + VersionID);
                f.add(new App());
                f.pack();                               // fits f to its contents
                f.setResizable(false);
                f.setVisible(true);
            }
        });
    }
}
