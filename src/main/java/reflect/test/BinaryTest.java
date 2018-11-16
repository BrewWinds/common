package reflect.test;

/**
 * @Auther: 01378178
 * @Date: 2018/11/16 09:35
 * @Description:
 */
public class BinaryTest {


    public static void main(String[] args) {
        String s = "1";
        byte[] bs = s.getBytes();
        for(int i=0; i<bs.length; i++){
            byte b = bs[i];
            String binary = Integer.toBinaryString(0x100 | (b & 0xff));
            System.out.println(binary.substring(1, binary.length()));
        }
    }

    /**
     * 转二进制, 8 位的转. 获取字节数组, 然后每个字节数组转成Integer
     */
}
