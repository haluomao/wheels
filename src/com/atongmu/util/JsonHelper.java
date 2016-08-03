package com.atongmu.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * json 工具类
 * date: 2016/7/31
 *
 * jars：
 *  jackson-core
 *  jackson-databind
 *  jackson-annotation
 *
 * @author maofagui
 * @version 1.0
 */
public class JsonHelper {

    /**
     * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
     * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
     * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
     * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
     * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
     * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
     */
    public static String write(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static <T> T read(String json, Class<T> obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, obj);
    }

    //json 注解使用实例
    class Example implements Serializable{
        private Integer serializabled;

        //不JSON序列化
        @JsonIgnore
        private Integer unserializabled;

        //格式化序列
        @JsonFormat(pattern = "yyyy年MM月dd日")
        private Date serialDate;

        //序列化serialRename属性为serialName
        @JsonProperty("serilName")
        private String serialRename;
    }


    //使用
    public static void main(String[] args) {
        Map map = new HashMap<Integer, String>();
        map.put(1, "hello");
        map.put(2, "world");

        try {
            String res = JsonHelper.write(map);
            System.out.println(res);

            map = JsonHelper.read(res, Map.class);
            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
