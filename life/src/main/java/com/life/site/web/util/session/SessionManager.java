package com.life.site.web.util.session;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;


/**
 * @Date    : 2020. 3. 15.
 * @package : com.hansol.util
 * @file    : SessionManager.java
 * @Author  : PJJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 3. 15.      PJJ        최초 생성
 *
 */
public class SessionManager {

    public static UserVo getUser(HttpServletRequest request) {
        SessionLife sess = request.getSession().getAttribute(CommonConstants.HANSOL_SESSION)==null?
                                new SessionLife():(SessionLife)request.getSession().getAttribute(CommonConstants.HANSOL_SESSION);
        return (UserVo)sess.getUser();
    }
    
    public static String getUserIp(HttpServletRequest request) {
        SessionLife sess = request.getSession().getAttribute(CommonConstants.HANSOL_SESSION)==null?
                new SessionLife():(SessionLife)request.getSession().getAttribute(CommonConstants.HANSOL_SESSION);
        return sess.getLogin_ip();
    }
    
    public static String getCountry(HttpServletRequest request) {
        SessionLife sess = request.getSession().getAttribute(CommonConstants.HANSOL_SESSION)==null?
                new SessionLife():(SessionLife)request.getSession().getAttribute(CommonConstants.HANSOL_SESSION);
        return sess.getCountry();
    }
    
    public static List<Map<String, Object>> getMenuList(HttpServletRequest request) {
        SessionLife sess = request.getSession().getAttribute(CommonConstants.HANSOL_SESSION)==null?
                new SessionLife():(SessionLife)request.getSession().getAttribute(CommonConstants.HANSOL_SESSION);
        return sess.getMenulist();
    }
    
    public static void setUser(HttpServletRequest request, UserVo user) {
        SessionLife sess = request.getSession().getAttribute(CommonConstants.HANSOL_SESSION)==null?  new SessionLife():(SessionLife)request.getSession().getAttribute(CommonConstants.HANSOL_SESSION);
        sess.setUser(user);
        request.getSession().setAttribute(CommonConstants.HANSOL_SESSION, sess);
    }
}
