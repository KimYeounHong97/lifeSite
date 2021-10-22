package com.life.site.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;


/**
 * @Date    : 2021. 1. 14.
 * @package : com.hansol.user
 * @file    : UserController.java
 * @Author  : sujin
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2021.1.14.   sujin        최초 생성
 * 2021.4.01.   sujin        vue framework 용 API 생성 (user/menu/prgmauth)
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoggerService loggerService;
    
    /**
     * /api/user/list
     * @name   getUserList
     */
    @PostMapping(value = "/list")
    public CommonResult getUserList(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        Map<String, Object> paramMap = param.getData();
        result.setData(userService.getUserList(paramMap));
        return result;
    }


    /**
     * /api/user/info
     * @name   getUserInfo
     */

    @PostMapping(value = "/info")
    public CommonResult getUserInfo(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        return CommonResult.ofSuccess(userService.getUserInfo(param, request));
    }
    
    
    @ResponseBody
    @PostMapping("/edit/info")
    public CommonResult editUserInfo(HttpServletRequest request, HttpSession session,
            HttpServletResponse response, @RequestParam HashMap<String, Object> param, RedirectAttributes redirectAttributes) throws Exception {
        CommonResult result = new CommonResult();
        int cnt =0;
        try {
        	cnt = userService.editUserInfo(param);
        	result.setStatus(cnt==0?false:true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        }
        return result;
    }
    
    
    /**
     * /api/user/log
     * @name   getUserAccessLogList
     */

    @PostMapping(value = "/log")
    public CommonResult getUserAccessLogList(HttpServletRequest request, CommonParam param) throws Exception {
        return CommonResult.ofSuccess(loggerService.getUserAccessLog(param.getData()));
    }
}
