package com.life.site.config.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.life.site.config.param.CommonConstants;
import com.life.site.web.util.IpUtil;

public class ParamInterceptor extends HandlerInterceptorAdapter {
    
    protected Log log = LogFactory.getLog(ParamInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        String url = request.getRequestURI().toLowerCase();
        String ip = IpUtil.getClientIpAddr(request);
        String[] blockKeywords = CommonConstants.BLOCK_KEYWORD.split(";");
        boolean result = true;
        
        try {
            if (blockKeywords != null) {
                for (int i = 0; i < blockKeywords.length; i++) {
                    
                    Enumeration<String> param = request.getParameterNames();
                    
                    while (param.hasMoreElements()) {
                        String[] value = request.getParameterValues((String) param.nextElement());
                        
                        if (value != null) {
                            for (int j = 0; j < value.length; j++) {
                                if (value[j].toLowerCase().indexOf(blockKeywords[i].toLowerCase()) != -1) {
                                    result = false;
                                    response.sendRedirect("/error");
                                    throw new Exception("부적절한 파라미터입니다.\n" + value[j]);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex){
            log.debug("====================================== START ======================================");
            log.debug(" ======> Request URL\t: " + url);
            log.debug(" ======> FROM IP\t: " + ip);
            log.debug(" ======> ERROR\t: " + ex.getMessage());
            log.debug("====================================== END ======================================\n");
        }
        return result;
    }
}
