package com.atongmu.util;


import com.atongmu.util.log.ILog;

import java.text.MessageFormat;

/**
 * 日志帮助类
 * @author anonymous
 * @version 1.0
 */
public class LogHelper {

    /**
     * Info日志记录
     *
     * @param message
     * @return
     */
    public static int info(String message) {
        return ILog.getInstance().info(message);
    }

    /**
     * @param message
     * @param throwable
     * @return
     */
    public static int info(String message, Throwable throwable) {
        return ILog.getInstance().info(message, throwable);
    }

    /**
     * @param message
     * @param arguments
     * @return
     */
    public static int info(String message, Object... arguments) {
        message = MessageFormat.format(message, arguments);
        return info(message);
    }


    /**
     * Error 日志记录
     *
     * @param message
     * @return
     */
    public static int error(String message) {
        return ILog.getInstance().error(message);
    }

    public static int error(String message, Throwable throwable) {
        return ILog.getInstance().error(message, throwable);
    }

    /**
     * @param message
     * @param arguments
     * @return
     */
    public static int error(String message, Object... arguments) {
        message = MessageFormat.format(message, arguments);
        return error(message);
    }

    /**
     * Debug 日志记录
     *
     * @param message
     * @return
     */
    public static int debug(String message) {
        return ILog.getInstance().debug(message);
    }

    /**
     * @param message
     * @param throwable
     * @return
     */
    public static int debug(String message, Throwable throwable) {
        return ILog.getInstance().debug(message, throwable);
    }

    /**
     * 格式化参数
     *
     * @param message
     * @param arguments
     * @return
     */
    public static int debug(String message, Object... arguments) {
        message = MessageFormat.format(message, arguments);
        return debug(message);
    }

    /**
     * Warn 日志记录
     *
     * @param message
     * @return
     */
    public static int warn(String message) {
        return ILog.getInstance().warn(message);
    }

    /**
     * @param message
     * @param throwable
     * @return
     */
    public static int warn(String message, Throwable throwable) {
        return ILog.getInstance().warn(message, throwable);
    }

    /**
     * @param message
     * @param arguments
     * @return
     */
    public static int warn(String message, Object... arguments) {
        message = MessageFormat.format(message, arguments);
        return warn(message);
    }
}
