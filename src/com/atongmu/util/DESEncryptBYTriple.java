package com.atongmu.util;

import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * 字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的
 * 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * <p/>
 * Created by wangjun04 on 2015/11/17.
 */
public class DESEncryptBYTriple {
    /**
     * @param args在java中调用sun公司提供的3DES加密解密算法时，需要使
     * 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包：
     * jce.jar
     * security/US_export_policy.jar
     * security/local_policy.jar
     * ext/sunjce_provider.jar
     */

    //定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String Algorithm = "DESede";
    private static final String CRYPT_KEY = "1D16EE4825A24ae0822F819AdF6CDE14"; //默认密钥 32位长度
    private static final String CRYPT_IV = "qcDY6X+aPLw="; //默认矢量

    /**
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3EncodeECB(String data) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        return new BASE64Encoder().encode(des3EncodeECB(bytes));
    }


    /**
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3EncodeECB(String data, boolean isUrlSafe) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        String ret = new BASE64Encoder().encode(des3EncodeECB(bytes));
        if (!isUrlSafe)
            return ret;
        else
            return urlSafeBase64Encode(ret);
    }

    /**
     * ECB加密,不要IV
     *
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] data) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(CRYPT_KEY);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = secretKeyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * ECB解密,不要IV
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3DecodeECB(String data) throws Exception {
        byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        return new String(des3DecodeECB(bytes), "UTF-8");
    }

    /**
     * ECB解密,不要IV
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3DecodeECB(String data, boolean isUrlSafe) throws Exception {
        if (!isUrlSafe) {
            byte[] bytes = new BASE64Decoder().decodeBuffer(data);
            return new String(des3DecodeECB(bytes), "UTF-8");
        } else
            return new String(des3DecodeECB(urlSafeBase64Decode(data)), "UTF-8");
    }


    /**
     * ECB解密,不要IV
     *
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeECB(byte[] data) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(CRYPT_KEY);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = secretKeyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * CBC加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3EncodeCBC(String data) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        return new BASE64Encoder().encode(des3EncodeCBC(bytes));
    }

    /**
     * CBC加密
     *
     * @param data
     * @param isUrlSafe
     * @return
     * @throws Exception
     */
    public static String des3EncodeCBC(String data, boolean isUrlSafe) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        String ret = new BASE64Encoder().encode(des3EncodeCBC(bytes));

        if (!isUrlSafe)
            return ret;
        else
            return urlSafeBase64Encode(ret);
    }

    /**
     * CBC加密
     *
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] data) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(CRYPT_KEY);
        byte[] keyiv = new BASE64Decoder().decodeBuffer(CRYPT_IV);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    /**
     * CBC解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3DecodeCBC(String data) throws Exception {
        byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        return new String(des3DecodeCBC(bytes), "UTF-8");
    }

    /**
     * CBC解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String des3DecodeCBC(String data, boolean isUrlSafe) throws Exception {
        byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        String ret = new String(des3DecodeCBC(bytes), "UTF-8");

        if (!isUrlSafe)
            return ret;
        else
            return new String(des3DecodeCBC(urlSafeBase64Decode(data)), "UTF-8");
    }

    /**
     * CBC解密
     *
     * @param data  Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] data) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(CRYPT_KEY);
        byte[] keyiv = new BASE64Decoder().decodeBuffer(CRYPT_IV);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    /**
     * url安全加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static String urlSafeBase64Encode(String data) throws Exception {
        int no_of_eq = StringUtils.countMatches(data, "=");
        data = data.replace("=", "");
        data += no_of_eq;

        data = data.replace("+", "-").replace("/", "_");
        return data;
    }

    /**
     * url 安全解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] urlSafeBase64Decode(String data) throws Exception {
        data = data.replace("-", "+").replace("_", "/");
        int no_of_eq = Integer.parseInt(data.substring(data.length() - 1, data.length()));

        data = data.substring(0, data.length() - 1);
        if (no_of_eq > 0)
            data += StringUtils.repeat("=", no_of_eq);

        return new BASE64Decoder().decodeBuffer(data);
    }
}
