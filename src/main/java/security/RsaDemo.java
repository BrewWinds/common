package security;

import com.google.common.collect.Maps;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * @Auther: 01378178
 * @Date: 2019/7/19 10:15
 * @Description:
 */
public class RsaDemo {

    private static final String RSA = "RSA";
    private static final String PRIVATE_KEY = "privateKey";
    private static final String PUBLICK_KEY = "publicKey";

    public static Map<String, String> genKey() throws NoSuchAlgorithmException {

        Map<String, String> keyMap = Maps.newHashMap();
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA);
        SecureRandom random = new SecureRandom();

        keygen.initialize(1024, random);

        KeyPair kp = keygen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();

        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        keyMap.put(PRIVATE_KEY, privateKeyStr);
        keyMap.put(PUBLICK_KEY, publicKeyStr);

        return keyMap;
    }

    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    public static String encrypt(PublicKey publicKey, String plainText) throws Exception, NoSuchAlgorithmException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {

        }

        byte[] encryptBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedStr = Base64.getEncoder().encodeToString(encryptBytes);

        return encryptedStr;
    }

    public static String decrypt(PrivateKey privateKey, String source) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        try{
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        }catch (Exception e){

        }

        byte[] cipherBytes = Base64.getDecoder().decode(source);
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return new String(plainBytes);
    }


    public static void main(String[] args) throws Exception {

        Map<String, String> keyMap = genKey();
        RSAPublicKey publicKey = getPublicKey(keyMap.get(PUBLICK_KEY));
        RSAPrivateKey privateKey = getPrivateKey(keyMap.get(PRIVATE_KEY));

        String info = "你好呀";

        String cipherStr = encrypt(publicKey, info);
        String recoverStr = decrypt(privateKey, cipherStr);


        System.out.println(String.format("raw=%s, encripted=%s, recver=%s", info, cipherStr, recoverStr));

    }

}
