package kafka.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Date: 2019/2/20 16:40
 * @Description:
 * This is the data buffer which play an
 * important role in the transactional consumer,
 * which store the offset to be commit.
 *
 * It is operated by more than 2 threads, and one
 * task which will generate multiple thread for.
 *
 * So concurrency handling is necessary.
 *
 */
public class OffsetHolder {

    private Logger logger = LoggerFactory.getLogger(OffsetHolder.class);

    private BlockingDeque<Long> queue = new LinkedBlockingDeque<>(1000);
    private Map<Long, Boolean> finishOffSet = new ConcurrentHashMap<>();

    /**
     * + to queue
     * @param offset
     * @return
     */
    public boolean init(long offset){
        Long last = queue.peekLast();
        if(last!=null && last > offset){
            // warn
            return false;
        }

        boolean success = false;
        while(!success){
            try {
                queue.push(offset);
                success = true;
            }catch(/*InterruptedException e*/ Exception e){
                //log
            }
        }
        return success;

    }

    /**
     * add to finishOffset and set value as true
     * @param offset
     */
    public void complete(long offset){
        this.finishOffSet.put(offset, Boolean.TRUE);
    }

    /**
     * get the latest offset that is finished.
     *
     * offset = queue.peek();
     *
     * return (lastOffset = 1)
     *
     * otherwise, offset in queue has been updated.
     * commit will lost.   offset consumed will be consumed once again.
     * idempotent necessary.
     *
     * @return
     */
    public long clacOffSet(){

        long lastOffSet = -1;

        /**
         * case is that after removing offset from queue, before committing
         * to the kafka, system corrupts. and offset will lost.
         */
        while(true) {
            Long offset = queue.peek();
            if (offset == null || !finishOffSet.containsKey(offset)) {
                return lastOffSet;
            }

            queue.remove();
            lastOffSet = offset;
            finishOffSet.remove(offset);
        }
    }
}
