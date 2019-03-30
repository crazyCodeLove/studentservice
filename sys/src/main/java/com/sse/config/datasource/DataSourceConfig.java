package com.sse.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-03-30 13:00
 */

@Configuration
public class DataSourceConfig {

    public static final String DEFAULT_DATASOURCE_NAME = "studentDataSource";

    // student_service 数据源
    @Bean(name = "studentDS")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.student") // application.properteis中对应属性的前缀
    public DataSource studentDataSource() {
        return DataSourceBuilder.create().build();
    }

    // log1 数据源
    @Bean(name = "log1DS")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.log1") // application.properteis中对应属性的前缀
    public DataSource log1DataSource() {
        return DataSourceBuilder.create().build();
    }

    // log2 数据源
    @Bean(name = "log2DS")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.log2") // application.properteis中对应属性的前缀
    public DataSource log2DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(studentDataSource());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("studentDataSource", studentDataSource());
        dsMap.put("log1DataSource", log1DataSource());
        dsMap.put("log2DataSource", log2DataSource());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
