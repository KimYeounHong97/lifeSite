package com.life.site.config.exception;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.life.site.config.param.CommonResult;


@RestControllerAdvice
public class GlobalExceptionHandler {
    protected Log log = LogFactory.getLog(GlobalExceptionHandler.class);
    
    @ExceptionHandler(SQLException.class)
    protected CommonResult SqlException(HttpServletRequest request, SQLException e) {
        log.error("Sql Exception", e);
        String msg = e.getMessage();
        String sql_code = "";
        
        if (!msg.isEmpty()) {
            sql_code = msg.split(":")[0];
        }
        // SQL 메시지 필터
        SqlExceptionType sqlExceptionType = SqlExceptionType.getFromCode(sql_code);
        
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(false);
        commonResult.setMessage(sqlExceptionType.getMsg());
        
        return commonResult;
    }
    
    
    @ExceptionHandler(CommonResultException.class)
    protected CommonResult CommonResultException(HttpServletRequest request,CommonResultException e) {
        log.error("CommonResultException", e);
        //String msg = e.getMessage();

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(e.isStatus());
        commonResult.setMessage(e.getMessage());
        if(e.getData()!=null) {
      	  commonResult.setData(e.getData());
        }
        return commonResult;
    }
}
