package timewheel;

/**
 * @Auther: 01378178
 * @Date: 2019/6/12 11:05
 * @Description:
 */
public interface Timer {

    void add(TimerTask timerTask);

    Integer size();

    void shutdown();

    Boolean advanceClock(Long timeoutMs);

}
