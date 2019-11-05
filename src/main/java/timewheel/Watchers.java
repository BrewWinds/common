package timewheel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: 01378178
 * @Date: 2019/6/12 16:25
 * @Description:
 */
public class Watchers<T> {

    private ConcurrentLinkedQueue operations = new ConcurrentLinkedQueue();

    Integer countWatched = operations.size();
    Boolean isEmpty = operations.isEmpty();


    Watchers(T t){
        operations.add(t);
    }

}
