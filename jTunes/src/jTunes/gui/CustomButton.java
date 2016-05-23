package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class is subclassed from JPanel with the intent
 * of creating a highly-flexible button via MouseEvents.
 * 
 * @author joshuachu
 */
@SuppressWarnings("serial")
public class CustomButton extends JPanel {
    private ImageIcon myIcon;
    private JLabel myLabel;
    
    // default border constructor
    public CustomButton(String iconPath, String substitute, int fontSize, Color bg) {
        this(iconPath, substitute, fontSize, bg, new Insets(10,10,10,10));
    }
    
    // border width can be specified
    public CustomButton(String iconPath, String substitute, int fontSize, Color bg, Insets insets) {
        setLayout(new BorderLayout());
        setBackground(bg);
        setBorder(new EmptyBorder(insets));
        
        // Attempts to set our JLabel's icon field with an image 
        // On failure, it will substitute a text version of the icon.
        try {
            myIcon = new ImageIcon(ImageIO.read(
                    getClass().getClassLoader().getResource(iconPath)));
            myLabel = new JLabel(myIcon);
        } catch (IOException | IllegalArgumentException e) {
            int idx = iconPath.lastIndexOf('/');
            System.err.println(iconPath.substring(idx) + " not found.");
            e.printStackTrace();
            
            myLabel = new JLabel(substitute);
            myLabel.setFont(new Font("Sans-Serif", Font.BOLD, fontSize));
        }
        add(myLabel, BorderLayout.CENTER); // add our JLabel
    }
    
    // this method allows our JLabel's icon field to be modified.
    public void setIcon(String iconPath, String substitute, int fontSize) {
        removeAll(); // get rid of all components from this panel
        
        // same idea as constructor
        try {
            myIcon = new ImageIcon(ImageIO.read(
                    getClass().getClassLoader().getResource(iconPath)));
            myLabel = new JLabel(myIcon);
        } catch (IOException | IllegalArgumentException e) {
            int idx = iconPath.lastIndexOf('/');
            System.err.println(iconPath.substring(idx) + " not found.");
            e.printStackTrace();
            
            myLabel = new JLabel(substitute);
            myLabel.setFont(new Font("Sans-Serif", Font.BOLD, fontSize));
        }
        add(myLabel, BorderLayout.CENTER);
        
        // repaint the button
        repaint();
        // let this panel's layout manager know there's layout work to do.
        revalidate();
    }
}
