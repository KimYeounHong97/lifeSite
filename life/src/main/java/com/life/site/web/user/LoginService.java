package com.life.site.web.user;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.life.site.config.annotation.ApiPermission;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.LoginInfo;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.user.exception.UserAuthException;
import com.life.site.web.util.IpUtil;
import com.life.site.web.util.MessageUtil;
import com.life.site.web.util.ObjectUtil;
import com.life.site.web.util.session.SessionLife;

/**
 * @Package  : com.hansol.common.user.LoginService
 * @Authr    : Hansol
 * @Date     : 2020. 05. 20.
 * @Desc     : 
 */
@Service("LoginService")
public class LoginService {
    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    LoginService loginService;

    @Autowired
    LoggerService loggerService;

    @Autowired
    FavMenuService favMenuService;;
    
    @Autowired
    MessageSource messageSource;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    PasswordManager passwdManager;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    public List<Map<String, Object>> getUserMenuList(UserVo user) throws Exception {
        return loginMapper.getUserMenuList(user);
    }
    
    /**
     * 로그인 정보 확인
     *  - 로그인 아이디 조회 및 비밀번호 확인
     *  
     * @param loginInfo
     * @return User 객체 - 로그인한 사용자 정보
     * @throws Exception
     */
    public UserVo getLoginUserAuth(HttpServletRequest request, LoginInfo loginInfo) throws Exception {
        String encoded = null;
        UserVo userParam = UserVo.builder().USER_ID(loginInfo.getUserId()).build();
        UserVo user = loginMapper.selectUserInfoById(userParam);
        
        if (null == user) {
            throw new UserAuthException(MessageUtil.getMessage("err.user-auth", null));
        }

        encoded = passwdManager.getSHA256(loginInfo.getPasswd());
        if (false == user.matchPassword(encoded)) {
            setLoginFailCount(user);
            loggerService.writeAccessLog(request, user, false, null, null);
            throw new UserAuthException(MessageUtil.getMessage("err.user-auth", null));
        }

        return user;
    }

