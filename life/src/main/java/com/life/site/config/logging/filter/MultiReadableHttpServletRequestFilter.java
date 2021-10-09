package com.life.site.config.logging.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.life.site.config.logging.MultiReadableHttpServletRequest;
import com.life.site.config.logging.RequestWrapper;
import com.life.site.config.logging.util.AgentUtils;
import com.life.site.config.logging.util.MDCUtil;
import com.life.site.model.UserVo;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.*;

@Component
@Order
public class MultiReadableHttpServletRequestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        MultiReadableHttpServletRequest multiReadRequest = new MultiReadableHttpServletRequest((HttpServletRequest) req);
        
        RequestWrapper requestWrapper = RequestWrapper.of(multiReadRequest);
        // Set Http Header
        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP_MDC, requestWrapper.headerMap());

        // Set Http Body
        MDCUtil.setJsonValue(MDCUtil.PARAMETER_MAP_MDC, requestWrapper.parameterMap());
          
        // If you use SpringSecurity, you could SpringSecurity UserDetail Information.
        //System.out.println(SessionManager.getUser((HttpServletRequest) request).toString());
        UserVo uservo =  SessionManager.getUser( (HttpServletRequest)multiReadRequest);
        MDCUtil.setJsonValue(MDCUtil.USER_INFO_MDC, uservo);

        // Set Agent Detail
        MDCUtil.setJsonValue(MDCUtil.AGENT_DETAIL_MDC, AgentUtils.getAgentDetail((HttpServletRequest) multiReadRequest));
        
        // Set Http Request URI
        MDCUtil.set(MDCUtil.REQUEST_URI_MDC, requestWrapper.getRequestUri());
        
        MDCUtil.set(MDCUtil.TRANSACTION_ID, UUID.randomUUID().toString());
        
        try {
            chain.doFilter(multiReadRequest, res);
        } finally {
            MDC.clear();
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}