package fun.bean;

import java.util.stream.Stream;

/**
 * @Date: 2018/11/21 11:03
 * @Description:
 */
public interface Performance {

    public String getName();

    public Stream<Artist> getMusicians();

    // TODO: test
    public default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return Stream.concat(Stream.of(artist), artist.getMembers());
        });
    }

}