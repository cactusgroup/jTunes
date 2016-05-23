package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import jTunes.App;
import jTunes.Resources;
import jTunes.database.ValueType;

/* 50 px high
 * +------------------------------+
 * | = [Albums]                 Q |
 * +------------------------------+
 * 
 */
@SuppressWarnings("serial")
//This class defines the upper part of our GUI. 
public class HeaderPanel extends JPanel {
    private JLabel title;
    
    public HeaderPanel(String title) {
        this(title, 350);
    }
    public HeaderPanel(String t, int width) {
        // set up title text
        title = new JLabel(t);
        
        // set up layout, size, and background color
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, 50));
        setBackground(ColorConstants.MINT);
        
        // Menu button initialization
        // With attached MouseListener
        // LEFT (LINE_START)
        CustomButton menuButton = new CustomButton(
                Resources.menuIcon,
                "\u2261",
                60,
                ColorConstants.MINT);
            menuButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    App.setTitle("Genres");
                    App.query(ValueType.genre);
                }
            });
        add(menuButton, BorderLayout.LINE_START);
        
        // Title bar initialization
        // CENTER
        JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(ColorConstants.MINT);
            titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.CENTER);
    }
    
    public void setTitle(String t) {
        title.setText(t); // Sets the title for the GUI.
    }
}
