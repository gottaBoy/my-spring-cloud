package com.mysting.tomato.datasource.util;

import com.mysting.tomato.datasource.constant.DataSourceKey;

/**
 * 用于数据源切换
 */
public class DataSourceHolder {

	//注意使用ThreadLocal，微服务下游建议使用信号量
    private static final ThreadLocal<DataSourceKey> DATA_SOURCE_KEY = new ThreadLocal<>();

    //得到当前的数据库连接
    public static DataSourceKey getDataSourceKey() {
        return DATA_SOURCE_KEY.get();
    }
    //设置当前的数据库连接
    public static void setDataSourceKey(DataSourceKey type) {
    	DATA_SOURCE_KEY.set(type);
    }
    //清除当前的数据库连接
    public static void clearDataSourceKey() {
    	DATA_SOURCE_KEY.remove();
    }


}