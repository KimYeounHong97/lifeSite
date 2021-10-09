package com.life.site.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SettingsMapper {
    public List<HashMap<String, Object>> selectMyInfo(Map<String, Object> param);

    public int updateMyInfo(HashMap<String, Object> param);

    public int deleteMyInfo(HashMap<String, Object> param);

    public HashMap<String, Object> selectRecentAccessLog(String userId);

    public HashMap<String, Object> selectPassword(Map<String, Object> param);

    public int updatePasswd(Map<String, Object> param);

    public int insertPasswdHistory(Map<String, Object> param);
}
