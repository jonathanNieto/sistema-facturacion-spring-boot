package com.jonatnie.facturacionapp;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MvcConfig
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /* This is only for developing TODO: remove before deploy */
    private final Logger log = LoggerFactory.getLogger(getClass());
            
    private static final String RESOURCES_PATH = Paths.get("upload").toAbsolutePath().toUri().toString();
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        /* This is only for developing TODO: remove before deploy */
        log.info("RESOURCES_PATH: " + RESOURCES_PATH);

        registry
        .addResourceHandler("/upload/**")
        .addResourceLocations(RESOURCES_PATH);
    }

}