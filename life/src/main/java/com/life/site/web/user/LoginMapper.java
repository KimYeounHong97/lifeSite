package com.life.site.web.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.life.site.model.UserVo;


/**
 * @Package  : com.hansol.user.LoginMapper
 * @Authr    : Hansol
 * @Date     : 2020. 05. 20.
 * @Desc     : 
 */
@Repository
@Mapper
public interface LoginMapper {
    public List<Map<String, Object>> getUserMenuList(UserVo user);

    public List<Map<String, Object>> getUserPrgmList(UserVo user);
    
    public Map<String, Object> selectPasswdUnchange(String userId);
    
    public Map<String, Object> selectPwRuleChangeDt(Map<String, Object> param);
    
    public Map<String, Object> selectUserGb(String userId);

    public UserVo selectUserInfoById(UserVo user);

    public void updatePasswdFailCount(Map<String, Object> param);
    
    public void updateZeroPasswdFailCount(String userId);
}
