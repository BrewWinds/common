package kafka.heartbeat;

import java.time.Duration;

/**
 * @Date: 2019/4/22 10:45
 * @Description:
 */
public interface Time {

    long nanoseconds();

    void sleep(long ms);

    long milliseconds();

    default Timer timer(Duration timeout){
        return timer(timeout.toMillis());
    }

    default Timer timer(long timeoutMs){
        return new Timer(this, timeoutMs);
    }
}
