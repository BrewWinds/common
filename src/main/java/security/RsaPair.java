package security;

import com.alibaba.druid.util.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * @Auther: 01378178
 * @Date: 2019/7/18 17:14
 * @Description:
 */
public class RsaPair {

    // druid
    private static final String DRUID_PRIVATE_KEY_STRING = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAocbCrurZGbC5GArEHKlAfDSZi7gFBnd4yxOt0rwTqKBFzGyhtQLu5PRKjEiOXVa95aeIIBJ6OhC2f8FjqFUpawIDAQABAkAPejKaBYHrwUqUEEOe8lpnB6lBAsQIUFnQI/vXU4MV+MhIzW0BLVZCiarIQqUXeOhThVWXKFt8GxCykrrUsQ6BAiEA4vMVxEHBovz1di3aozzFvSMdsjTcYRRo82hS5Ru2/OECIQC2fAPoXixVTVY7bNMeuxCP4954ZkXp7fEPDINCjcQDywIgcc8XLkkPcs3Jxk7uYofaXaPbg39wuJpEmzPIxi3k0OECIGubmdpOnin3HuCP/bbjbJLNNoUdGiEmFL5hDI4UdwAdAiEAtcAwbm08bKN7pwwvyqaCBC//VnEWaq39DCzxr+Z2EIk=";
    public static final String DRUID_PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKHGwq7q2RmwuRgKxBypQHw0mYu4BQZ3eMsTrdK8E6igRcxsobUC7uT0SoxIjl1WveWniCASejoQtn/BY6hVKWsCAwEAAQ==";


    public static final String COMPLEX_PRIVATE_KEY_STRING =
            "MIIEowIBAAKCAQEAot1PBCpaPhwh7FVn+lfRB/nxDWf0oDUIEVEdjq1Zclhpxpnc"
                    + "c5RgmSeWtcSH5hgCL/eu4GEZ6W//dOyXYRPEVZsr3P4rViUpyfzMMtcz7zEz/W9Z"
                    + "Bt5XUc5u2HGEBWaj2opcyt7f4u4Ftzu4xcyGBrBpMuEMnOpMS1o+96FduVCRVxG2"
                    + "ud7WzOhIaIxBPw1ZwCPBBNcrvH2Dofm1FOfr7kWD+tUn9w1szzM7EPAW+abGsxbT"
                    + "YuOquyxj+eUPZbHLwDm/WeW2GiPugK3OLpFS0sYN5uUOCe1QAQ0itTtwLwZ7mHvC"
                    + "CL3hs2EdUccOg5ERsSmumChOZaa/w2HZNYmdywIDAQABAoIBAEEn/tOwyfetM5kk"
                    + "Nfv+o/7n7Jsrar+pV7ft1sBc87r+ShsNNRf8VYUQRIglvyS7mCxuj8Tus/ojN3Uk"
                    + "rg9FZDfHY43/FYFaTtSjUWJJnFpmOeF7aYeI7jApQUlQaLvsa0MkNaln7vQPu7Op"
                    + "retcPoZwjzWuuWgRiEJhMW3KXf+j56HjC5OKs79HekTLNvTpQL3qwKimqmNM4Is4"
                    + "BMqfaP+WXGFmrm4ptcoWojY9Pw2MCr5RsOrQpKX4pkfZouhABIaUss5HwTMhzYGA"
                    + "rzvpGjUpaXBRtXCJaGMP/i3JTRymm6vseUKLhTtW5/+5jRlEU3RBzkwjF+MZiODc"
                    + "EeFedvkCgYEA/imXKphBckM2sgtYeekLVkw5vLoxN47hh0uwm3N95OHInYrMUmag"
                    + "k0VyCMDQvEgRXlfuRZNekai73i0RQkR8ZIwymJapuh8H+7AXjfPGZ80ybXy1Zfak"
                    + "6upztIwYw3H1wY4uwR4gAq5KLHjDon1KaXPrN4t3jJYiBYvBt1QeGKcCgYEApAq9"
                    + "6fJu0OhfRbElzJZ1GCaPQW3kj0S6SN2bnWXkMoIKuKWvxPyPJB3oe8wjo0VXCG6Q"
                    + "mIvFvYKEPkjKcKglh3oU8a0sEv7bxhpU9lIgB2ckMetSfJ0fFaJ+Mkfgl/YeXR8l"
                    + "ND7Do+GYsKxRjfS04nwrfQQQqvEQKoIYaqCOEj0CgYEAp1idFsefN+u3hK1huQtP"
                    + "kqen0HLL2yfxqNKWWxRFDUH4hHqSI6M9ERrFhavnBEFzZioBUcBBoSh3YkdMONS9"
                    + "DrscyUlSQaES8Y41RBsBRPFfxaU6DwPB/IcYXgVsfKt5Q9MpR7BdJUa6weNV9JQI"
                    + "Pby8YBstQNzNU9d8WghBtLsCgYBLT8MxDORjdMuIq3O3IbxQgiNy3oj4XY1ItrUi"
                    + "5gyGQ724WBkCTxFIe5Kog2g5JTR34orv/fuirbEZB0Ipxoi0UhAbhG2fqvIrWRt4"
                    + "muupemjbojYUj+4deKSHYQhzu8Lk7c+e1NHtAz2env4yNg51jJxKoPsl/9Z0LZYY"
                    + "He4b4QKBgDAlbolW+PX2dnYIzmMrB0EKPVxMXcfmx7s9vXf3OmSVI0G9EKiqZJ+t"
                    + "QIxl/Ih6trdI9GmL/UGP87qOOrhj+J2IDxPRLlUiRiF3WMoL3rR3GeAjHOpmBWdY"
                    + "Se8tXFWMFmRqAtiWU3qtuLiWFC6DT/Wm26w/yHhuKKO8l8hasjgV";

