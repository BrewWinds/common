package reflect.test;

import java.util.List;
import java.util.Set;

/**
 * @Date: 2018/11/15 11:46
 * @Description:
 */
public class ParameterizedTypeTest<T> {
    public List<T> list = null;
    public Set<String> set = null;

    public static void main(String[] args) throws NoSuchFieldException {


//        Field filed2 = ParameterizedTypeTest.class.getDeclaredField("set");
//        Type setType = filed2.getGenericType();
//        System.out.println(setType.getClass().getTypeName());

    }
}
