package com.life.site.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonResult;
import com.life.site.model.LoginInfo;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.user.exception.UserAuthException;
import com.life.site.web.util.MessageUtil;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;

@Controller
@RequestMapping("/user")
public class LoginController {

    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    PasswordManager passwordManager;

    @Autowired
    LoginService loginService;

    @Autowired
    LoggerService loggerService;
    
    /**
     * @Date   : 2020. 3. 14.
     * @method : loginCheck
     * @desc   : 
     *
     * @param user
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/login")
    public CommonResult loginCheck(HttpServletRequest request, HttpSession session,
            HttpServletResponse response, @RequestParam HashMap<String, Object> param, RedirectAttributes redirectAttributes) throws Exception {
        CommonResult result = new CommonResult();
        LoginInfo loginInfo = new LoginInfo();
        String url = CommonConstants.MAIN_PAGE;

        loginInfo.setUserId(param.get(CommonConstants.Params.USER_ID).toString());
        loginInfo.setPasswd(param.get(CommonConstants.Params.PASSWD).toString());

        // 로그인 화면에서 가져온 정보 확인(loginInfo)
        log.debug("--------------------------------------------------------------->");
        log.debug("----------------------> login request info ");
        log.debug("----------------------> loginInfo.getUserId() = " + loginInfo.getUserId());
        log.debug("--------------------------------------------------------------->");

        try {
            loginService.processLogin(CommonConstants.AccessGubun.LOGIN, loginInfo, request, session);

            // 쿠키 생성 - userId 저장, 아이디 저장 기간 설정
            Cookie cookie = new Cookie(CommonConstants.COOKIE_REMEMBER_ID, loginInfo.getUserId());
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            Cookie cookieType = new Cookie(CommonConstants.COOKIE_TYPE, CommonConstants.AccessGubun.LOGIN);
            cookieType.setPath("/");
            cookieType.setMaxAge(60*60*24*7);
            response.addCookie(cookieType);
        } catch (UserAuthException e) {
            log.error(e.getMessage());

            loginInfo.setMessage(e.getMessage());
            redirectAttributes.addFlashAttribute(loginInfo);

            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            loginInfo.setMessage(e.getMessage());
            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        }

        
        result.setData(url);
        result.setStatus(true);
        return result;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpSession session,
            HttpServletResponse response) throws Exception {
        Object obj = session.getAttribute(CommonConstants.LIFE_SESSION);
        if (null != obj) {
            session.removeAttribute(CommonConstants.LIFE_SESSION);
            session.invalidate();
        }

        String returnMsg = "로그아웃되었습니다.\n다시 접속해 주세요.";
        RequestDispatcher dispatcher = request.getRequestDispatcher("/timeout?message=" + returnMsg);
        dispatcher.forward(request, response);
    }

    /**
     * /user/timeout
     * @name   modalLogin
     */
    @ResponseBody
    @PostMapping(value = "/timeout")
    public CommonResult modalLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response,
            @RequestParam HashMap<String, Object> param) throws Exception {

        CommonResult result = new CommonResult();
        LoginInfo loginInfo = new LoginInfo();
        
        try {
            loginInfo.setUserId(param.get(CommonConstants.Params.USER_ID).toString());
            loginInfo.setPasswd(param.get(CommonConstants.Params.PASSWD).toString());

            // 로그인 화면에서 가져온 정보 확인(loginInfo)
            log.debug("----------------------> login timeout");
            log.debug("----------------------> " + loginInfo.getUserId());
            log.debug("----------------------> " + loginInfo.getPasswd());

            loginService.processLogin(CommonConstants.AccessGubun.LOGIN, loginInfo, request, session);
        } 
        catch (UserAuthException e) {
            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        } 
        catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        }

        result.setMessage(MessageUtil.getMessage("msg.done.login", null));
        result.setStatus(true);
        return result;
    }
    

    @ResponseBody
    @PostMapping("/find/id")
    public CommonResult findIdCheck(HttpServletRequest request, HttpSession session,
            HttpServletResponse response, @RequestParam HashMap<String, Object> param, RedirectAttributes redirectAttributes) throws Exception {
        CommonResult result = new CommonResult();
        Map<String, Object> idInfo = new HashMap<String, Object>();
        String url = param.get("returnUrl").toString();

        try {
        	idInfo =loginService.getFindId(param);
        	if(idInfo !=null && idInfo.size()!=0 ) {
        		result.setData(url);
                result.setStatus(true);
        	}else {
                result.setStatus(false);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setStatus(false);
            return result;
        }
        return result;
    }
}