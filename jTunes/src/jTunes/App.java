package jTunes;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class App extends JPanel {
    private static final String VersionID = "1.01";
    
    private List<String> choices;
    
    public App() {
        OurDb db = new OurDb();
        choices = db.getGenres();
        
        setPreferredSize(new Dimension(800, 600));
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        JPanel[] menu = new JPanel[] {
            new JPanel(),
            new JPanel(),
            new JPanel()
        };
        String[] menuItems = {
                "Blues",
                "Jazz",
                "Rock n Roll"
        };
        
        // filling, sizing, and coloring
        for (int i = 0; i < menu.length; ++i) {
            menu[i].setLayout(new BorderLayout());
            menu[i].add(new JLabel(menuItems[i], JLabel.CENTER), BorderLayout.CENTER);
            
            menu[i].setPreferredSize(new Dimension(300, 50));
            
            menu[i].setBackground(Color.CYAN);
        }
        
        // horizontal group
        GroupLayout.ParallelGroup hGroup = 
                layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        for (int i = 0; i < menu.length; ++i)
            hGroup.addComponent(menu[i]);
        layout.setHorizontalGroup(hGroup);
        
        // vertical group
        GroupLayout.SequentialGroup vGroup =
                layout.createSequentialGroup();
        for (int i = 0; i < menu.length; ++i)
            vGroup.addComponent(menu[i]);
        layout.setVerticalGroup(vGroup);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes" + VersionID);
                f.add(new App());
                f.setPreferredSize(new Dimension(300, 600));
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