    /**
     * 로그인 비밀번호 오류 회수 카운트
     * 
     * @param user
     * @throws Exception
     */
    public void setLoginFailCount(UserVo user) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        data.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());

        loginMapper.updatePasswdFailCount(data);
    }
    
    /**
     * 로그인 비밀번호 오류 회수 초기화
     *  - 로그인 성공 시 오류 회수를 0으로 초기화한다
     *  
     * @param user
     * @throws Exception
     */
    public void initLoginFailCount(UserVo user) throws Exception {
        loginMapper.updateZeroPasswdFailCount(user.getUSER_ID());
    }

    /**
     * 로그인 정보 확인 (비밀번호 체크 X)
     *  - 로그인 아이디로 조회
     *  
     * @param loginInfo
     * @return User 객체 - 로그인한 사용자 정보
     * @throws Exception
     */
    public UserVo getLoginUserNoPasswd(HttpServletRequest request, LoginInfo loginInfo) throws Exception {
        
        UserVo userParam = UserVo.builder().USER_ID(loginInfo.getUserId()).ORG_CD(loginInfo.getORG_CD()).build();
        UserVo user = loginMapper.selectUserInfoById(userParam);
        
        if (null == user) {
            throw new UserAuthException(MessageUtil.getMessage("err.user-auth", null));
        }

        if (CommonConstants.STR_Y.equals(user.getLOCK_FL())) {
            loggerService.writeAccessLog(request, user, false, null, null);
            throw new UserAuthException(MessageUtil.getMessage("err.user-lock", null));
        }

        return user;
    }
    
    /**
     * 비밀번호  변경 주기
     *  - 접속 로그인 아이디 조회 
     *  
     * @param loginInfo
     * @return 비밀번호 변경 주기
     * @throws Exception
     */
    public Map<String, Object> getPasswdUnchange(LoginInfo loginInfo) throws Exception {
        return  loginMapper.selectPasswdUnchange(loginInfo.getUserId());
    }
    
    /**
     * 암호 정책 변경 주기 조회
     *  
     * @param input
     * @return 암호 정책 변경 주기
     * @throws Exception
     */
    public Map<String, Object> getPwRuleChangeDt(Map<String, Object> input) throws Exception {
        return  loginMapper.selectPwRuleChangeDt(input);
    }
    
    /**
     * 임직원 여부
     *  
     * @param loginInfo
     * @return 임직원 여부
     * @throws Exception
     */
    public Map<String, Object> getUserGb(LoginInfo loginInfo) throws Exception {
        return  loginMapper.selectUserGb(loginInfo.getUserId());
    }
    
    
    /**
     * 로그인 처리
     * - 사용자 확인 및
     * @param loginType
     * @param loginInfo
     * @param request
     * @param session
     * @throws Exception
     */
    public void processLogin(String loginType, LoginInfo loginInfo, HttpServletRequest request, HttpSession session) throws UserAuthException, Exception {
        
        // 사용자 아이디로 DB 조회 및 비밀번호 확인
        UserVo user;
        if (CommonConstants.AccessGubun.SSO.equals(loginType) || loginInfo.isSwithOrg()) {
            user = loginService.getLoginUserNoPasswd(request, loginInfo);
        }
        else {
            user = loginService.getLoginUserAuth(request, loginInfo);
        }

        // 로그인 구분 정보 저장
        user.setACC_GB(loginType);

        // 계정 잠금상태 확인
        if (CommonConstants.STR_Y.equals(user.getLOCK_FL())) {
            loggerService.writeAccessLog(request, user, false, null, null);
            throw new UserAuthException(MessageUtil.getMessage("err.user-lock", null));
        }


        //API Permission 설정
        if(user.getADMIN_GUBUN()==null || user.getADMIN_GUBUN().isBlank()){

            if(user.getUSER_GB().equals("0")){
                user.setAPI_PERMISSION_ROLE(ApiPermission.Role.MEMBER);
            }else if(user.getUSER_GB().equals("1")){
                user.setAPI_PERMISSION_ROLE(ApiPermission.Role.VENDOR);
            } else{
                user.setAPI_PERMISSION_ROLE(ApiPermission.Role.VENDOR);
            }

        }else{
            user.setAPI_PERMISSION_ROLE(ApiPermission.Role.ADMIN);
        }

        // 세션 정보에 User 객체 저장
        SessionLife sess = new SessionLife();
        
        List<Map<String, Object>> menuList = (List<Map<String, Object>>)loginService.getUserMenuList(user);

        Map<String,Object> param_fav = new HashMap<String,Object>();
        param_fav.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        param_fav.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());
        param_fav.put(CommonConstants.Params.ORG_CD, user.getORG_CD());
        List<Map<String, Object>> favorites = (List<Map<String, Object>>)favMenuService.getFavoritesMenu(param_fav);

        user.setFavoritesMenuList(favorites);
        sess.setUser(user);
        sess.setLogin_ip(IpUtil.getClientIpAddr(request));
        sess.setMenulist(ObjectUtil.addTreeObject(menuList));

        // 국가 정보 조회
        try {
            sess.setCountry(loginService.getCountry(sess.getLogin_ip()));
        }
        catch (Exception e ) {
            e.getStackTrace();
            log.error("ERROR:: get Country API error id(" + user.getUSER_ID() + "), ip(" + sess.getLogin_ip() + ")");
            sess.setCountry(CommonConstants.STR_NO_INFO);
        }
        
        // set session
        session.removeAttribute(CommonConstants.HANSOL_SESSION);
        session.setAttribute(CommonConstants.HANSOL_SESSION, sess);

        // 비밀번호 실패 횟수 0으로 초기화
        loginService.initLoginFailCount(user);
        // 로그인 기록 남기기
        String accGb = null;
        if(loginInfo.isSwithOrg()) {
            accGb = CommonConstants.AccessGubun.CHGORG;
        }
        loggerService.writeAccessLog(request, user, true, null, accGb);
    }
    
    /**
     * 비밀번호 설정 주기 로직
     * @param loginInfo
     * @param input 
     * @throws Exception
     */
    public boolean passwdCheck(LoginInfo loginInfo, Map<String, Object> input) throws UserAuthException, Exception {
        int unchange = 0;
        int changeDt =0;
        Boolean passwdCheck = false;
        
        try {
                if(!loginService.getUserGb(loginInfo).get("USER_GB").toString().equals("0")) {//임직원 여부 확인
                
                    unchange= Integer.parseInt(loginService.getPasswdUnchange(loginInfo).get("UNCHANGE").toString());
                    changeDt = Integer.parseInt(loginService.getPwRuleChangeDt(input).get("VALUE").toString());
                    
                    if(unchange >= changeDt) {
                        passwdCheck = true;
                    }
                }
        }
        catch (Exception e ) {
            e.getStackTrace();
        }
        return passwdCheck;
    }

    /**
     * 국가 정보 조회
     *  - ip2c.org API 사용
     * @param ip
     */
    public String getCountry(String ip) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(CommonConstants.API_GET_COUNTRY + ip).openConnection();
        conn.setDefaultUseCaches(false);
        conn.setUseCaches(false);
        conn.connect();
        InputStream is = conn.getInputStream();
        String result = CommonConstants.STR_NO_INFO;

        int c = 0;
        String s = "";
        while ((c = is.read()) != -1)
            s+= (char)c;

        is.close();
        switch (s.charAt(0)) {
            case '0':
                log.warn("getCountry API : Something wrong");
                break;
            case '1':
                String[] reply = s.split(";");
//                log.debug("Two-letter: " + reply[1]);
//                log.debug("Three-letter: " + reply[2]);
//                log.debug("Full name: " + reply[3]);
                
                Locale locale = null;
                String[] countries = Locale.getISOCountries();
                for (String country : countries) {
                    if (country.equals(reply[1])) {
                        locale = new Locale(Locale.KOREA.getCountry(), country);
                        result = locale.getDisplayCountry(Locale.KOREA);
                        break;
                    }
                }
                break;
            case '2':
                log.warn("getCountry API : Not found in database");
                break;
        }
        
        return result;
    }

    public UserVo getUserInfoById(String userId) throws Exception {
        UserVo userParam = UserVo.builder().USER_ID(userId).build();
        UserVo user = loginMapper.selectUserInfoById(userParam);
        return user;
    }
}
