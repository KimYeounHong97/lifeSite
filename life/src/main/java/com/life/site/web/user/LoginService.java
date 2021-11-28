package com.life.site.web.user;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.transaction.annotation.Transactional;

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

        encoded = Base64.getEncoder().encodeToString(loginInfo.getPasswd().getBytes());
        if (false == user.matchPassword(encoded)) {
            loggerService.writeAccessLog(request, user, false, null, null);
            throw new UserAuthException(MessageUtil.getMessage("err.user-auth", null));
        }

        return user;
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
        
        UserVo userParam = UserVo.builder().USER_ID(loginInfo.getUserId()).build();
        UserVo user = loginMapper.selectUserInfoById(userParam);
        
        if (null == user) {
            throw new UserAuthException(MessageUtil.getMessage("err.user-auth", null));
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
     * 아이디 조회
     *  
     * @param input
     * @return 아이디
     * @throws Exception
     */
    public Map<String, Object>  getFindId(HashMap<String, Object> param) throws Exception {
    	Map<String, Object> idInfo = new HashMap<String, Object>();
    	 String checkMethod = param.get("check_method").toString();
    	 String mobile1;
    	 String mobile2;
    	 String mobile3;
    	 
    	 if(checkMethod.equals("email")) {
    		 String email = param.get("email").toString();
    		 idInfo =  loginMapper.selectFindUserIdByEmail(email);
    	 }else if(checkMethod.equals("phone")) {
    		 idInfo =  loginMapper.selectFindUserIdByPhone(param);
    	 }
    	 return idInfo;
    }
    
    
    /**
     * 비밀번호 조회
     *  
     * @param input
     * @return 비밀번호
     * @throws Exception
     */
    public Map<String, Object>  getFindPswd(HashMap<String, Object> param) throws Exception {
    	Map<String, Object> pswdInfo = new HashMap<String, Object>();
    	 String checkMethod = param.get("check_method").toString();
    	 String mobile1;
    	 String mobile2;
    	 String mobile3;
    	 
    	 if(checkMethod.equals("email")) {
    		 pswdInfo =  loginMapper.selectFindUserPswdByEmail(param);
    	 }else if(checkMethod.equals("phone")) {
    		 pswdInfo =  loginMapper.selectFindUserPswdByPhone(param);
    	 }
    	 return pswdInfo;
    }
    
    
    /**
     * 아이디 중복 체크
     *  
     * @param input
     * @return 아이디
     * @throws Exception
     */
    public Boolean  getIdChk(HashMap<String, Object> param) throws Exception {
    	Map<String, Object> idInfo = new HashMap<String, Object>();
    	 String inputId = param.get("userId").toString();
    	 Boolean result = false;
    	 
    	 idInfo = loginMapper.selectSearchUserIdById(inputId);
    	 
    	 if(idInfo !=null && idInfo.size()!=0 ) {
     		result = false;
     	}else {
     		result = true;
     	}
    	 return result;
    }
    
    
    /**
     * 회원 등록
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public void  insertUser(HashMap<String, Object> param , String type) throws Exception {
    	String typeNm ="";
    	if(type.equals("USER")) {
    		typeNm ="일반유저";
    	}else if(type.equals("ADMIN")) {
    		typeNm = "시스템관리자";
    	}
    	param.put("type", type);
    	param.put("type_nm", typeNm);
    	
    	 //유저 등록
    	 loginMapper.insertUser(param);
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
        user = loginService.getLoginUserAuth(request, loginInfo);

        //API Permission 설정
        if(user.getUSER_GRADE()!=null || !user.getUSER_GRADE().isEmpty()){

            if(user.getUSER_GRADE().equals("GU")){
                user.setAPI_PERMISSION_ROLE(ApiPermission.Role.MEMBER);
            }else if(user.getUSER_GRADE().equals("SA")){
            	user.setAPI_PERMISSION_ROLE(ApiPermission.Role.ADMIN);
            }

        }
        // 세션 정보에 User 객체 저장
        SessionLife sess = new SessionLife();
        
        sess.setUser(user);
        sess.setLogin_ip(IpUtil.getClientIpAddr(request));

        // set session
        session.removeAttribute(CommonConstants.LIFE_SESSION);
        session.setAttribute(CommonConstants.LIFE_SESSION, sess);

        // 로그인 기록 남기기
        String accGb = null;
        
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
