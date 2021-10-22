package com.life.site.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;

/**
 * @Date    : 2021. 1. 14.
 * @package : com.hansol.user
 * @file    : UserService.java
 * @Author  : sujin
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2021.1.14.   sujin        최초 생성
 */
@Service("UserService")
public class UserService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    LoginMapper loginMapper;

    public List<HashMap<String, Object>> getUserList(Map<String, Object> param) {
        return userMapper.selectUserList(param);
    }
    

    /**
     * 사용자 정보 조회
     * => USER_ID = null일 경우 현재 로그인 되어 있는 사용자 정보 return (get from session)
      * @param paramMap USER_ID
      *                 ORG_CD 사업장코드 (전사 null 값이 기본값. 사업장이 존재하는 경우 넣어주세요)
      * @param request
      * @return
     */
    public UserVo getUserInfo(Map<String, Object> paramMap, HttpServletRequest request) {
        
        UserVo userInfo = UserVo.builder().build();
        String user_id = StringUtil.nvl(paramMap.get(CommonConstants.Params.LOGIN_USERID));

        
        UserVo userParam = UserVo.builder()
                .USER_ID(user_id)
                .build();

        userInfo = loginMapper.selectUserInfoById(userParam);

        /*if(StringUtil.isEmpty(user_id)) {
            userInfo = SessionManager.getUser(request);
        }
        else {
            UserVo userParam = UserVo.builder()
                                    .USER_ID(user_id)
                                    .build();

            userInfo = loginMapper.selectUserInfoById(userParam);
        }*/
        return userInfo;
    }
    
    /**
     * 회원 정보 수정
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public int  editUserInfo(HashMap<String, Object> param) throws Exception {
    	int cnt =0;
    	 //유저 등록
    	 cnt = userMapper.updateUserInfo(param);
    	 return cnt;
    }
    
}
