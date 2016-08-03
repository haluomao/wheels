package com.atongmu.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Clob;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 工具类方法操作集
 * @author anonymous
 * @version
 */
public class SystemUtility {
    /**
     * 获取用户真实IP
     *
     * @return
     */
    public static String getUserIP() {
        return null;
    }


    /**
     * 获取服务器IP
     * @return
     */
    public static String getServerIp(){
        return "127.0.0.1";
    }

    /**
     * 读取xml文件
     *
     * @param path
     * @return
     */
    public static Configuration loadXml(String path) {
        try {
            Configuration configuration = new XMLConfiguration(path);
            return configuration;
        } catch (Exception ex) {
            LogHelper.error("loadxml error [path:{0},ex:{1}]", path, ex);
        }
        return null;
    }

    /**
     * 读取Properties配置
     *
     * @param path
     * @return
     */
    public static Configuration loadProperties(String path) {
        try {
            Configuration configuration = new PropertiesConfiguration(path);
            return configuration;
        } catch (Exception ex) {
            LogHelper.error("loadProperties error [path:{0},ex:{1}]", path, ex);
        }
        return null;
    }


    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String getRequest(String url, String params) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            url += "?" + params;

            LogHelper.info("URL:"+url);

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            throw new IOException("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送GET请求
     * @param url
     * @param params
     * @param encoding 编码格式
     * @return
     */
    public static String getRequest(String url,String params, String encoding){
        String result = "";
        BufferedReader in = null;
        try {
            url += "?" + params;

            LogHelper.info("URL:"+url);

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            String line;
            while ((line = in.readLine()) != null) {
                result += line+"\n";
            }
        } catch (Exception e) {
            LogHelper.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String postRequest(String url, String params) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            conn.setDoOutput(true);// 发送POST请求必须设置如下两行
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流s
            out.print(params);// 发送请求参数
            out.flush();// flush输出流的缓冲
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception ex) {
            throw new IOException("发送POST请求出现异常！url:" + url, ex);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                LogHelper.error("关闭链接出错，{0}", ex);
            }
        }
        return result;
    }

    /**
     * 格式化参数
     *
     * @param format
     * @param arguments
     * @return
     */
    public static String format(String format, Object... arguments) {
        return MessageFormat.format(format, arguments);
    }

    /**
     * 去除String 末尾字符
     *
     * @param str
     * @param trimStr
     * @return
     */
    public static String trimEnd(String str, String trimStr) {
        if (str.trim().length() >= trimStr.length()) {
            if (str.substring(str.length() - trimStr.length(), str.length()).equals(trimStr))
                return str.substring(0, str.length() - trimStr.length());
        }

        return str;
    }

    /**
     * 发送windows MQ队列
     *
     * @param mqPath
     * @param data
     * @param title
     * @return
     */
    public static int sendWinMQ(String mqPath,String data,String title){
        try {
            //SendWinMQPath ： 测试地址：phpservicentest.qidian.com/SendMQService.aspx  HOSTS:202.102.67.227 phpservicentest.qidian.com
            postRequest(Config.getConfig("SendWinMQPath", "http://phpservicentest.qidian.com/SendMQService.aspx"),
                    format("path={0}&data={1}&title={2}", mqPath, data, title));
        } catch (Exception ex) {
            LogHelper.error("发送Win MQ失败,path:{0},data:{1},title:{2},ex:{3}", mqPath, data, title, ex);
        }
        return 0;
    }



}
