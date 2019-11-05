package timewheel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: 01378178
 * @Date: 2019/6/12 15:50
 * @Description:
 */
public class DealyedOperationPurgatory {

    Timer timer;
    Integer purgeInterval;
    Boolean reaperEnabled;
    Boolean timerEnabled;
    Integer brokerId;
    String purgagoryName;

    public DealyedOperationPurgatory(String purgatoryName,
                                     Timer timeoutTimer,
                                     Integer brokerId,
                                     Integer purgeInterval,
                                     Boolean reaperEnabled,
                                     Boolean timerEnable){

        this.purgagoryName = purgatoryName;
        this.timer = timeoutTimer;
        this.purgeInterval = purgeInterval == null ? 1000 : purgeInterval;
        this.reaperEnabled = reaperEnabled == null ? true : reaperEnabled;
        this.timerEnabled = timerEnable == null ? true : timerEnable;
        this.brokerId = brokerId == null ? 0 : brokerId;
    }

    private class WatcherList{
        Pool wathersByKey = new Pool();
    }

  /*  private class Watchers{
        ConcurrentLinkedQueue<DelayedOperation> operations = new ConcurrentLinkedQueue();
        Integer countWatched = operations.size();
        Boolean isEmpty = operations.isEmpty();

        public void watch(DelayedOperation t){
            operations.add(t);
        }

        Integer tryCompleteWatched(){

            Integer completed = 0;

            Iterator<DelayedOperation> iter = operations.iterator();

            while(iter.hasNext()){
                DelayedOperation curr = iter.next();
                if(curr.isCompleted()){
                    iter.remove();
                }else if(curr.maybeTryComplete()){
                    iter.remove();
                    completed += 1;
                }
            }

            if(operations.isEmpty()){
            }
        }
    }*/
}
