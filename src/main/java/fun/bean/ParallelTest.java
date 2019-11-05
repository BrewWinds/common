package fun.bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Date: 2018/11/23 15:17
 * @Description:
 */
public class ParallelTest {

    //** fork ,join 框架 to learn
    private int addIntegers(List<Integer> values){
        return values.parallelStream().mapToInt(i->i).sum();
    }

    //  Array, ArrayList, IntStream.range 性能好 随机读取, 所以轻易被分解.
    // HashSet, TreeSet, 不容易公平地被分解.
    // 链表 LinkedList, Stream.iterator， BufferedReader.lines
    // 数据结构影响了, parallel 的性能.
    // 无状态的在操作过程中, 不必维护状态.
    // 有状态则有维护状态所需的开销和限制.

    // 避开有状态, 选用无状态的操作. 获得更好的并行性能.
    // map, filter, flatMap, sorted, distinct, limit.

    // parallelPrefix   任意给定一个函数, 计算数组的和.
    // parallelSetAll   使用lambda表达式更新数组元素
    // parallelSort     并行化对数组元素排序

    public static double[] imperativeInitialize(int size){
        double[] values = new double[size];
        for(int i=0; i< values.length; i++){
            values[i] = i;
        }
        return values;
    }

    public static double[] parallelInitialize(int size){
        double[] values = new double[size];
        Arrays.setAll(values, i->i);
        Arrays.parallelSetAll(values, i->i);
        return values;
    }

    /** 0, 1, 2, 3, 4,  3.5
     *  0, 1, 3, 6, 10, 13.5
     * @param values
     * @param n
     * @return
     */
    public static double[] simpleMovingAverage(double[] values, int n){
        double[] sums = Arrays.copyOf(values, values.length);       //1
        Arrays.parallelPrefix(sums, Double::sum);                   //2
        int start = n-1;
        return IntStream.range(start, sums.length)                  //3
                .mapToDouble( i-> {
                    double prefix = i == start ? 0 : sums[i-n];
                    return (sums[i] - prefix) / n;                  //4
                }).toArray();                                       //5
    }

    public static double[] average(double[] values, int n){
        double[] sums = Arrays.copyOf(values, values.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = n-1;
        return IntStream.range(start, values.length)
                .mapToDouble( i-> {
                    double prefix = i == start ? 0 : sums[i-n];
                    return (sums[i] - prefix) / n;
                }).toArray();
    }

    public static void main(String[] args) {
        double[] sum = {0,1,2,3,4,3.5};
//        double[] copy = Arrays.copyOf(sum, sum.length);
//        Arrays.stream(copy).forEach(System.out::println);
//
//        System.out.println("===============================");
//        Arrays.parallelPrefix(copy, Double::sum);
//        Arrays.stream(copy).forEach(System.out::println);
        simpleMovingAverage(sum, 3);

    }

    public static int sequentialSumOfSquares(IntStream range){
        return range.parallel().map(x->x*x).sum();
    }

    public static int multiplyThrough(List<Integer> numbers){
        return 5 * numbers.parallelStream().reduce(1, (acc, x) -> x*acc );
    }


}
