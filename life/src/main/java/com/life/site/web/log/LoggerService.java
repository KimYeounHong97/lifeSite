package com.life.site.web.log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;
import com.life.site.web.util.IpUtil;
import com.life.site.web.util.session.SessionManager;


/**
 * @Date    : 2020. 04. 20.
 * @package : com.hansol.stdweb.log
 * @file    : LoggerService.java
 * @Author  : KYH
 * @version : 1.0
 */
@Configuration
@Component
@Service("LoggerService")
public class LoggerService {
    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LoggerMapper loggerMapper;
    
    @Autowired
    MessageSource messageSource;
    
    /**
     * 액세스 로그
     * 
     * @param request
     * @param user  세션 유저 정보
     * @param isSuccess 호출 성공/실패 여부
     * @param prgmCd 프로그램코드
     * @param accGb 접근 구분(LOGIN, SSO, PAGE(메뉴 호출))
     * @return
     */
    public int writeAccessLog(HttpServletRequest request, UserVo user, boolean isSuccess, String prgmCd, String accGb) {
        Map<String, Object> param = new HashMap<String, Object>();
        String agent;
        StringBuffer accType = new StringBuffer();
        
        if (user == null) {
            log.error("writeAccessLog failed. User obj is null"); 
            return -1;
        }

        param.put(CommonConstants.Logs.USER_ID, user.getUSER_ID());
        param.put(CommonConstants.Logs.USER_NM, user.getUSER_NM());
        param.put(CommonConstants.Logs.COMP_CD, user.getCOMP_CD());
        param.put(CommonConstants.Logs.DEPT_CD, user.getDEPT_CD());
        param.put(CommonConstants.Logs.DEPT_NM, user.getDEPT_NM());
        param.put(CommonConstants.Logs.GRADE_CD, user.getGRADE_CD());
        param.put(CommonConstants.Logs.GRADE_NM, user.getGRADE_NM());
        param.put(CommonConstants.Logs.ORG_CD, user.getORG_CD());
        param.put(CommonConstants.Logs.ORG_NM, user.getORG_NM());
        param.put(CommonConstants.Logs.ACC_IP, IpUtil.getClientIpAddr(request));

        /* 세션에 저장된 사용자 로그인 구분 정보 말고 페이지 호출인 경우 인자로 넘겨준 값을 기록한다 */
        if (accGb != null) {
            param.put(CommonConstants.Logs.ACC_GB, accGb);
        } else {
            param.put(CommonConstants.Logs.ACC_GB, user.getACC_GB());
        }

        // Agent 정보
        agent = request.getHeader("user-agent");
        accType.append(getBrowswerInfo(agent));
        accType.append(", ");

        // 접속 방법 (모바일, PC)
        Device device = DeviceUtils.getCurrentDevice(request);
        if ((null == device) || device.isNormal()) {
            accType.append(CommonConstants.STR_PC);
        }
        else {
            accType.append(CommonConstants.STR_MOBILE);
        }

        param.put(CommonConstants.Logs.AGENT, agent);
        param.put(CommonConstants.Logs.ACC_TYPE, accType.toString());
        param.put(CommonConstants.Logs.COUNTRY, SessionManager.getCountry(request));

        // Parameter
        StringBuilder parameterMessage = new StringBuilder();
        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, Object> lists = new HashMap<String, Object>();
        
        if (parameters != null) {
            for (Object key : parameters.keySet()) {
                String keyStr = (String) key;
                String[] value = (String[]) parameters.get(keyStr);
                parameterMessage.append((String) key + " : " + Arrays.toString(value) + " ");
                lists.put(keyStr, value[0].toString());
            }
        }

        if (isSuccess) {
            param.put(CommonConstants.Logs.SUCCESS_FL, CommonConstants.STR_SUCCESS);
        }
        else {
            param.put(CommonConstants.Logs.SUCCESS_FL, CommonConstants.STR_FAIL);
        }
        
        // 로그인 기록을 남길 때에는 url 주소는 기록하지 않는다. (20201224 => 그냥 다 남겨보자)
//        if (user.getACC_GB() != CommonConstants.AccessGubun.LOGIN
//                && user.getACC_GB() != CommonConstants.AccessGubun.SSO) {
        param.put(CommonConstants.Logs.ACC_URL, request.getRequestURI());
        
        if (prgmCd != null) {
            param.put(CommonConstants.Logs.PROGRAM_CD, prgmCd);
        } else {
            param.put(CommonConstants.Logs.PROGRAM_CD, lists.get(CommonConstants.Params.CALLED_PROGRAM));
        }
        param.put(CommonConstants.Logs.MENU_CD, lists.get(CommonConstants.Params.MENU_CD));
        param.put(CommonConstants.Logs.MENU_NM, lists.get(CommonConstants.Params.MENU_NM));
        
        return loggerMapper.insertAccessLog(param);
    }
    
