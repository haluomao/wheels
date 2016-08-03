package com.atongmu.util;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 采用MD5加密解密
 *
 * @author maofagui
 */
public class MD5Util {
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String src) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        byte[] byteArray = src.getBytes();
        messageDigest.update(byteArray);
        // 转换并返回结果，也是字节数组，包含16个元素
        byte[] resultByteArray = messageDigest.digest();
        // 字符数组转换成字符串返回
        return byteArrayToHex(resultByteArray);
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 得到文件的MD5
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String file2MD5(String filePath) throws Exception {
        // 缓冲区大小
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        String res = null;
        try {
            // 拿到一个MD5转换器
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(filePath);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            res = byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return res;
        } finally {
            digestInputStream.close();
            fileInputStream.close();
            return res;
        }
    }

}
