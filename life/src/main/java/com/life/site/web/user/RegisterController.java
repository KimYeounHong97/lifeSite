package com.life.site.web.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;


@RestController
@RequestMapping("/user")
public class RegisterController {
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    RegisterService registerService;

    @Autowired
    SettingsService settingService;

    /**
     * 회원 가입 처리
     * 
     * @Date   : 2020. 3. 14.
     * @method : loginCheck
     * @desc   : 
     *
     * @param user
     * @param model
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor= {RuntimeException.class, Exception.class, SQLException.class})
    @PostMapping("/signup")
    public CommonResult loginCheck(HttpServletRequest request, HttpServletResponse response, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();

        Map<String, Object> data = (HashMap<String, Object>) param.getData();
        data.put(CommonConstants.Params.LOGIN_ID, data.get(CommonConstants.Params.USER_ID));

        // INSESRT 동작은 항상 결과가 1로 나와야 함. 해당 결과만 전달
        result.setData(registerService.registerUser(data));

        return result;
    }

    /**
     * 아이디 중복 체크
     * 
     * @param request
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/check-id")
    public CommonResult checkUser(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(registerService.checkId(param.getData()));
        return result;
    }

    /**
     * 암호 정책 조회 (회원가입 시 사용)
     * 
     * @param request
     * @param param COMP_CD
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/get-rules")
    public CommonResult getPasswdRules(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult result = new CommonResult();

        Map<String, Object> input = param.getData();
        result.setData(registerService.getRules(input));

        return result;
    }

    /**
     * 로그인 화면에서 패스워드 초기화 하기
     * 
     * @param request
     * @param param EMAIL, USER_ID, USER_NM
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor= {RuntimeException.class, Exception.class, SQLException.class})
    @PostMapping(value = "/init-passwd")
    public CommonResult initPasswd(HttpServletRequest request, CommonParam param) throws Exception {
        Map<String, Object> input = param.getData();
        input.put(CommonConstants.Params.LOGIN_ID, CommonConstants.STR_SYSTEM);

        return settingService.initPasswd((HashMap<String, Object>) input);
    }
}
