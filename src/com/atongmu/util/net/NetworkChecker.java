package com.atongmu.util.net;

import com.atongmu.util.LogHelper;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 检查网络工具类
 * date: 2016/8/1
 *
 * @author maofagui
 * @version 1.0
 */
public class NetworkChecker {

    /**
     * 检查端口
     *
     * @param port 端口值
     * @return 结果
     */
    public static boolean checkPort(int port) {
        if (port <= 0 || port > 65535) {
            LogHelper.error("端口号不符合规则:" + port);
            return false;
        }
        return true;
    }

    /**
     * 检查IP地址，对ipv4地址合法性进行检测
     *
     * @param ipAddress ip值
     * @return 结果
     */
    public static boolean checkIp(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress))
            return false;
        if ("localhost".equals(ipAddress))
            return true;
        String[] arr = ipAddress.split("\\.");
        if (null == arr || arr.length != 4)
            return false;
        int tmp = 0;
        for (int i = 0; i < arr.length; i++) {
            tmp = Integer.parseInt(arr[i]);
            if (tmp < 0 || tmp > 255)
                return false;
        }
        return true;
    }

    /**
     * 检测连接
     *
     * @param ipAddress ip
     * @param port      端口
     * @return true/false
     */
    public static boolean checkConnect(String ipAddress, int port, boolean isShowException) {

        // 对ipv4地址合法性进行检测
        if (!checkIp(ipAddress)) {
            return false;
        }

        // 对端口合法性进行检测
        if (!checkPort(port)) {
            return false;
        }

        Socket socket = null;
        try {
            socket = new Socket();
            // 设置连接超时
            socket.setSoTimeout(1000);
            socket.connect(new InetSocketAddress(ipAddress, port), 1000);
            return true;
        } catch (Exception e) {
            try {
                // 设置连接超时
                socket.connect(new InetSocketAddress(ipAddress, port), 100);
                return true;
            } catch (Exception e1) {
                try {
                    // 设置连接超时
                    socket.connect(new InetSocketAddress(ipAddress, port), 100);
                    return true;
                } catch (Exception e2) {
                    try {
                        // 对80端口的ip地址进行ping验证
                        if (port == 80) {
                            Pinger p = new Pinger(ipAddress, 1000, 10);
                            boolean reachable = p.isReachable();
                            if (reachable) {
                                return true;
                            }
                        }
                        if (isShowException) {
                            LogHelper.info(ipAddress + " " + port + "：异常信息：" + e2.toString());
                        }
                        return false;
                    } catch (Exception e3) {
                        if (isShowException) {
                            LogHelper.info(ipAddress + " " + port + "：异常信息：" + e3.toString());
                        }
                        return false;
                    }
                }
            }
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (Exception e) {
                    LogHelper.error("关闭资源失败", e);
                }
            }

        }
    }
}
