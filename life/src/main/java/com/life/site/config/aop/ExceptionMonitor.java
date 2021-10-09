package com.life.site.config.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.life.site.web.util.EnvProperty;



@Aspect
@Component

public class  ExceptionMonitor  {
    //@Autowired
    //private SqlSession sqlSession;
    
    @Autowired
    private EnvProperty env;
   
    protected Log log = LogFactory.getLog(this.getClass());

    @AfterThrowing(value = "execution(* com..*Service.*(..))", throwing = "ex")
    public void afterDAOException(JoinPoint joinPoint, Exception ex) {

        //SQLException sql_ex = new SQLException(ex.getCause());
        log.debug(ex.getMessage());
    }    
    @AfterThrowing(value = "execution(* com..*Controller.*(..))", throwing = "ex")
    public void afterControllerException(JoinPoint joinPoint, Exception ex) {
        log.debug(ex.getMessage());
        if(ex.getMessage().isEmpty()) {
            return;
         }
        // exception stack to string
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        
        String profile = System.getProperty("spring.profiles.active");
    }


}