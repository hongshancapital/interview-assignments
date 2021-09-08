package com.moonciki.interview.utils;

import com.moonciki.interview.commons.exception.CustomException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 三重加密 3DES也作 Triple DES,
 *
 * @author admin
 */
public class TripleDESCBC {
    // 算法名称
    public static final String KEY_ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";

    public final static TripleDESCBC instance = new TripleDESCBC();

    private KeyGenerator keyGen;

    private Cipher cipher;

    private TripleDESCBC() {
        //Security.addProvider(new BouncyCastleProvider());
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (Exception e) {
            throw CustomException.createException(e);
        }
    }

    /**
     * 生成key
     *
     * @return
     */
    public byte[] generateKey() {
        byte[] keyBytes = keyGen.generateKey().getEncoded();

        return keyBytes;
    }

    /**
     * 生成 iv
     *
     * @return
     */
    public byte[] getIVKey() {
        byte[] keyBytes = keyGen.generateKey().getEncoded();

        byte[] ivBytes = Arrays.copyOfRange(keyBytes, 0, 8);

        return ivBytes;
    }

    /**
     * MD5加密大写
     *
     * @param text
     * @return
     */
    public String md5HxUpper(String text) {
        String md5Str = DigestUtils.md5Hex(text).toUpperCase();

        return md5Str;
    }

    /**
     * 获取对应秘钥
     * 将字符串 md5 加密
     * @param keyStr
     * @return
     * @throws Exception
     */
    public SecretKey getSecretKey(String keyStr) throws Exception {

        String md5Str = md5HxUpper(keyStr);

        byte[] keyBytes = md5Str.getBytes();

        DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
        SecretKey secretKey = SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(spec);

        return secretKey;
    }

    /**
     * 获取 IV
     * 字符串取 md5，转 byte 数组，然后取前8个字节
     * @param ivStr
     * @return
     */
    public IvParameterSpec getIv(String ivStr) {

        if(ivStr == null){
            ivStr = "";
        }

        String md5Str = md5HxUpper(ivStr);

        String ivSub = md5Str.substring(0, 8);

        byte[] ivBytes = ivSub.getBytes();

        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        return iv;
    }

    /**
     * 加密
     * @param keyStr
     * @param ivStr
     * @param dataByte
     * @return
     * @throws Exception
     */
    public byte[] encrypt(String keyStr, String ivStr, byte[] dataByte) throws Exception {
        SecretKey secretKey = getSecretKey(keyStr);

        IvParameterSpec iv = getIv(ivStr);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptData = cipher.doFinal(dataByte);
        return encryptData;
    }

    /**
     * 解密
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
    public byte[] decrypt(String keyStr, String ivStr, byte[] encrypt) throws Exception {
        SecretKey secretKey = getSecretKey(keyStr);

        IvParameterSpec iv = getIv(ivStr);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] encryptData = cipher.doFinal(encrypt);
        return encryptData;
    }

    /**
     * 加密 为 base64字符串
     * @param keyStr
     * @param dataStr
     * @return
     * @throws Exception
     */
    public String encrypt2String(String keyStr, String ivStr, String dataStr) throws Exception {
        byte[] encryptData = encrypt(keyStr, ivStr, dataStr.getBytes());
        String result = CommonUtil.encodeBase64(encryptData);

        return result;
    }

    /**
     * 依据 base64 格式解密为字符串
     * @param keyStr
     * @param dataStr
     * @return
     * @throws Exception
     */
    public String decrypt2String(String keyStr, String ivStr, String dataStr) throws Exception {
        byte[] dataByte = CommonUtil.decodeBase64(dataStr);

        byte[] decByte = decrypt(keyStr, ivStr, dataByte);

        String result = new String(decByte);

        return result;
    }

    public static void main(String[] args) throws Exception {
        String keyStr = "testkey";

        String iv = "testiv";

        String dataStr = "hello world";

        TripleDESCBC tripleDES = new TripleDESCBC();

        String enStr2 = tripleDES.encrypt2String(keyStr, iv, dataStr);

        System.out.println("加密后：" + enStr2);

        String decStr2 = tripleDES.decrypt2String(keyStr, iv, enStr2);

        System.out.println("解密后：" + decStr2);

        String md5Str = DigestUtils.md5Hex("a");

        System.out.println(md5Str);

    }
}
