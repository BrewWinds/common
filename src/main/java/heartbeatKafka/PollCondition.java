package heartbeatKafka;

/**
 * @Date: 2019/4/12 15:31
 * @Description:
 */
public interface PollCondition {

    boolean shouldBlock();

}
