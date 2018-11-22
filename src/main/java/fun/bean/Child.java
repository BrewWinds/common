package fun.bean;

/**
 * @Auther: 01378178
 * @Date: 2018/11/22 16:32
 * @Description:
 */
public interface Child extends Parent{
    @Override
    default void welcome() {
        message("Child:Hi!");
    }
}
