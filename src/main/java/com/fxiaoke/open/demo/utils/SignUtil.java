package com.fxiaoke.open.demo.utils;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class SignUtil {
    public static String decryptAes(String encryptedContent, String aesKey) throws Exception {
        byte[] ciphertextBytes = Base64.decodeBase64(encryptedContent); // decode加密密文结果
        byte[] aesKeyBytes = Base64.decodeBase64(aesKey); // decode 秘钥
        SecretKeySpec keySpec = new SecretKeySpec(aesKeyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(aesKeyBytes, 0, 16); // 初始化向量
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 加密模式为CBC
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);
        String result = new String(plaintextBytes, "utf-8"); // 解密结果
        return result;
    }

    public static String encryptAes(String result, String aesKey) throws Exception {
        byte[] plaintextBytes = result.getBytes();
        byte[] aesKeyBytes = Base64.decodeBase64(aesKey);
        SecretKeySpec keySpec = new SecretKeySpec(aesKeyBytes, "AES");
        IvParameterSpec iv = new IvParameterSpec(aesKeyBytes, 0, 16);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = cipher.doFinal(plaintextBytes);
        String encryptedResult = Base64.encodeBase64String(encryptedBytes); //转换为64位编码
        return encryptedResult;
    }

    public static String shaEncode(String text) throws Exception {
        MessageDigest shaDigest = MessageDigest.getInstance("SHA");
        byte[] byteArray = text.getBytes("UTF-8");
        byte[] md5Bytes = shaDigest.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
