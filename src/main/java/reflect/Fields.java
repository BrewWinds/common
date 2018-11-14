package reflect;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Auther:
 * @Date: 2018/11/14 13:45
 * @Description:
 */
public class Fields {
    private static final Unsafe UNSAFE;
    static{
        try{
          Field f = Unsafe.class.getDeclaredField("theUnsafe");
          f.setAccessible(true);

          UNSAFE = (Unsafe) f.get(null);
        }catch(Exception e){
            throw new RuntimeException("failed to get unsafe instance");
        }
    }

    public static void put(Object target, Field field, Object value){

        // 设置 accessible
        field.setAccessible(true);

        // 获取field 的偏移量
        long fieldOffset = UNSAFE.objectFieldOffset(field);

        // 获取类型
        Class<?> type = field.getType();

        if(Boolean.TYPE.equals(type)){
            UNSAFE.putBoolean(target, fieldOffset, ((boolean)value));
        }else if(Byte.TYPE.equals(type)){
            UNSAFE.putByte(target, fieldOffset, ((byte)value));
        }else if(Short.TYPE.equals(type)){
            UNSAFE.putShort(target, fieldOffset, ((short)value));
        }else if(Character.TYPE.equals(type)){
            UNSAFE.putChar(target, fieldOffset, ((char)value));
        }else if(Integer.TYPE.equals(type)){
            UNSAFE.putInt(target, fieldOffset, ((int)value));
        }else if(Long.TYPE.equals(type)){
            UNSAFE.putLong(target, fieldOffset, ((long)value));
        }else if(Double.TYPE.equals(type)){
            UNSAFE.putDouble(target, fieldOffset, ((double)value));
        }else if(Float.TYPE.equals(type)){
            UNSAFE.putFloat(target, fieldOffset, ((float)value));
        }else {
            UNSAFE.putObject(target, fieldOffset, value);
        }
    }

    public static Object get(Object target, Field field){

        long fieldOffset = UNSAFE.objectFieldOffset(field);

        Class<?> type = field.getType();

        if(Boolean.TYPE.equals(type)){
           return UNSAFE.getBoolean(target, fieldOffset);
        }else if(Byte.TYPE.equals(type)){
            return UNSAFE.getByte(target, fieldOffset);
        }else if(Short.TYPE.equals(type)){
            return UNSAFE.getShort(target, fieldOffset);
        }else if(Character.TYPE.equals(type)){
            return UNSAFE.getChar(target, fieldOffset);
        }else if(Integer.TYPE.equals(type)){
            return UNSAFE.getInt(target, fieldOffset);
        }else if(Long.TYPE.equals(type)){
            return UNSAFE.getLong(target, fieldOffset);
        }else if(Double.TYPE.equals(type)){
            return UNSAFE.getDouble(target, fieldOffset);
        }else if(Float.TYPE.equals(type)){
            return UNSAFE.getFloat(target, fieldOffset);
        }else {
            return UNSAFE.getObject(target, fieldOffset);
        }

    }
}
