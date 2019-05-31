package fun.bean;

import java.util.stream.Stream;

/**
 * @Date: 2018/11/21 11:03
 * @Description:
 */
public interface Performance {

    String getName();

    Stream<Artist> getMusicians();

    // TODO: test
    default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return Stream.concat(Stream.of(artist), artist.getMembers());
        });
    }

}