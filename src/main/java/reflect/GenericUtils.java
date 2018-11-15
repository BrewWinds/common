package reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Auther:
 * @Date: 2018/11/15 11:09
 * @Description:
 */
public class GenericUtils {

    /**
     * 获取子类确切的参数
     */
    public static Class getActualType(Class<?> clazz, int argIndex){

        Type gType = clazz.getGenericSuperclass();
        if(!(gType instanceof ParameterizedType)){
            return Object.class;
        }

        Type[] params =  ((ParameterizedType) gType).getActualTypeArguments();
        if(argIndex >= params.length || argIndex < 0){
            return Object.class;
        }

        Type t = params[argIndex];

        if(t instanceof ParameterizedType){
            return (Class) ((ParameterizedType) t).getRawType();
        }else if(t instanceof GenericArrayType){
            Type tmp = ((GenericArrayType) t).getGenericComponentType();
            return Array.newInstance((Class<?>)((ParameterizedType)tmp).getRawType(), 0).getClass();
        }else if(t instanceof Class<?>){
            return (Class<?>)t;
        }else{
            return Object.class;
        }
    }

}
