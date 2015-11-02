package com.hackerrank.cepservice.ws.spring;

import javax.ws.rs.ext.Provider;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {
    "com.hackerrank.cepservice.core", 
    "com.hackerrank.cepservice.ws", 
    "org.jboss.resteasy.plugins.providers.jackson"}, 
    includeFilters = { 
        @ComponentScan.Filter(
            type = FilterType.ANNOTATION, 
            value = Provider.class
        )
    }
)
public class SpringWSConfiguration {

    SpringWSConfiguration() {
        
    }
}
