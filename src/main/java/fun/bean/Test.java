package fun.bean;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther: 01378178
 * @Date: 2018/11/22 10:01
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        //chapter 3, question 1.a,
        Stream<Integer> stream = Stream.of(1, 4, 51, 32, 1, 5, 6, 7);
        System.out.println(addUp(stream));

        //chapter 3, question 1.b
        List<Artist> artists = SampleData.getThreeArtists();
        System.out.println(artistsAndNationality(artists));

        //chapter Q 1.c
        List<Album> albums = SampleData.albums.collect(Collectors.toList());
        songs(albums);

        // Q 2
        long count = artists.stream().flatMap((x) -> x.getMembers()).count();
        long count2 = artists.stream().map(x -> x.getMembers().count()).reduce(0L, Long::sum);
        System.out.println(count);
        System.out.println(count2);

        // --
        System.out.println(countChars("apple count SDFDF "));

        Optional<String> opt = maxLengthLowerStr(Lists.newArrayList());
        System.out.println(opt.isPresent() + " " + opt.orElse("null value present"));

        // --
        System.out.println(filter(Lists.newArrayList("a", "b", "c"), (x) -> x.equals("a")));

        System.out.println("================================================");
        IntStream.range(0, 1).forEach(System.out::println);

        // --

        // --
        String result = artists.stream().map(Artist::getName).collect(Collectors.joining(",", "[", "]"));

        // --
        System.out.println(longestName());
        System.out.println(longestName2());
    }

    static Stream<String> getNameStream() {
        Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
        return names;
    }

    public static Optional<String> longestName() {
        Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
        return names.collect(Collectors.maxBy(Comparator.comparing(String::length)));
    }

    public static Optional<String> longestName2() {
        Stream<String> names = getNameStream();
        return names.reduce((x, y)-> x.length() > y.length() ? x : y);
    }


    Optional<Artist> doTestFunction(List<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.stream().collect(Collectors.maxBy(Comparator.comparing(getCount)));
    }

    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream().collect(Collectors.averagingInt(a -> a.getTrackList().size()));
    }

    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
        return artists.collect(Collectors.partitioningBy(x -> x.isSolo()));
    }

    public Map<Boolean, List<Artist>> bandsAndSoloRef(Stream<Artist> artists) {
        return artists.collect(Collectors.partitioningBy(Artist::isSolo));
    }


    public boolean assertBoolan() {
        Optional<String> a = Optional.of("a");

        Optional opt = Optional.empty();
        Optional alsoEmpty = Optional.ofNullable(null);

        System.out.println(opt.isPresent());
        System.out.println(alsoEmpty.isPresent());

        opt.orElse("b");
        alsoEmpty.orElseGet(() -> "c");

        return true;
    }


    // int 4个字节, Integer对象16个字节  int, long, float
    // 如果方法返回类型为基本类型, 则在基本类型前加 to, toLongFunction(返回基本嫩类型)
    // 参数是基本类型则 LongFunction  mapToLong，

    static void printTrackLenStatistics(Album album) {

        IntSummaryStatistics trackLenStatistics = album.getTracks()
                .mapToInt(x -> x.getLength()).summaryStatistics();

        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                trackLenStatistics.getMax(),
                trackLenStatistics.getMin(),
                trackLenStatistics.getAverage(),
                trackLenStatistics.getSum());

    }

    static <I> List<I> filter(List<I> source, Predicate<I> predicate) {
        return source.stream().reduce(new ArrayList(),
                (r, t) -> {
                    if (predicate.test(t)) {
                        List<I> list = new ArrayList(r);
                        list.add(t);
                        return list;
                    } else {
                        return r;
                    }
                },
                (List<I> l, List<I> r) -> {
                    List<I> rs = Lists.newArrayList();
                    rs.addAll(l);
                    rs.addAll(r);
                    return rs;
                });
    }

    /**
     * function as map
     *
     * @param source
     * @param func
     * @param <I>
     * @param <O>
     * @return
     */
    static <I, O> List<O> map(List<I> source, Function<I, O> func) {
        return source.stream().reduce(new ArrayList(), (r, t) -> {
            List<O> list = new ArrayList(r);
            list.add(func.apply(t));
            return list;
        }, (List<O> l, List<O> r) -> {
            List<O> newL = Lists.newArrayList(l);
            newL.addAll(r);
            return newL;
        });
    }

    /**
     *
     */
    static void streamMehtod() {
        Stream.generate(() -> "Hello").limit(4).forEach(System.out::println);
        Stream.iterate(0, x -> x + 3).limit(5).forEach(System.out::println);
    }

    /**
     * @param str
     * @return
     */
    static Optional<String> maxLengthLowerStr(List<String> str) {
        return str.stream().max(Comparator.comparing(Test::countChars));
    }


    /**
     * @param str
     * @return
     */
    static int countChars(String str) {
        int lowerCount = (int) str.chars().filter(Character::isLowerCase).count();
        return lowerCount;
    }

    /**
     * Album > Track Name
     * group by Album , List<Name>
     *
     * @param albums
     * @return
     */
    static List<Album> songs(List<Album> albums) {
        return albums.stream().filter(album -> album.getTrackList().size() <= 3).collect(Collectors.toList());
    }

    /**
     * @param artists
     * @return
     */
    static List<String> artistsAndNationality(List<Artist> artists) {
        return artists.stream().map(x -> {
            return x.getName() + x.getNationality();
        }).collect(Collectors.toList());
    }

    /**
     * @param numbers
     * @return
     */
    static int addUp(Stream<Integer> numbers) {
        return numbers.reduce((x, y) -> {
            return x + y;
        }).get();
    }
}
