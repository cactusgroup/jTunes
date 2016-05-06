package jTunes;
import java.util.List;

interface IDbAccess {
    List<String> getGenres();
    List<String> getArtists(Criteria l);
    List<String> getAlbums(Criteria l);
    List<String> getSongs(Criteria l);
}
