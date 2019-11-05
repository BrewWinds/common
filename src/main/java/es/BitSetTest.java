package es;

import org.apache.kafka.common.utils.Crc32;

import java.util.BitSet;

/**
 * @Date: 2019/8/27 10:43
 * @Description:
 */
public class BitSetTest {

    public static void main(String[] args) {
//        String timeMs = System.currentTimeMillis()+"";
////        int len = ("SKU"+timeMs).getBytes().length * 8;
////
////
////        BitSet bitSet = new BitSet(len);
////        System.out.println(Crc32.crc32(("SKU123456789").getBytes()));
////        System.out.println(bitSet.set(Crc32.crc32(("SKU123456789").getBytes())););
////
////        bitSet.

//        String str = "测试测试测试测试";
//
//        String result = "[";
//        BitSet bitSet = new BitSet();
//        byte[] strBits = str.getBytes();
//        for(int i = 0; i< strBits.length * 8; i++){
//            if((strBits[i / 8] & (128 >> (i % 8))) > 0){
//                bitSet.set(i);
//            }
//        }
//        for(int i = 0; i < bitSet.length(); i++){
//            if(bitSet.get(i))
//                result += "1";
//            else
//                result +="0";
//        }
//        result += "]";
//
//        System.out.println(result);
//
//        System.out.println(bitSet.get);

        int [] array = new int [] {1,2,3,22,0,3};
        BitSet bitSet  = new BitSet(6);
        //将数组内容组bitmap
        for(int i=0;i<array.length;i++)
        {
            bitSet.set(array[i], true);
        }
        System.out.println(bitSet.size());
        System.out.println(bitSet.get(3));


    }
    private static String toBitSet(String str){
        String result = "[";
        BitSet bitSet = new BitSet();
        byte[] strBits = str.getBytes();
        for(int i = 0; i< strBits.length * 8; i++){
            if((strBits[i / 8] & (128 >> (i % 8))) > 0){
                bitSet.set(i);
            }
        }
        for(int i = 0; i < bitSet.length(); i++){
            if(bitSet.get(i))
                result += "1";
            else
                result +="0";
        }
        result += "]";
        return result;
    }



}
