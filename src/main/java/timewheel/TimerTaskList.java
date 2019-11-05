package timewheel;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Date: 2019/6/12 10:04
 * @Description:
 */
public class TimerTaskList implements Delayed {

    private AtomicLong expiration = new AtomicLong(-1);

    TimerTaskEntry root = new TimerTaskEntry(null, -1L);

    private AtomicInteger taskCounter;

    public TimerTaskList(AtomicInteger taskCounter){
        this.taskCounter = taskCounter;
        root.next = root;
        root.prev = root;
    }
    /**
     * set the bucket's expiration time
     * returns true if the expiration time is changed.
     */
    public Boolean setExpiration(Long expiration){
        return this.expiration.getAndSet(expiration) != expiration;
    }

    public Long getExpiraton(){
        return expiration.get();
    }

    void foreach(Function f){
        synchronized (this){
            TimerTaskEntry entry = root.next;
            while(entry!=null){
                TimerTaskEntry nextEntry = entry.next;
                if(!entry.cancelled()){
                    f.apply(entry.timerTask);
                }
                entry = nextEntry;
            }
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max((getExpiraton() - System.nanoTime()), 0), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed d) {
        if(d instanceof TimerTaskList){
            if(getExpiraton() < ((TimerTaskList) d).getExpiraton())
                return -1;
            else if(getExpiraton() > ((TimerTaskList) d).getExpiraton()){
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public void add(TimerTaskEntry timerTaskEntry){
        boolean done = false;
        while(!done){
            timerTaskEntry.remove();

            synchronized (this){
                synchronized (timerTaskEntry) {

                    if(timerTaskEntry.list == null){
                        TimerTaskEntry tail = root.prev;
                        timerTaskEntry.next = root;
                        timerTaskEntry.prev = tail;
                        timerTaskEntry.list = this;
                        tail.next = timerTaskEntry;
                        root.prev = timerTaskEntry;
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    public void remove(TimerTaskEntry timerTaskEntry){
        synchronized(this){
            synchronized (timerTaskEntry) {
                if(timerTaskEntry.list.equals(this)){
                    timerTaskEntry.prev.next = timerTaskEntry.next;
                    timerTaskEntry.next.prev = timerTaskEntry.prev;
                    timerTaskEntry.next = null;
                    timerTaskEntry.prev = null;
                    timerTaskEntry.list = null;
                    taskCounter.decrementAndGet();
                }
            }
        }
    }
    public void flush(Consumer c){
        synchronized (this){
            TimerTaskEntry head = root.next;
            while(head!=null){
                remove(head);
                c.accept(head);
                head = root.next;
            }
            expiration.set(-1L);
        }
    }
}

class TimerTaskEntry{

    public TimerTaskList list = null;
    public TimerTaskEntry next = null;
    public TimerTaskEntry prev = null;
    public Long expirationMs;

    TimerTask timerTask;

    public TimerTaskEntry(TimerTask timerTask, Long expirationMs){

        if(timerTask != null){
            this.timerTask = timerTask;
            this.timerTask.setTimerTaskEntry(this);
        }
        this.expirationMs = expirationMs;
    }

    public void remove(){
        TimerTaskList currentList = list;
        while(currentList!=null){
            currentList.remove(this);
            currentList = list;
        }
    }

    public Boolean cancelled(){
        return timerTask.getTimerTaskEntry() != this;
    }

}