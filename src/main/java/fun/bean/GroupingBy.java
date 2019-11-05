package fun.bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Date: 2018/11/23 10:17
 * @Description:
 */
public class GroupingBy<T, K>{

    private final Function<? super T, ? extends K> classifier;

    /**
     * @param classifier
     */

    public GroupingBy( Function<? super T, ? extends K> classifier ){
        this.classifier = classifier;
    }

    public Supplier<Map<K, List<T>>> supplier(){
        return HashMap::new;
    }


    public BiConsumer<Map<K, List<T>>, T> accumulator(){
        return (map, element) -> {
            K key = classifier.apply(element);
            List<T> elements = map.computeIfAbsent(key, k->new ArrayList());
            elements.add(element);
        };
    }

    public BinaryOperator<Map<K, List<T>>> combiner(){
        return (left, right) -> {
            right.forEach((key, value) -> {
                left.merge(key, value, (leftValue, rightValue)->{
                    leftValue.addAll(rightValue);
                    return leftValue;
                });
            });
            return left;
        };
    }

    public Function<Map<K, List<T>>, Map<K, List<T>>> finisher(){
        return map -> map;
    }


    /**
     * @param path
     */
    public static void wordCount(Path path) {
//        Map<String, Long> wordCount = Files.lines(path)
//                .parallel()
//                .flatMap(line -> Arrays.stream(line.trim().split("\\s")))
//                .map(word->word.replaceAll("[^a-zA-Z]]", "").toLowerCase().trim())
//                .filter(word -> word.length() > 0)
//                .map(word -> new AbstractMap.SimpleEntry(word, 1))
//                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.counting()));
    }


}
