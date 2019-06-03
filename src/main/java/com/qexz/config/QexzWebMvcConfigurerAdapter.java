package com.qexz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * <p>此类在本项目中没有作用，该项目中静态资源用Nginx管理，此处留作学习使用<p/>
 */
@Configuration
public class QexzWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {


    @Value("${upload.file.path}")
    private String uploadFilePath;

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //自定义项目内目录
        //registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
        //指向外部目录
        registry.addResourceHandler("/upload/**").addResourceLocations(uploadFilePath);
        super.addResourceHandlers(registry);
    }
}