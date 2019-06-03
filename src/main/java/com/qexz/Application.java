package com.qexz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import java.util.Date;

/**
 * <p>返回方法最好用接口</p>
 * <p>Java 8新的语法，Lambda表达式，返回一个方法。在之前的版本中，返回方法一般用返回一个匿名的接口的实现类实现。</p>
 * 参考网址：<a>https://ask.csdn.net/questions/198070?sort=votes_count</a>
 */
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Converter<Long, Date> addNewConvert() {
        return new Converter<Long, Date>() {
            @Override
            public Date convert(Long source) {
                Date date = null;
                try {
                    date = new Date(source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    //这里是什么语法？
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            //ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");

            //container.addErrorPages(error401Page, error404Page, error500Page);
            container.addErrorPages(error404Page, error500Page);
        });
    }

}
