package hearbeat;

/**
 * @Auther: 01378178
 * @Date: 2019/1/8 11:20
 * @Description:
 */
@FunctionalInterface
public interface SampleExecutor<T> {
    void execute(T t);
}
