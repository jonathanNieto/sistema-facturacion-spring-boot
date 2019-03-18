package com.jonatnie.facturacionapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MvcConfig
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final String FILE_UPLOAD = "file:/home/jonathan/temp/upload/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry
        .addResourceHandler("/upload/**")
        .addResourceLocations(FILE_UPLOAD);
    }

}