package com.qexz.config;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/17          FXY        Created
 **********************************************
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 拦截器
 */

@javax.servlet.annotation.WebFilter(urlPatterns = {"/account/api/login"})
@Order(1)
public class WebFilter implements Filter {

    @Autowired
    private RedisOperationsSessionRepository repository;

    public static Logger LOG = LoggerFactory.getLogger(WebFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("拦截器初始化");
        repository.cleanupExpiredSessions();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String username = request.getParameter("username");
        httpServletRequest.getSession().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        filterChain.doFilter(httpServletRequest, response);
    }

    @Override
    public void destroy() {
        LOG.info("拦截器销毁");
        repository.cleanupExpiredSessions();
    }
}
