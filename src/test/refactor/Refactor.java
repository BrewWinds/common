package refactor;

import fun.bean.Album;
import fun.bean.SampleData;
import fun.bean.Track;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: 01378178
 * @Date: 2018/11/21 11:22
 * @Description:
 */
public class Refactor{

    public Set<String> beforeTracks(List<Album> albums){
        Set<String> trackNames = new HashSet<>();
        for(Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        System.out.println(trackNames.size());
        return trackNames;
    }

    public void trackRefactor(List<Album> albums){

        Set<String> trackNames = albums.stream()
                .flatMap(x->x.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(Track::getName).collect(Collectors.toSet());

    }

    public static void main(String[] args) {
        Refactor refactor = new Refactor();
        List<Album> albums = SampleData.albums.collect(Collectors.toList());
        refactor.beforeTracks(albums);
        refactor.trackRefactor(albums);

    }
}
