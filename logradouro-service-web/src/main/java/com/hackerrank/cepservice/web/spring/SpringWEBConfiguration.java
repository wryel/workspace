package com.hackerrank.cepservice.web.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.hackerrank.cepservice.core"})
public class SpringWEBConfiguration {

    /**
     * Workaround para um velho conhecido bug do JSF. Quando uma propriedade
     * wrapper númerica recebe null, ele converte automaticamente para 0
     * 
     * @see https://java.net/jira/browse/JAVASERVERFACES-3071
     */
    static {
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
    }
    
    SpringWEBConfiguration() {
       
    }
}