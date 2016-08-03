package com.atongmu.util.net;

import com.atongmu.util.LogHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TODO unchecked
 * @author maofagui
 * @version 1.0
 */
public class Pinger {
    /**
     * 要ping的主机
     */
    private String remoteIpAddress;
    /**
     * 设置ping的次数
     */
    private final int pingTimes;
    /**
     * 设置超时
     */
    private int timeOut;

    /**
     * 构造函数
     *
     * @param remoteIpAddress
     * @param pingTimes
     * @param timeOut
     */
    public Pinger(String remoteIpAddress, int pingTimes, int timeOut) {
        super();
        this.remoteIpAddress = remoteIpAddress;
        this.pingTimes = pingTimes;
        this.timeOut = timeOut;
    }

    /**
     * 测试是否能ping通
     */
    public boolean isReachable() {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是linux格式的命令
        // window命令是 "ping " + remoteIpAddress + " -n " + pingTimes + " -w " + timeOut
        String pingCommand = "ping " + remoteIpAddress + " -c " + pingTimes + " -w " + timeOut;

        try {
            // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            LogHelper.info(remoteIpAddress + " " + 80 + "：异常信息：" + ex.toString());
            // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 若line含有 TTL=16字样,说明已经ping通,返回1,否則返回0.
     *
     * @param line
     * @return
     */
    private static int getCheckResult(String line) {
        if (line.contains("ttl") || line.contains("TTL")) {
            return 1;
        }
        return 0;
    }

}
