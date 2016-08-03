package com.atongmu.util.log;
/**
 * ILog 日志操作抽象类
 * date: 2016/8/1
 *
 * @author anonymous
 * @version 1.0
 */
public abstract class ILog {
    private static ILog instance = null;

    public static ILog getInstance(){
        if(instance == null)
            instance = new Slf4j();

        return instance;
    }

    /**
     * Info日志记录
     * @param message
     * @return
     */
    public abstract int info(String message);

    /**
     * Info日志记录
     * @param message
     * @param throwable
     * @return
     */
    public abstract int info(String message,Throwable throwable);

    /**
     * Error 日志记录
     * @param message
     * @return
     */
    public abstract int error(String message);
    /**
     * Error 日志记录
     * @param message
     * @param throwable
     * @return
     */
    public abstract int error(String message,Throwable throwable);

    /**
     * Debug 日志记录
     * @param message
     * @return
     */
    public abstract int debug(String message);
    /**
     * Debug 日志记录
     * @param message
     * @param throwable
     * @return
     */
    public abstract int debug(String message,Throwable throwable);

    /**
     * Warn 日志记录
     * @param message
     * @return
     */
    public abstract int warn(String message);

    /**
     * Warn 日志记录
     * @param message
     * @param throwable
     * @return
     */
    public abstract int warn(String message,Throwable throwable);
}
