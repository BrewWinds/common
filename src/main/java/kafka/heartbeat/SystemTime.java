package kafka.heartbeat;

/**
 * @Date: 2019/4/22 10:46
 * @Description:
 */
public class SystemTime implements Time{

    @Override
    public long nanoseconds() {
        return System.nanoTime();
    }

    @Override
    public void sleep(long ms) {
        try{
            Thread.sleep(ms);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public long milliseconds() {
        return System.currentTimeMillis();
    }
}
