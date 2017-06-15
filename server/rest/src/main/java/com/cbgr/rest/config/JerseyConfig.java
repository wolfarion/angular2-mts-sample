package com.cbgr.rest.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

/**
 * Конфиг Jersey.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public static final String JSON_MEDIA_TYPE = MediaType.APPLICATION_JSON + "; charset=utf-8";
    private static final String RESOURCE_PACKAGE = "com.cbgr.rest.controller";

    public JerseyConfig() {
        packages(RESOURCE_PACKAGE)
                .register(new ValidationExceptionMapper())
                .register(new ServerExceptionMapper());
    }

    @PostConstruct
    public void init() {
        configureSwagger();
    }

    /**
     * Конфигурация Swagger.
     */
    private void configureSwagger() {
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);

        BeanConfig config = new BeanConfig();
        config.setConfigId("user-service");
        config.setTitle("User Service");
        config.setSchemes(new String[]{"http"});
        config.setBasePath("/user-service");
        config.setResourcePackage(RESOURCE_PACKAGE);
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}