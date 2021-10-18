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
     * 사업장 목록 가져오기
     * => thymeleaf 직접 호출
     * @param comp_cd
     * @return
     */
    public List<HashMap<String, Object>> getAllOrgList(String comp_cd) {
        Map<String, Object> param = new HashMap<>();
        param.put(CommonConstants.Params.COMP_CD, comp_cd);
        return userMapper.getAllOrgList(param);
    }

    /**
     * 사업장 목록 가져오기
     * @param data
     * @return
     */
    public List<HashMap<String, Object>> getUserOrgList(Map<String,Object> param) {
        return userMapper.getUserOrgList(param);
    }
    
    /**
     * 부서 목록 가져오기
     * => thymeleaf combobox 직접 호출
     * @param comp_cd
     * @return
     */
    public List<HashMap<String, Object>> getDeptList(String comp_cd) {
        Map<String, Object> param = new HashMap<>();
        param.put(CommonConstants.Params.COMP_CD, comp_cd);
        return userMapper.getDeptList(param);
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
        String user_id = StringUtil.nvl(paramMap.get(CommonConstants.Params.USER_ID));
        String org_cd = StringUtil.nvl(paramMap.get(CommonConstants.Params.ORG_CD));

        if(StringUtil.isEmpty(user_id)) {
            userInfo = SessionManager.getUser(request);
        }
        else {
            UserVo userParam = UserVo.builder()
                                    .USER_ID(user_id)
                                    .build();

            userInfo = loginMapper.selectUserInfoById(userParam);
        }
        return userInfo;
    }

    /* java stream 사용 => JAVA 1.8 이상에서만 동작 */
    public Map<String, Object> getUserPrgmList(UserVo userParam) {

        List<Map<String, Object>> prgmList = loginMapper.getUserPrgmList(userParam);
        
        prgmList.stream().forEach(prgm -> {
            List<String> tempStr = new ArrayList<String>();
            Set<String> k = prgm.keySet();
            Iterator<String> kIterator = k.iterator();
            while(kIterator.hasNext()) {
                String tKey = kIterator.next();
                String tValue = (String)prgm.get(tKey);
                if(( tKey.indexOf("auth-") > -1 ) && "Y".equals(tValue)) {
                    tempStr.add(tKey);
                }
            }
            prgm.put("BTN_AUTH", tempStr);
        });

        Map<String, Object> prgmReturnMap = prgmList.stream().collect(Collectors.toMap(
                                                                (m)->(String)m.get("PROGRAM_CD"), (m)->m));
        
        return prgmReturnMap;
    }
}
