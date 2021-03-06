package com.sse.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-03-30 13:00
 */

@Configuration
public class DataSourceConfig {

    public static final String STUDENT_DATASOURCE_NAME = "studentDataSource";
    public static final String LOG1_DATASOURCE_NAME = "log1DataSource";
    public static final String LOG2_DATASOURCE_NAME = "log2DataSource";

    public static final String DEFAULT_DATASOURCE_NAME = STUDENT_DATASOURCE_NAME;

    // student_service 数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.student") // application.properteis中对应属性的前缀
    public DataSource studentDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    // log1 数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.log1") // application.properteis中对应属性的前缀
    public DataSource log1DataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    // log2 数据源
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.log2") // application.properteis中对应属性的前缀
    public DataSource log2DataSource() {
        return DruidDataSourceBuilder.create().build();
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
        dsMap.put(STUDENT_DATASOURCE_NAME, studentDataSource());
        dsMap.put(LOG1_DATASOURCE_NAME, log1DataSource());
        dsMap.put(LOG2_DATASOURCE_NAME, log2DataSource());
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
