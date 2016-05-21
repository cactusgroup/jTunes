package jTunes.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SearchResultPanel extends JPanel {
    private List<SearchResult> results;
    
    public SearchResultPanel() {
        results = new ArrayList<>(15);
        
        setPreferredSize(new Dimension(350,550));
        setBackground(Color.CYAN);
        setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GREEN));
        
        for (int i = 0; i < 3; ++i)
            results.add(new SearchResult());
        
        for (int i = 0; i < 3; ++i)
            add(results.get(i));
    }
    
    private class SearchResult extends JPanel {
        public SearchResult()  {
            setPreferredSize(new Dimension(330, 50));
            setBackground(Color.RED);
            setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
        }
    }
}
