package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/* 50 px high
 * +------------------------------+
 * | = [Albums]                 Q |
 * +------------------------------+
 * 
 */
public class Header extends JPanel {
    private Image i;
    private String title;
    
    public Header(String title) throws IOException {
        this(title, 350);
    }
    public Header(String t, int width) throws IOException {
        title = t;
        
        setPreferredSize(new Dimension(width, 50));
        setBackground(ColorConstants.MINT_GREEN);
        
        setLayout(new BorderLayout());
        add(new JLabel("Q"), BorderLayout.LINE_END);
        add(new JLabel("Albums"), BorderLayout.CENTER);
        
//        try {
//            i = ImageIO.read(getClass().getResource("resources/images/menuicon.png"));
//        } catch (IOException e) {
//            System.err.println("Menu icon not found.");
//            e.printStackTrace();
//            throw e;
//        }
//        JPanel iconPanel = new JPanel(new BorderLayout());
//        iconPanel.setBorder(new EmptyBorder(10,10,10,10));
//        iconPanel.add(new JLabel(new ImageIcon(i)), BorderLayout.CENTER);
//        add(iconPanel, BorderLayout.LINE_START);
        add(new JLabel("="), BorderLayout.LINE_START);
    }
    
    public void setTitle(String t) {
        title = t;
    }
}
