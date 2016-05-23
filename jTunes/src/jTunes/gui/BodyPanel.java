package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel is effectively our album art panel
 * @author Carl Colena
 */
@SuppressWarnings("serial")
public class BodyPanel extends JPanel {
    private JLabel picLabel;            // pic label for album art container in JLabel.
    private boolean imgSet = false;     // Boolean for label instantiation and setting.
    public BodyPanel() {
        setPreferredSize(new Dimension(350, 500));
    }
    
    /**
     * Sets album art to display on this panel
     * @param albumart
     * @throws IOException
     */
    public void SetAlbumArt(String albumart) throws IOException{
        BufferedImage myPicture; // BufferedImage object to hold our picture
        
        
        try{ // Lets try to load from the relative path in our Eclipse project
            myPicture = ImageIO.read(new File("src/resources/imgs/"+albumart+".jpg"));
        }
        catch(Exception e){ //Otherwise, read from relative path in .jar.
            myPicture = ImageIO.read(getClass().getResource("/resources/imgs/" + albumart + ".jpg"));
        }
        
        if(!imgSet){ // If the image JLabel wasn't already set
            picLabel = new JLabel(new ImageIcon(myPicture)); // New JLabel and picture
            imgSet = true;      // Set so it won't come here again next time
        }
        else{ // Otherwise, 
            picLabel.setIcon(new ImageIcon(myPicture)); // set the current Icon source to be new picture source.
        }
        add(picLabel,BorderLayout.CENTER);   // Render the picture to be centered.         
    }
    
}
