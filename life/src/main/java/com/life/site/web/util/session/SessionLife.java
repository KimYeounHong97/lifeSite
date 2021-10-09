package com.life.site.web.util.session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.life.site.model.UserVo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date    : 2020. 3. 16.
 * @package : com.hansol.common.param
 * @file    : SessionHansol.java
 * @Author  : PSJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 3. 16.      PSJ        최초 생성
 *
 */
@Getter
@Setter
public class SessionLife  implements Serializable {

    private static final long serialVersionUID = -2772004169608041412L;
    
    UserVo user;
    String login_ip;
    String country;
    List<Map<String, Object>> menulist;
}
