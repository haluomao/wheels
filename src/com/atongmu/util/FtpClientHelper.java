package com.atongmu.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ftp相关
 * @ClassName: FtpClientService 
 * @author: zhaojinbao
 * @date: 2015年12月10日 下午5:10:31
 */
public class FtpClientHelper {
    
    private static FTPClient ftpClient = new FTPClient();
    // 创建Lock锁
    private static Lock lock = new ReentrantLock();
    // 在Lock锁对象上关联Condition对象
    private static Condition job1_con = lock.newCondition();// 创建一个属于job1的等待唤醒机制
    private static Condition job2_con = lock.newCondition();// 创建一个属于job2的等待唤醒机制

    /**
     * 登录到ftp
     * @param hostname ftp地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @return 登录状态
     */
    public static boolean login(String hostname, int port, String username, String password) {
        try {
            lock.lock();
            // 连接
            ftpClient.connect(hostname, port);
            ftpClient.login(username, password);
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LogHelper.error("FTP服务端拒绝连接");
                return false;
            }
        } catch (Exception ex) {
            LogHelper.error("ftp连接异常：{0}",ex);
            return false;
        }finally{
            lock.unlock();
        }

        return true;
    }

    /**
     * 关闭资源
     */
    public void closeConn() {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (Exception e) {
                    LogHelper.error("关闭资源异常：{0}",e);
                }
            }
        }
    }

    /**
     * 上传文件到ftp
     * @param curPath 本地文件地址
     * @param ftpFileName 上传后的文件名
     * @return 上传状态
     */
    public static boolean storeFile(String curPath, String ftpFileName) {

        boolean flag = false;
        if (ftpClient != null) {
            // 获取锁
            lock.lock();
            // 本地文件
            File srcFile = new File(curPath);
            FileInputStream fis = null;
            try {
                // 设置上传目录
                fis = new FileInputStream(srcFile);
                ftpClient.changeWorkingDirectory(Config.getConfig("ftpFilePath"));
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("GBK");

                // 设置文件类型（二进制）
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                // 上传,解决文件名乱码问题
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes("GBK"), "iso-8859-1"), fis);
                fis.close();
                return flag;
            } catch (Exception e) {
                LogHelper.error("上传文件到ftp异常：{0}",e);
                return false;
            } finally {
                // 唤醒job2的线程
                job2_con.signal();
                // 释放锁
                lock.unlock();
            }
        }
        return false;
    }

    /**
     * 检测ftp最近一段时间是有没有文件生成
     * @param ftpFilePath 服务器文件所在目录
     * @return 状态
     */
    public static boolean checkFileExist(String ftpFilePath) {

        if (ftpClient != null) {
            try {
                // 获取锁
                lock.lock();
                // ftp文件路径
                ftpClient.changeWorkingDirectory(ftpFilePath);
                // 二进制
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setControlEncoding("GBK");
                // 获取 ftp文件下的所有文件
                FTPFile[] listFiles = ftpClient.listFiles();
                if (listFiles.length != 0) {
                    for (FTPFile ftpFile : listFiles) {
                        // 这里的.和.. 因为它会默认生成这两个
                        if (!ftpFile.getName().equals(".") && !ftpFile.getName().equals("..")) {
                            // 获取文件的最后一次修改时间
                            long timeInMillis = ftpFile.getTimestamp().getTimeInMillis();
                            // 本地时间
                            long currentTimeMillis = System.currentTimeMillis();
                            // 本地时间-最后一次修改时间
                            long intervalTime = currentTimeMillis - timeInMillis;
                            // 如果时间差小于指定的时间，则表示文件存在
                            if ((intervalTime / 1000 / 60) < Long.parseLong(Config.getConfig("subTime"))) {
                                return true;
                            }
                        }
                    }
                } else {
                    return false;
                }

            } catch (Exception e) {
                LogHelper.error("检测ftp文件生成异常：{0}",e);
                return false;
            } finally {
                // 唤醒job1的线程
                job1_con.signal();
                // 释放锁
                lock.unlock();
            }
        }

        return false;
    }

    /**
     * 检测最近一段时间内生成的ftp文件大小是否为0
     * @param ftpFilePath 服务器文件所在目录
     * @return 状态
     */
    public static boolean checkFileCount(String ftpFilePath) {
        if (ftpClient != null) {
            try {
                // 获取锁
                lock.lock();
                // 改变ftp路径
                ftpClient.changeWorkingDirectory(ftpFilePath);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 开通端口来传输数据
                ftpClient.enterLocalPassiveMode();
                // 设置编码
                ftpClient.setControlEncoding("GBK");
                // 获取ftp路径下的所有的文件
                FTPFile[] listFiles = ftpClient.listFiles();
                if (listFiles.length != 0) {
                    for (FTPFile ftpFile : listFiles) {
                        // 这里的.和.. 因为它会默认生成这两个
                        if (!ftpFile.getName().equals(".") && !ftpFile.getName().equals("..")) {
                            // 获取文件最后一次修改时间
                            long timeInMillis = ftpFile.getTimestamp().getTimeInMillis();
                            // 获取本地时间
                            long currentTimeMillis = System.currentTimeMillis();
                            // 时间差
                            long intervalTime = currentTimeMillis - timeInMillis;
                            // 如果时间差小于指定的时间，则表示有文件生成
                            if ((intervalTime / 1000 / 60) < Long.parseLong(Config.getConfig("subTime"))) {
                                // 判断生成的文件大小是否为0
                                if (ftpFile.getSize() == 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                LogHelper.error("检测ftp文件大小异常：{0}",e);
                return false;
            } finally {
                // 唤醒job1的线程
                job1_con.signal();
                // 释放锁
                lock.unlock();
            }
        }
        return false;
    }

}
