package com.qexz.config;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/20          FXY        Created
 **********************************************
 */

import com.qexz.common.QexzConst;
import com.qexz.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@javax.servlet.annotation.WebFilter(urlPatterns = {"/*"})
@Order(2)
public class RedirectFilter implements Filter {

    @Autowired
    private RedisOperationsSessionRepository repository;

    public static Logger LOG = LoggerFactory.getLogger(RedirectFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("拦截器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Account account = (Account) httpServletRequest.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        if(account!=null){
            String username = account.getUsername();
            String currentSessionId = httpServletRequest.getSession().getId();
            ExpiringSession session = repository.getSession(currentSessionId);
            long creationTime = session.getCreationTime();
            Map<String, ? extends Session> allSession = repository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
            if (!allSession.isEmpty()) {
                allSession.forEach((key, value) -> {

                    ExpiringSession expiringSession = (ExpiringSession) value;
                    if (expiringSession.getCreationTime() > creationTime) {
                        try {
                            repository.delete(currentSessionId);
                            httpServletResponse.sendRedirect("/invalid");
                            return ;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
        LOG.info("拦截器销毁");
    }
}
