package fun.bean;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @Date: 2018/11/22 17:09
 * @Description:
 */
public class Artists {

    private List<Artist> artists;

    public Artists(List<Artist> artists) {
        this.artists = artists;
    }

    public Artist getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            indexException(index);
        }
        return artists.get(index);
    }

    private void indexException(int index) {
        throw new IllegalArgumentException(index +
                "doesn't correspond to an Artist");
    }

    public String getArtistName(int index) {
        try {
            Artist artist = getArtist(index);
            return artist.getName();
        } catch (IllegalArgumentException e) {
            return "unknown";
        }
    }

    public Optional<Artist> getArtist2(int index){
        if(index<0 || index >= artists.size()){
            return Optional.empty();
        }
        return Optional.of(artists.get(index));
    }

    public String getArtistName2(int index){
        Optional<Artist> artist = getArtist2(index);
        return artist.map(Artist::getName).orElse("unknown");
    }

    public static void main(String[] args) {
    }
}
