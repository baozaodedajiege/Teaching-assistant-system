package com.ctgu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
 
@Configuration
public class DruidDataSourceConfiguration {
	@Bean  
	@Primary
    @ConfigurationProperties(prefix = "spring.datasource") 
    public DataSource druidDataSource() {  
        DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;  
    }  
}