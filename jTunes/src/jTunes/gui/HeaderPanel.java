package jTunes.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/* 50 px high
 * +------------------------------+
 * | = [Albums]                 Q |
 * +------------------------------+
 * 
 */
public class HeaderPanel extends JPanel {
    private String menuIconPath = "../resources/menuicon.png";
    private String searchIconPath = "../resources/searchicon.png";
    private JLabel title;
    private boolean showingSearch = false;
    
    public HeaderPanel(String title) {
        this(title, 350);
    }
    public HeaderPanel(String t, int width) {
        // set up title text
        title = new JLabel(t);
        
        // set up layout, size, and background color
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, 50));
        setBackground(ColorConstants.MINT_GREEN);
        
        // Menu button initialization
        // With attached MouseListener
        // LEFT (LINE_START)
        CustomButton menuButton = new CustomButton(
                menuIconPath,
                "\u2261",
                60,
                ColorConstants.MINT_GREEN);
            menuButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    // show slide-in menu
                }
            });
        add(menuButton, BorderLayout.LINE_START);
        
        // Title bar / search area initialization
        // CENTER
        JPanel cards = new JPanel(new CardLayout());
            JPanel titleCard = new JPanel(new BorderLayout());
                titleCard.setBackground(ColorConstants.MINT_GREEN);
                titleCard.add(title, BorderLayout.CENTER);
            JPanel searchCard = new JPanel(new BorderLayout());
                /* The text of this search box is used to
                 * inform the SearchSuggestionPanel. */
                // TODO: attach StateChange listener
                JTextField searchBox = new JTextField();
                searchCard.setBackground(ColorConstants.MINT_GREEN);
                searchCard.add(searchBox, BorderLayout.CENTER);
        cards.add(titleCard, "title");
        cards.add(searchCard, "search");
        add(cards, BorderLayout.CENTER);
        
        // Search button initialization
        // Attached MouseListener
        // RIGHT (LINE_END)
        CustomButton searchButton = new CustomButton(
                searchIconPath,
                "Q",
                30,
                ColorConstants.MINT_GREEN);
            /* On each click, the CENTER area toggles between
             * title and search area */
            searchButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    if (showingSearch)
                        cl.show(cards, "title");
                    else
                        cl.show(cards, "search");
                    showingSearch = !showingSearch;
                }
            });
        add(searchButton, BorderLayout.LINE_END);
    }
    
    public void setTitle(String t) {
        title.setText(t);
    }
}
