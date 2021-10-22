package com.life.site.web.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.MapUtils;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.UserVo;
import com.life.site.web.util.Util;
import com.life.site.web.util.session.SessionManager;


@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    SettingsService service;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordManager passwdManager;

    @PostMapping(value = "/myinfo")
    public CommonResult getMyInfo(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        Map<String, Object> data = param.getData();
        data.put(CommonConstants.Params.LANG, Util.getLang());

        result.setData(service.getMyInfo(data));
        return result;
    }

    @PostMapping(value = "/myinfo-edit")
    public CommonResult editMyInfo(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        UserVo updateUser;

        HashMap<String, Object> data = (HashMap<String, Object>)param.getData();
        UserVo user = SessionManager.getUser(request);
        data.put(CommonConstants.Params.LOGIN_ID, user.getUSER_ID());

        result.setData(service.editMyInfo(data));
        
        // Update session
        updateUser = loginService.getUserInfoById(user.getUSER_ID());
        SessionManager.setUser(request, updateUser);
        return result;
    }

    /**
     * 탈퇴하기
     * 
     * @param request
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/unsubscribe")
    public CommonResult unsubscribeMyInfo(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();

        HashMap<String, Object> data = (HashMap<String, Object>)param.getData();
        UserVo user = SessionManager.getUser(request);
        data.put(CommonConstants.Params.LOGIN_ID, user.getUSER_ID());

        result.setData(service.unsubscribeMyInfo(data));
        return result;
    }

    /**
     * 비밀번호 조회
     * 
     * @param request
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/get-passwd")
    public CommonResult getPasswd(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        
        Map<String, Object> input = param.getData();
        input.put(CommonConstants.Params.USER_ID, SessionManager.getUser(request).getUSER_ID());
       
        if(MapUtils.isEmpty(service.getPasswd(input))) {
            result.setStatus(false);
            result.setMessage("현재 암호를 올바르게 입력해주세요.");
        }else {
            result.setStatus(true);
        }
        
        return result;
    }

    /**
     * 암호 초기화
     * - 사용자 암호 정보 변경/실패회수 초기화 및 암호 변경 이력 추가
     * - 메일 전송
     * 
     * @param request
     * @param param EMAIL, USER_ID, USER_NM 
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor= {RuntimeException.class, Exception.class, SQLException.class})
    @PostMapping(value = "/init-passwd")
    public CommonResult initPasswd(HttpServletRequest request, CommonParam param) throws Exception {
        UserVo user = SessionManager.getUser(request);
        Map<String, Object> input = param.getData();
        input.put(CommonConstants.Params.LOGIN_ID, user.getUSER_ID());

        return service.initPasswd(input);
    }

    /**
     * 비밀번호 변경 처리
     * 
     * @param request
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update-passwd")
    public CommonResult updatePasswd(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        UserVo user = SessionManager.getUser(request);
        Map<String, Object> input = param.getData();
        String encoded = null;

        input.put(CommonConstants.Params.LOGIN_ID, user.getUSER_ID());

        encoded = passwdManager.getSHA256(input.get(CommonConstants.Params.PASSWD).toString());
        input.put(CommonConstants.Params.PASSWD, encoded);

        result.setData(service.updatePasswd(input));
        return result;
    }
}
