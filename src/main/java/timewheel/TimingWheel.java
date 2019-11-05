package timewheel;

import com.google.common.collect.Lists;
import org.apache.hadoop.mapreduce.TaskCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date: 2019/6/12 10:04
 * @Description:
 */
public class TimingWheel {

    private Long interval;
    private List<TimerTaskList> buckets;
    private Long currentTime;
    private Integer wheelSize;
    private AtomicInteger taskCounter;
    private Long tickMs;

    private TimingWheel overflowWheel;

    private DelayQueue queue;

    public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter,
                       DelayQueue<TimerTaskList> queue){

        this.tickMs = tickMs;
        this.interval = tickMs * wheelSize;
        this.buckets = new ArrayList<>(wheelSize);

        // rounding down to multiple of tickMs
        this.currentTime = startMs - (startMs % tickMs);
        this.wheelSize = wheelSize;
        this.taskCounter = taskCounter;
        this.queue = queue;
    }

    public void addOverflowWheel(){
        synchronized (this){
            overflowWheel = new TimingWheel(interval, wheelSize, currentTime, taskCounter, queue);
        }
    }

    public Boolean add(TimerTaskEntry timerTaskEntry){

        Long expiration = timerTaskEntry.expirationMs;

        if(timerTaskEntry.cancelled()){
            return false;
        }else if(expiration < currentTime + tickMs){
            return false;
        }else if(expiration < currentTime + interval){
            Long virtualId = expiration / tickMs;
            TimerTaskList bucket = buckets.get((int) (virtualId%wheelSize));
            bucket.add(timerTaskEntry);
            if(bucket.setExpiration(virtualId * tickMs)){
                queue.offer(bucket);
            }
            return true;
        }else{
            if(overflowWheel == null){
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }

    // 更新当前时间
    public void advanceClock(Long timeMs){
        if(timeMs >= currentTime + tickMs){
            currentTime = timeMs - (timeMs % tickMs);

            if(overflowWheel!=null){
                overflowWheel.advanceClock(currentTime);
            }
        }
    }
}
