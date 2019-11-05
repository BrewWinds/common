package util;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Date: 2019/1/8 13:58
 * @Description:
 */
public class ObjectArrayWrapper<T> implements Serializable, Comparable<ObjectArrayWrapper<T>> {


    private final T[] array;

    public ObjectArrayWrapper(T[] array){
        if(array == null){
            throw new RuntimeException();
        }
        this.array = array;
    }

    public static <T> ObjectArrayWrapper<T> create(T[] array){
        if(array == null || array.length == 0){
            return null;
        }
        return new ObjectArrayWrapper<>(array);
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ObjectArrayWrapper)){
            return false;
        }
        return Arrays.equals(array, ((ObjectArrayWrapper) obj).array);
    }

    @Override
    public int compareTo(ObjectArrayWrapper o) {
        if( o == null){
            return 1;
        }

        return new CompareToBuilder().append(array, o.array).toComparison();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    public T[] getArray(){
        return array;
    }
}
