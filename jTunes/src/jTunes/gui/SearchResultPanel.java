package jTunes.gui;

import jTunes.QueryResponse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class SearchResultPanel extends JPanel {
    private JPanel resultsPane;
    // This class is the panel that contains all of the selections we need to search for songs.
    // i.e., the results of database searches for genre, artist, etc. will appear here.
    public SearchResultPanel() {
        // Initializing the layout of the panel.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(350, 500));
        setBackground(Color.WHITE);
        
        resultsPane = new JPanel();
        resultsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        resultsPane.setPreferredSize(new Dimension(350, 0));
        resultsPane.setBackground(Color.WHITE);
        // We will support vertical scrollbars if we have many elements.
        // It works very nicely.
        JScrollPane scroll = new JScrollPane(
                resultsPane,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(5, 500));
        
        add(scroll);
    }
    
    // Function to add the search results from the Menu class, which gets them from the SQL server.
    public void addSearchResults(List<SearchResult> searchResults,
                                 QueryResponse response) {
        // dynamic resizing of resultsPane to accommodate all search results
        Dimension d = resultsPane.getPreferredSize();
        d.setSize((int)d.getWidth(), searchResults.size() * 50);
        resultsPane.setPreferredSize(d);
        
        // the last search result displays without a divider
        searchResults.get(searchResults.size() - 1).setLast(true);
        
        // add search results to resultsPane
        // after giving them a way to respond if clicked.
        for (SearchResult s : searchResults) { 
            s.setResponse(response);
            resultsPane.add(s);
        }
    }
    
    // This clears all of the search results from our resultPane (read: our GUI)
    public void clearSearchResults() {
        resultsPane.removeAll();
        resultsPane.repaint();
        resultsPane.revalidate();
    }
}