    public static final String COMPLEX_PUBLIC_KEY_STRING =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAot1PBCpaPhwh7FVn+lfR"
                    + "B/nxDWf0oDUIEVEdjq1Zclhpxpncc5RgmSeWtcSH5hgCL/eu4GEZ6W//dOyXYRPE"
                    + "VZsr3P4rViUpyfzMMtcz7zEz/W9ZBt5XUc5u2HGEBWaj2opcyt7f4u4Ftzu4xcyG"
                    + "BrBpMuEMnOpMS1o+96FduVCRVxG2ud7WzOhIaIxBPw1ZwCPBBNcrvH2Dofm1FOfr"
                    + "7kWD+tUn9w1szzM7EPAW+abGsxbTYuOquyxj+eUPZbHLwDm/WeW2GiPugK3OLpFS"
                    + "0sYN5uUOCe1QAQ0itTtwLwZ7mHvCCL3hs2EdUccOg5ERsSmumChOZaa/w2HZNYmd"
                    + "ywIDAQAB";


    /**
     * RSA使用X509EncodedKeySpec、PKCS8EncodedKeySpec生成公钥和私钥
     * @param keyBytes
     * @param plainText
     * @return
     */

    private static final String RSA = "RSA";


    public static String enctypt(String plainText) throws Exception {
//        return encrypt(null, plainText);
        return null;
    }

    public static String encrypt(String key, String plainText) throws Exception{
        if(key == null){
           key = DRUID_PRIVATE_KEY_STRING;
        }
        return null;
    }

    private static String encrypt(byte[] keyBytes, String plainText) throws Exception {

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = factory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance(RSA);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        }catch(InvalidKeyException e){
            // 因为 IBM JDK 不支持私钥加密, 公钥解密, 所以要反转公私钥
            // 也就是说对于解密, 可以通过公钥的参数伪造一个私钥对象欺骗 IBM JDK
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance(RSA).generatePublic(publicKeySpec);

            cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, fakePublicKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedString = Base64.byteArrayToBase64(encryptedBytes);

        return encryptedString;
    }

    public static String decrypt(PublicKey publicKey, String cipherText){
        return null;
    }

}
