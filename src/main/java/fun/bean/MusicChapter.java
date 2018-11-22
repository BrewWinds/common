package fun.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2018/11/21 11:05
 * @Description:
 */
public abstract class MusicChapter {

    protected final List<Artist> artists;
    protected final List<Album> albums;

    public MusicChapter() {
        artists = new ArrayList<>();
        albums = new ArrayList<>();
        loadData("");
    }

    private void loadData(String file) {

    }

}