    /**
     * ITGC 히스토리 로그
     * 
     * @param param
     * @return
     */
    public int writeHistoryLog(Map<String, Object> argParam, String targetId,  String jobTarget, String jobGubun, String bigo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(CommonConstants.Params.TARGET_ID, argParam.get(targetId));
        param.put(CommonConstants.Params.COMP_CD, argParam.get("COMP_CD"));
        param.put(CommonConstants.Params.ACT_ID, argParam.get("LOGIN_ID"));
        param.put(CommonConstants.Params.JOB_TARGET, jobTarget);
        param.put(CommonConstants.Params.JOB_GUBUN, jobGubun);
        param.put(CommonConstants.Params.BIGO, bigo);

        return loggerMapper.insertHistoryLog(param);
    }
    
    /**
     * ITGC 히스토리 로그 - 일괄 insert
     * @param argParam
     * @param targetId
     * @param jobTarget
     * @param jobGubun
     * @return
     */
    public int writeUserDeleteLog(Map<String, Object> argParam, String targetId,  String jobTarget, String jobGubun, String bigo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(CommonConstants.Params.TARGET_ID, argParam.get(targetId));
        param.put(CommonConstants.Params.COMP_CD, argParam.get("COMP_CD"));
        param.put(CommonConstants.Params.ACT_ID, argParam.get("LOGIN_ID"));
        param.put(CommonConstants.Params.JOB_TARGET, jobTarget);
        param.put(CommonConstants.Params.JOB_GUBUN, jobGubun);
        param.put(CommonConstants.Params.BIGO, bigo);

        return loggerMapper.insertUserDeleteAuthAllLog(param);
    }

    /**
    * 사용자 별 접속 로그 조회
    * @param param
    * @return
    * @throws Exception
    */
    public List<Map<String, Object>> getUserAccessLog(Map<String, Object> param) throws Exception{
        return loggerMapper.getUserAccessLog(param);
    }

    /**
    * 사용자 별 접속 로그 조회
    * - 설정-나의정보 에서 활동피드 조회 시 사용 (call from thymeleaf)
    *
    * @param param
    * @return
    * @throws Exception
    */
    public List<Map<String, Object>> getAccessLogById(String user_id) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(CommonConstants.Params.USER_ID, user_id);
        return loggerMapper.getUserAccessLog(param);
    }

    /**
     * url 권한 조회 
     * @param param
     * @return
     * @throws Exception
     */
    public int checkAuthByUrl(String userId, String url) {
        Map<String, Object> param = new HashMap<String, Object>();
        String[] urlArr = url.replace("?", ";").split(";");
        String urlStr = urlArr[0];
        param.put("USER_ID", userId);
        param.put("URL", urlStr);
        return loggerMapper.checkAuthByUrl(param);
    }

    /**
     * 메뉴 권한 조회 
     * @param param
     * @return
     * @throws Exception
     */
    public int checkAuthByPrgmcd(UserVo user, String prgmCd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(CommonConstants.Params.USER_ID, user.getUSER_ID());
        param.put(CommonConstants.Params.COMP_CD, user.getCOMP_CD());
        param.put(CommonConstants.Params.ORG_CD, user.getORG_CD());
        param.put(CommonConstants.Params.CALLED_PROGRAM, prgmCd);
        return loggerMapper.checkAuthByPrgmcd(param);
    }
    
    protected String getBrowswerInfo(String userAgent) {
        String browser = "unknown";
        
        if (userAgent != null) {
            if (userAgent.indexOf("Trident") > -1) {
                browser = "MSIE";
            } else if (userAgent.indexOf("Chrome") > -1) {
                browser = "Chrome";
            } else if (userAgent.indexOf("Opera") > -1) {
                browser = "Opera";
            } else if (userAgent.indexOf("iPhone") > -1
                    && userAgent.indexOf("Mobile") > -1) {
                browser = "iPhone";
            } else if (userAgent.indexOf("Android") > -1
                    && userAgent.indexOf("Mobile") > -1) {
                browser = "Android";
            }
            else {
                browser = "Firefox";
            }
        }
        return browser; 
    }

    /**
     * 인터페이스 통계 
     * 
     * @param request
     * @param user
     * @param param
     * @return
     */
    public int writeInterfaceLog(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put(CommonConstants.Params.IF_NAME, request.getParameter("IF_NAME"));
        param.put(CommonConstants.Params.IF_ID, request.getParameter("IF_ID"));
        param.put(CommonConstants.Params.PROGRAM_CD, request.getParameter("PROGRAM_CD"));
        param.put(CommonConstants.Params.PROGRAM_KEY, request.getParameter("PROGRAM_KEY"));
        param.put(CommonConstants.Params.APRV_EVENT, request.getParameter("APRV_EVENT"));
        param.put(CommonConstants.Params.RESULT_CODE, request.getParameter("RESULT_CODE"));
        param.put(CommonConstants.Params.RESULT_MESSAGE, request.getParameter("RESULT_MESSAGE"));
        param.put(CommonConstants.Params.LOGIN_ID, request.getParameter("IF_NAME"));
        param.put(CommonConstants.Params.COMP_CD, request.getParameter("COMP_CD"));

        log.debug(param);
        
        return loggerMapper.insertInterfaceLog(param);
    }
}
