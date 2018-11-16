package reflect.test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @Date: 2018/11/15 11:27
 * @Description:
 */
public class ConcreteDao extends AbstractDao<TestModel> {


    public ConcreteDao() throws NoSuchMethodException {
    }

    @Override
    public <T, R> void testA(Class<? extends AbstractDao> map, String r) {

    }

    public <T, R> void testB(Class<? super AbstractDao> map, String r){
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ConcreteDao dao = new ConcreteDao();
        Method m = ConcreteDao.class.getDeclaredMethods()[1];
        Type type = m.getGenericParameterTypes()[0];
        Type t =((ParameterizedType)type).getActualTypeArguments()[0];

        //
        System.out.println(t instanceof WildcardType);
        System.out.println(((WildcardType)t).getUpperBounds()[0]);
//        System.out.println(((WildcardType)t).getUpperBounds()[1]);

        //
        //System.out.println(((WildcardType)t).getLowerBounds()[0]);

//        System.out.println(type instanceof ParameterizedType);
//        System.out.println(type instanceof Class<?>);
//        System.out.println(type instanceof WildcardType);

    }


}
