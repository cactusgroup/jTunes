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
        Dimension d = resultsPane.getPreferredSize();
        d.setSize((int)d.getWidth(), searchResults.size() * 50);
        resultsPane.setPreferredSize(d);
        
        searchResults.get(searchResults.size() - 1).setLast(true);
        for (SearchResult s : searchResults) { 
            s.setResponse(response);
            resultsPane.add(s);
        }
    }
    
    // This clears all of the result panes. 
    public void clearSearchResults() {
        resultsPane.removeAll();
        resultsPane.repaint();
        resultsPane.revalidate();
    }
}
