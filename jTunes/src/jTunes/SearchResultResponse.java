/**
 * 
 */
package jTunes;

/**
 * This interface allows our SearchResult's to respond to
 * a mouse click.
 * @author joshuachu
 */
@FunctionalInterface
public interface SearchResultResponse {
    public void respond(String response);
}
