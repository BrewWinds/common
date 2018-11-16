package reflect.test;

import reflect.GenericUtils;


/**
 * @Date: 2018/11/15 11:26
 * @Description:
 */
public abstract class AbstractDao<T> {

    public AbstractDao(){
        Class<?> clazz = this.getClass();
        Class<T> classType = GenericUtils.getActualType(clazz, 0);
    }

    public abstract <T, R> void testA(Class<? extends AbstractDao> a, String r);
}
