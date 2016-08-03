package com.atongmu.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j 操作类
 * date: 2016/8/1
 *
 * @author anonymous
 * @version 1.0
 */
public class Slf4j extends ILog {
    private static Logger logger = null;

    public Slf4j() {
        super();
        if (logger == null)
            logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Info日志记录
     *
     * @param message
     * @return
     */
    public int info(String message) {
        try {
            logger.info(message);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    public int info(String message, Throwable throwable) {
        try {
            logger.info(message, throwable);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * Error 日志记录
     *
     * @param message
     * @return
     */
    public int error(String message) {
        try {
            logger.error(message);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    public int error(String message, Throwable throwable) {
        try {
            logger.error(message, throwable);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * Debug 日志记录
     *
     * @param message
     * @return
     */
    public int debug(String message) {
        try {
            logger.debug(message);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    public int debug(String message, Throwable throwable) {
        try {
            logger.debug(message, throwable);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * Warn 日志记录
     *
     * @param message
     * @return
     */
    public int warn(String message) {
        try {
            logger.warn(message);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    public int warn(String message, Throwable throwable) {
        try {
            logger.warn(message, throwable);
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }
}