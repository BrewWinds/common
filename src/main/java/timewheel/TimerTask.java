package timewheel;

/**
 * @Auther: 01378178
 * @Date: 2019/6/12 10:14
 * @Description:
 */
public abstract class TimerTask implements Runnable{

    // timestamp in millisecond
    Long dealyMs;

    private TimerTaskEntry timerTaskEntry = null;

    public TimerTaskEntry getTimerTaskEntry(){
        return timerTaskEntry;
    }


    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this){
            if(timerTaskEntry != null && this.timerTaskEntry!= entry){
                timerTaskEntry.remove();
            }
            this.timerTaskEntry = entry;
        }
    }

    void cancel(){
        if(timerTaskEntry != null){
            timerTaskEntry.remove();
            timerTaskEntry = null;
        }
    }

}
