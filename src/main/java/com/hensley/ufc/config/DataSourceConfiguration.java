//package com.hensley.ufc.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.Cloud;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.cloud.localconfig.LocalConfigConnector;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//@Profile("cloud")
//public class DataSourceConfiguration {
//
//  @Bean
//  public Cloud cloud() {
//    return new CloudFactory().getCloud();
//  }
//
//  @Bean
//  public CloudFactory cloudFactory() {
//      CloudFactory cf = new CloudFactory();
//      cf.registerCloudConnector(new LocalConfigConnector());
//      return cf;
//  }
//  
//  @Bean
//  @ConfigurationProperties()
//  public DataSource dataSource() {
//    return cloud().getSingletonServiceConnector(DataSource.class, null);
//  }
//
//}