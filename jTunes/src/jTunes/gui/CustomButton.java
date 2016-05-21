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

public class CustomButton extends JPanel {
    private ImageIcon myIcon;
    private JLabel myLabel;
    public CustomButton(String iconPath, String substitute, int fontSize, Color bg) {
        this(iconPath, substitute, fontSize, bg, new Insets(10,10,10,10));
    }
    public CustomButton(String iconPath, String substitute, int fontSize, Color bg, Insets insets) {
        setLayout(new BorderLayout());
        setBackground(bg);
        setBorder(new EmptyBorder(insets));
        
        try {
            myIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));
            myLabel = new JLabel(myIcon);
        } catch (IOException | IllegalArgumentException e) {
            int idx = iconPath.lastIndexOf('/');
            System.err.println(iconPath.substring(idx) + " not found.");
            e.printStackTrace();
            
            myLabel = new JLabel(substitute);
            myLabel.setFont(new Font("Sans-Serif", Font.BOLD, fontSize));
        }
        add(myLabel, BorderLayout.CENTER);
    }
    
    public void setIcon(String iconPath, String substitute, int fontSize) {
        removeAll();
        
        try {
            myIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));
            myLabel = new JLabel(myIcon);
        } catch (IOException | IllegalArgumentException e) {
            int idx = iconPath.lastIndexOf('/');
            System.err.println(iconPath.substring(idx) + " not found.");
            e.printStackTrace();
            
            myLabel = new JLabel(substitute);
            myLabel.setFont(new Font("Sans-Serif", Font.BOLD, fontSize));
        }
        add(myLabel, BorderLayout.CENTER);
        revalidate();
    }
}
