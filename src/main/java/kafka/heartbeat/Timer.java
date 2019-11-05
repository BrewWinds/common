package kafka.heartbeat;

/**
 * @Date: 2019/4/22 10:45
 * @Description:
 */
public class Timer {
    private final Time time;
    private long startMs;
    private long currentTimeMs;
    private long deadlineMs;


    Timer(Time time, long timeoutMs){
        this.time = time;
        update();
        reset(timeoutMs);
    }

    public void update(){
        update(time.milliseconds());
    }

    public void update(long currentTimeMs){
        this.currentTimeMs = Math.max(currentTimeMs, this.currentTimeMs);
    }


    public void reset(long timeoutMs){
        if(timeoutMs < 0){
            throw new IllegalArgumentException("Invalid negative timeout");
        }

        this.startMs = this.currentTimeMs;

        if(currentTimeMs > Long.MAX_VALUE - timeoutMs){
            this.deadlineMs = Long.MAX_VALUE;
        }else{
            this.deadlineMs = currentTimeMs + timeoutMs;
        }


    }

    public boolean isExpired(){
        return currentTimeMs >= deadlineMs;
    }
}
