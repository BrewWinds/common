package heartbeatKafka;

/**
 * @Date: 2019/4/12 15:36
 * @Description:
 */
public interface RequestFutureListener<T> {

    void onSuccess(T value);

    void onFailure(RuntimeException e);

}
