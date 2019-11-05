package kafka;

/**
 * @Date: 2019/2/20 18:11
 * @Description:
 */
public interface ConsumeExceptionHandler {

    boolean retry(Throwable th, int count);

}
