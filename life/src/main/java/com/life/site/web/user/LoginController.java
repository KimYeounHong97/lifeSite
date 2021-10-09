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
        loginInfo.setRememberId(param.get(CommonConstants.Params.REMEMBER_ID).toString());

        // 로그인 화면에서 가져온 정보 확인(loginInfo)
        log.debug("--------------------------------------------------------------->");
        log.debug("----------------------> login request info ");
        log.debug("----------------------> loginInfo.getUserId() = " + loginInfo.getUserId());
        // log.debug("----------------------> " + loginInfo.getPasswd());
        // log.debug("----------------------> " + loginInfo.getRememberId());
        log.debug("--------------------------------------------------------------->");

        try {
            loginService.processLogin(CommonConstants.AccessGubun.LOGIN, loginInfo, request, session);

            // 쿠키 생성 - userId 저장, 아이디 저장 기간 설정
            Cookie cookie = new Cookie(CommonConstants.COOKIE_REMEMBER_ID, loginInfo.getUserId());
            cookie.setPath("/");
            if (loginInfo.getRememberId().equals(CommonConstants.STR_Y)) {
                cookie.setMaxAge(60*60*24*7);
            }
            else {
                cookie.setMaxAge(0);
            }
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

        //비밀번호 설정 주기 확인
        if (!CommonConstants.AccessGubun.LOGIN.equals(CommonConstants.AccessGubun.SSO)) {
            Map<String, Object> input = new HashMap<String, Object>();
            input.put(CommonConstants.Params.COMP_CD, SessionManager.getUser(request).getCOMP_CD());
            
            if(loginService.passwdCheck(loginInfo,input)) {
                url = "/view/page/N40102";
            }
        }
        
        result.setData(url);
        result.setStatus(true);
        return result;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpSession session,
            HttpServletResponse response) throws Exception {
        Object obj = session.getAttribute(CommonConstants.HANSOL_SESSION);
        if (null != obj) {
            session.removeAttribute(CommonConstants.HANSOL_SESSION);
            session.invalidate();
        }

        // SSO 로그인일 경우 로그인 페이지가 아닌 안내페이지로 유도함.
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

    /**
     * 사업장 switch
     * 
     * @param request
     * @return 
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/switch-org")
    public String switchUserOrg( HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        
        try {
            // 세션이 없을 때
            if (session == null) {
                log.debug(" ======> session member 만료!!!");
                String returnMsg = "로그인 시간이 만료되었습니다. 다시 로그인해 주세요.";
                RequestDispatcher dispatcher = request.getRequestDispatcher("/timeout?message=" + returnMsg);
                dispatcher.forward(request, response);
            }
            
            LoginInfo loginInfo = new LoginInfo();
            UserVo user = SessionManager.getUser(request);

            loginInfo.setUserId(user.getUSER_ID());
            loginInfo.setORG_CD(StringUtil.getParameter(request, "ORG_CD"));
            loginInfo.setSwithOrg(true);
            
            // acc_gb 로그인 방법은 그대로 유지할 것 (SSO / LOGIN)
            // => 이 방식에 따라 로그인페이지로 갈 것인지 info 페이지로 갈 것인지 /timeout 에서 정해진다.
            loginService.processLogin(user.getACC_GB(), loginInfo, request, session);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:" + CommonConstants.MAIN_PAGE;
    }
}