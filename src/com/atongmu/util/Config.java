package com.atongmu.util;

import org.apache.commons.configuration.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取config配置项
 *
 * @author anonymous
 * @version 1.0
 */
public class Config {
    private static CompositeConfiguration compositeConfiguration = null;

    private Config() {

    }

    /**
     * 初始化
     *
     * @throws ConfigurationException
     */
    private static CompositeConfiguration getCompositeConfiguration() throws ConfigurationException {
        if (compositeConfiguration == null) {
            compositeConfiguration = new CompositeConfiguration();
            compositeConfiguration.addConfiguration(new SystemConfiguration());
            //个性化配置节永远需要覆盖默认的配置
            String serviceConfigPath = "config.properties";
            compositeConfiguration.addConfiguration(new PropertiesConfiguration(serviceConfigPath));
            String configPath = "config.xml";
            compositeConfiguration.addConfiguration(new XMLConfiguration(configPath));
            String cachePath = "cache.xml";
            compositeConfiguration.addConfiguration(new XMLConfiguration(cachePath));
        }
        return compositeConfiguration;
    }

    /**
     * 获取config配置
     *
     * @param configName 节点名称
     * @return 节点值
     */
    public static String getConfig(String configName) {
        try {
            return getCompositeConfiguration().getString(configName);
        } catch (ConfigurationException ex) {
            LogHelper.error("获取节点{0}出错,ex:{1}", configName, ex);
            return null;
        }
    }

    /**
     * 获取config配置
     *
     * @param configName 节点名称
     * @param defaultValue 默认值
     * @return 节点值
     */
    public static String getConfig(String configName, String defaultValue) {
        String ret = getConfig(configName);
        if (ret == null) {
            return defaultValue;
        }
        return ret;
    }

    /**
     * 获取config列表配置 从1开始，不超过100项
     *
     * @param configName 节点名称
     * @param itemPrefix 子节点的前缀
     * @return 节点值列表
     */
    public static List<String> getConfigList(String configName, String itemPrefix) {
        List<String> ret = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            String tmp = getConfig(configName + "." + itemPrefix + i);
            if (tmp == null) {
                break;
            }
            ret.add(tmp);
        }
        return ret;
    }

    /**
     * 获取config配置（int)
     *
     * @param configName   节点名称
     * @param defaultValue 默认值
     * @return 节点值
     */
    public static int getConfig(String configName, int defaultValue) {
        String ret = getConfig(configName);
        if (ret == null) {
            return defaultValue;
        }
        return Integer.parseInt(ret);
    }


    /**
     * 获取conifg配置(long)
     *
     * @param configName 节点名称
     * @param defaultValue 默认值
     * @return 节点值
     */
    public static long getConfig(String configName, long defaultValue) {
        String ret = getConfig(configName);
        if (ret == null) {
            return defaultValue;
        }
        return Long.parseLong(ret);
    }

    /**
     * 获取config配置(double)
     *
     * @param configName 节点名称
     * @param defaultValue 默认值
     * @return 节点值
     */
    public static double getConfig(String configName, double defaultValue) {
        String ret = getConfig(configName);
        if (ret == null) {
            return defaultValue;
        }
        return Double.parseDouble(ret);
    }


    /**
     * 获取config配置(boolean)
     *
     * @param configName 节点名称
     * @param defaultValue 默认值
     * @return 节点值
     */
    public static boolean getConfig(String configName, boolean defaultValue) {
        String ret = getConfig(configName);
        if (ret == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(ret);
    }
}
