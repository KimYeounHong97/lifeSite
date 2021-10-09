package com.life.site.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
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
        paramMap.put(CommonConstants.Params.COMP_CD, SessionManager.getUser(request).getCOMP_CD());
        result.setData(userService.getUserList(paramMap));
        return result;
    }

    /**
     * /api/user/orglist
     * @name   getUserOrgList
     */
    @PostMapping(value = "/orglist")
    public CommonResult getUserOrgList(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();

        Map<String, Object> input = param.getData();
        input.put(CommonConstants.Params.COMP_CD, SessionManager.getUser(request).getCOMP_CD());

        result.setData(userService.getUserOrgList(input));
        return result;
    }

    /**
     * /api/user/all-orglist
     * @name   getAllOrgList
     */
    @PostMapping(value = "/all-orglist")
    public CommonResult getAllOrgList(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(userService.getAllOrgList(SessionManager.getUser(request).getCOMP_CD()));
        return result;
    }

    /**
     * /api/user/info
     * @name   getUserInfo
     */

    @PostMapping(value = "/info")
    public CommonResult getUserInfo(HttpServletRequest request, CommonParam param) throws Exception {
        return CommonResult.ofSuccess(userService.getUserInfo(param.getData(), request));
    }
    
    /**
     * /api/user/menu
     * @name   getUserTreeMenuList
     */
    @PostMapping(value = "/menu")
    public CommonResult getUserMenuList(HttpServletRequest request) {
        return CommonResult.ofSuccess(SessionManager.getMenuList(request));
    }
    
    /**
     * /api/user/prgm
     * @name   getUserPrgmList
     */
    @PostMapping(value = "/prgm")
    public CommonResult getUserPrgmList(HttpServletRequest request) throws Exception {
        return CommonResult.ofSuccess(userService.getUserPrgmList(SessionManager.getUser(request)));
    }

    /**
     * /api/user/prgm-auth
     * @name  chkUserPrgmAuth
     */

    @PostMapping(value = "/prgm-auth")
    public CommonResult chkUserPrgmAuth(HttpServletRequest request, CommonParam param) throws Exception {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap = param.getData();
        paramMap.put(CommonConstants.Params.COMP_CD, SessionManager.getUser(request).getCOMP_CD());
        paramMap.put(CommonConstants.Params.USER_ID, SessionManager.getUser(request).getUSER_ID());

        Map<String, Object> result = new HashMap<>();
        String is_ok = "N";
        UserVo user = SessionManager.getUser(request);
        String prgm_cd = (String)paramMap.get("PROGRAM_CD");
        if(loggerService.checkAuthByPrgmcd(user, prgm_cd) > 0) {
            is_ok = "Y";
            // 화면 접속 로그
            loggerService.writeAccessLog(request, user, true, prgm_cd, CommonConstants.AccessGubun.PAGE);
        }
        
        result.put("IS_OK", is_ok);
        return CommonResult.ofSuccess(result);
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
