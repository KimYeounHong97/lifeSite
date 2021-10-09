package com.life.site.web.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.life.site.config.param.CommonResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date    : 2020. 11. 30.
 * @package : com.hansol.stdweb.log
 * @file    : LoggerController.java
 * @Author  : hana
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 11. 30.  hana        최초 생성
 *
 */
@RestController
@RequestMapping("/logging")
public class LoggerController { 
    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LoggerService loggerService;
    
    @PostMapping(value = "/interface")
    public CommonResult writeInterfaceLog(HttpServletRequest request) throws Exception {
        CommonResult result = new CommonResult();

        result.setData(loggerService.writeInterfaceLog(request));
        return result;
    }
}
