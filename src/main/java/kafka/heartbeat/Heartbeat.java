package kafka.heartbeat;

/**
 * @Date: 2019/4/22 10:42
 * @Description:
 */
public class Heartbeat {
    private final int sessionTimeoutMs;
    private final int heartbeatIntervalMs;
    private final int maxPollIntervalMs;
    private final long retryBackoffMs;

    private final Time time;
    private final Timer heartbeatTimer;
    private final Timer sessionTimer;
    private final Timer pollTimer;

    private volatile long lastHeartbeatSend;

    public Heartbeat(Time time,
                     int sessionTimeoutMs,
                     int heartbeatIntervalMs,
                     int maxPollIntervalMs,
                     long retryBackoffMs) {
        if (heartbeatIntervalMs >= sessionTimeoutMs)
            throw new IllegalArgumentException("Heartbeat must be set lower than the session timeout");

        this.time = time;
        this.sessionTimeoutMs = sessionTimeoutMs;
        this.heartbeatIntervalMs = heartbeatIntervalMs;
        this.maxPollIntervalMs = maxPollIntervalMs;
        this.retryBackoffMs = retryBackoffMs;
        this.heartbeatTimer = time.timer(heartbeatIntervalMs);
        this.sessionTimer = time.timer(sessionTimeoutMs);
        this.pollTimer = time.timer(maxPollIntervalMs);
    }

    public boolean pollTimeoutExpired(long now){
        update(now);
        return pollTimer.isExpired();
    }

    public void update(long now){
        heartbeatTimer.update(now);
        sessionTimer.update(now);
        pollTimer.update(now);
    }
}
