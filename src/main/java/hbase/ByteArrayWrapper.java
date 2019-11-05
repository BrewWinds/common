package hbase;

import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Auther: 01378178
 * @Date: 2018/12/3 11:59
 * @Description:
 */
public class ByteArrayWrapper implements Serializable, Comparable<ByteArrayWrapper> {

    private final byte[] array;

    public ByteArrayWrapper(byte[] array){
        if(array == null){
            throw new NullPointerException();
        }
        this.array = array;
    }

    public ByteArrayWrapper(Byte[] array){
        if(array == null){
            throw new NullPointerException();
        }
        this.array = ArrayUtils.toPrimitive(array);
    }

    @Override
    public int compareTo(ByteArrayWrapper o) {
        if(o==null){
            return 1;
        }
        return BytesArrayComparator.compareTo(array, o.array);
    }

    public byte[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ByteArrayWrapper)){
            return false;
        }
        return Arrays.equals(array, ((ByteArrayWrapper) obj).getArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
