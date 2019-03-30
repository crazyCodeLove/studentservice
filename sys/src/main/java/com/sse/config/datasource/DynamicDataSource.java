package com.sse.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-03-30 13:03
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    /*
    当前线程的数据源名称
     */
    private static final ThreadLocal<String> dataSourceContextHolder = new ThreadLocal<>();

    /*
    设置当前线程数据源名称
     */
    public static void setDataSourceName(String dataSourceName) {
        dataSourceContextHolder.set(dataSourceName);
    }

    /*
    获取当前线程数据源名称
     */
    public static String getDataSourceName() {
        return dataSourceContextHolder.get();
    }

    /*
    清除当前线程数据源名称
     */
    public static void clearDataSource() {
        dataSourceContextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceName();
    }
}
