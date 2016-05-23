package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import jTunes.database.ValueType;
import jTunes.SearchResultResponse;

@SuppressWarnings("serial")
// This is our SearchResult object which displays and holds the choices we are given on the
// gui for what genre, artist, album, and song to pick from in the database.
public class SearchResult extends JPanel {
    private Border outerBorder;
    private Border innerBorder;
    private boolean last;
    private SearchResultResponse myResponse;

    public SearchResult(ValueType type, String text)  {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(340, 50));
        setMaximumSize(getPreferredSize());
        setBackground(Color.WHITE);
        
        // setting divider and padding
        outerBorder = BorderFactory.createMatteBorder(
                0, 0, 1, 0,
                Color.decode("0xDEDEDE"));
        innerBorder = BorderFactory.createMatteBorder(
                5, 5, 5, 5,
                Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                        outerBorder,                    // divider
                        innerBorder));                  // padding
        
        // default state is "not the last" search resut
        last = false;
        
        // set the search result text
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add(label);
        
        // make this panel register like a button
        addMouseListener(new MouseAdapter() {
            private SearchResult s = SearchResult.this;
            public void mousePressed(MouseEvent e) {
                // button down style
                s.setBackground(ColorConstants.LIGHT_MINT);
                s.setInnerBorder(ColorConstants.LIGHT_MINT);
            }
            public void mouseReleased(MouseEvent e) {
                // button up style
                s.setBackground(Color.WHITE);
                s.setInnerBorder(Color.WHITE);
                
                // also, register with App that "I was clicked!"
                myResponse.respond(text);
            }
        });
    }
    
    
    // sets this search result as the last in the list
    public void setLast(boolean last) {
        this.last = last;
        
        if (last) {
            setBorder(innerBorder);
            return;
        }
        
        // useful case for resetting last result to "not last"
        // if (!last)
        setBorder(BorderFactory.createCompoundBorder(
                  outerBorder,
                  innerBorder));
    }
    
    public void setInnerBorder(Color color) {
        innerBorder = BorderFactory.createMatteBorder(5, 5, 5, 5, color);
        setLast(this.last);
    }
    
    public void setResponse(SearchResultResponse response) {
        myResponse = response;
    }
}
