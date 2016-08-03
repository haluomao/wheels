package com.atongmu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wangjun04 on 2015/11/25.
 */
public class SerializeHelper {

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception ex) {
            LogHelper.error("serialize 发生异常，ex:{0}", ex);
        }

        return null;
    }

    /**
     * 反序列化
     *
     * @param value
     * @param encoding 编码格式
     * @return
     */
    public static Object unserialize(byte[] bytes) {

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream( bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();

        } catch (Exception ex) {
            LogHelper.error("unserialize发生异常，ex:{0}", ex);
        }
        return null;
    }
}
