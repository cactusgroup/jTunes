package jTunes.gui;

import jTunes.QueryResponse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SearchResultPanel extends JPanel {
    private List<SearchResult> results;
    private JPanel resultsPane;
    
    public SearchResultPanel() {
        results = new ArrayList<>(15);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(350, 500));
        setBackground(Color.WHITE);
        
        resultsPane = new JPanel();
        resultsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        resultsPane.setPreferredSize(new Dimension(350, 0));
        resultsPane.setBackground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(
                resultsPane,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(5, 500));
        
        add(scroll);
    }
    
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
    
    public void clearSearchResults() {
        resultsPane.removeAll();
        resultsPane.repaint();
        resultsPane.revalidate();
    }
}
