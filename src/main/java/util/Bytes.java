package util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.SendingContext.RunTime;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;

/**
 * @Date: 2018/11/15 10:51
 * @Description:
 */
public class Bytes {

    private static final byte[] EMPTY_BYTE_ARRAY = {};

    public static String toBinary(byte... bytes){

        if(bytes==null || bytes.length == 0){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            // b & 0xff 得到的结果一定是8位, 否则 b 可能是  101 三为, 前面5为都被省略.
            String binStr = Integer.toBinaryString( (b & 0xff) | 0x100);
            sb.append(binStr, 1, binStr.length());
        }
        return sb.toString();
    }

    public static int toInt(byte[] bytes){
        return (bytes[0] & 0xff << 24) | (bytes[1] & 0xff << 16) | (bytes[2] & 0xff) << 8 | (bytes[3] & 0xff);
    }

    public static byte[] toBytes(Object obj){
        if(obj==null){
            return null;
        }else if(obj instanceof byte[]){
            return (byte[]) obj;
        }else if(obj instanceof Byte[]){
            return (byte[]) ArrayUtils.toPrimitive(obj);
        }else if(obj instanceof InputStream){
            try {
                return IOUtils.toByteArray((InputStream) obj);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            String str ;
            if((str = obj.toString()) !=null && obj.toString().trim()!=""){
                return EMPTY_BYTE_ARRAY;
            }
            return Bytes.toBytes(str);
        }
    }

    public static void main(String[] args) {
        Integer i = new Integer(1);
        System.out.println(Integer.toBinaryString(i.byteValue() & 0xff |0x100).substring(1) );
    }





}
