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

@SuppressWarnings("serial")
public class BodyPanel extends JPanel {
    private static JLabel picLabel;
    private boolean imgSet = false;
    public BodyPanel() {
        setPreferredSize(new Dimension(350, 500));
    }
    
    public void SetAlbumArt(String albumart) throws IOException{
        BufferedImage myPicture;
        try{myPicture = ImageIO.read(new File("src/resources/imgs/"+albumart+".jpg"));}
        catch(Exception e){
        myPicture = ImageIO.read(getClass().getResource("/resources/imgs/" + albumart + ".jpg"));}
        
        if(!imgSet){
            picLabel = new JLabel(new ImageIcon(myPicture));
            imgSet = true;
        }
        else{
            picLabel.setIcon(new ImageIcon(myPicture));
        }
        add(picLabel,BorderLayout.CENTER);            
    }
    
}
