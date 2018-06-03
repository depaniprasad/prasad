package com.hpe.payment.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;

/**
 * ApplicaitonConfig defines the Configuration that is required when the server starts  .
 *
 * @author : 16-August-2017 - DXC Technology - Created by Urukunda Reddy
 * 
 */
	 

@Configuration
@ComponentScan(basePackages = "com.hpe.payment")
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationConfig {
	 
	    /** The env. */
    	@Autowired
	    private Environment env;	 
	    
    	/**
    	 * Data source.
    	 *
    	 * @return the data source
    	 */
    	@Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getRequiredProperty(Constants.JDBC_DRIVER_CLASS_NAME));
	        dataSource.setUrl(env.getRequiredProperty(Constants.JDBC_URL));
	        dataSource.setUsername(env.getRequiredProperty(Constants.JDBC_USER_NAME));
	        dataSource.setPassword(env.getRequiredProperty(Constants.JDBC_PASSWORD));
	        return dataSource;
	    }
	 
	    /**
    	 * Jdbc template.
    	 *
    	 * @param dataSource the data source
    	 * @return the jdbc template
    	 */
    	@Bean
	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        jdbcTemplate.setResultsMapCaseInsensitive(true);
	        return jdbcTemplate;
	    }
	 
	    /**
    	 * Rest template.
    	 *
    	 * @return the rest template
    	 */
    	@Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	    
    	/**
    	 * Property sources placeholder configurer.
    	 *
    	 * @return the property sources placeholder configurer
    	 */
    	@Bean
	    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	           return new PropertySourcesPlaceholderConfigurer();
	        }
	    
    	/**
    	 * Task scheduler.
    	 *
    	 * @return the task scheduler
    	 */
    	@Bean
	    public TaskScheduler taskScheduler() {
	        return new ConcurrentTaskScheduler();
	    }
}