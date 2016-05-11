package jTunes;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import jTunes.gui.*;
//Tests
public class App extends JPanel {
    private static final String VersionID = "1.01";
    
    private List<String> choices;
    
    public App() {
        setPreferredSize(new Dimension(350, 600));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        try {
            add(new Header("Genre"));
        } catch (IOException e) {
            System.err.println("Failed to add Header.");
            e.printStackTrace();
        }
        add(new Body());
        add(new Footer("Hello"));
//        
//        setPreferredSize(new Dimension(350, 600));
//        GroupLayout layout = new GroupLayout(this);
//        layout.setAutoCreateGaps(false);
//        layout.setAutoCreateContainerGaps(false);
//        setLayout(layout);
//        
//        JPanel[] menu = new JPanel[] {
//            new JPanel(),
//            new JPanel(),
//            new JPanel(),
//            new JPanel()
//        };
//        String[] menuItems = {
//                "Blues",
//                "Jazz",
//                "Rock n Roll",
//                "Classical"
//        };
//        
//        // filling, sizing, and coloring
//        for (int i = 0; i < menu.length; ++i) {
//            menu[i].setLayout(new BorderLayout());
////            menu[i].add(new JButton(menuItems[i]), BorderLayout.CENTER);
//            
//            menu[i].setPreferredSize(new Dimension(10, 50));
//            
//            menu[i].setBackground(Color.decode("0x00ffab")); // 0x00FFAB is mint green
//        }
//        
//        // horizontal group
//        GroupLayout.ParallelGroup hGroup = 
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING);
//        for (int i = 0; i < menu.length; ++i)
//            hGroup.addComponent(menu[i]);
//        layout.setHorizontalGroup(hGroup);
//        
//        // vertical group
//        GroupLayout.SequentialGroup vGroup =
//                layout.createSequentialGroup();
//        for (int i = 0; i < menu.length; ++i)
//            vGroup.addComponent(menu[i]);
//        layout.setVerticalGroup(vGroup);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("jTunes " + VersionID);
                f.add(new App());
                f.setPreferredSize(new Dimension(350, 600));
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
