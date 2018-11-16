package reflect;

import java.lang.reflect.*;

/**
 * @Auther:
 * @Date: 2018/11/15 11:09
 * @Description:
 */
public class GenericUtils {

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


    public static Class<?> getType(Method method, int argIdx){
        return getClassType(method, argIdx, 0);
    }

    /**
     * public R handle(T, A)
     *
     * public Apple handle(Map<String, Integer> map, Apple a)
     * @param method
     * @param argIdx
     * @param genIdx
     * @return
     */
    public static Class<?> getClassType(Method method, int argIdx, int genIdx){
        Type type = method.getGenericParameterTypes()[argIdx];
        return getActualClassType(((ParameterizedType)type).getActualTypeArguments()[genIdx]);
    }


    public static Class<?> getActualClassType(Type type){

        if(type instanceof Class<?>){
            return (Class<?>) type;

        }else if(type instanceof WildcardType){

            WildcardType wildCard = (WildcardType) type;

            if( wildCard.getUpperBounds()[0] instanceof Object){
                return (Class<?>) wildCard.getUpperBounds()[0];
            }else if(wildCard.getLowerBounds()==null || wildCard.getLowerBounds().length==0){
                return (Class<?>) ((WildcardType) type).getLowerBounds()[0];
            }else{
                return Object.class;
            }
        }

        return Object.class;
    }

}
