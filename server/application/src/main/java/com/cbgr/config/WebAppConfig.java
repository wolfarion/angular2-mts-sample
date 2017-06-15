package com.cbgr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Конфиг веб приложения.
 */
@Configuration
@EnableAutoConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    private static final String SWAGGER_PAGE = "swagger.html";
    private static final String[] ALLOWED_ORIGINS = new String[] {"http://localhost:8080"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger").setViewName(SWAGGER_PAGE);
        super.addViewControllers(registry);
    }
}
