package util;

/**
 * @Date: 2019/6/14 14:00
 * @Description:
 */
public class Obj {

    public static Object orElse(Object o, Object def){
        return o == null ? def : o;
    }
}
