package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

/* 500 px high
 * 
 */

public class Body extends JPanel {
    public Body() {
        setPreferredSize(new Dimension(350, 500));
        setBackground(Color.LIGHT_GRAY);
        
        setLayout(new BorderLayout());
        add(new JLabel("Main scrolling area", JLabel.CENTER), BorderLayout.CENTER);
    }
}
