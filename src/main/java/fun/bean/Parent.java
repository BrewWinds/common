package fun.bean;

/**
 * @Date: 2018/11/22 16:05
 * @Description:
 */
public interface Parent {

    void message(String body);

    default void welcome(){
        message("Parent: Hi!");
    }

    String getLastMessage();

}
