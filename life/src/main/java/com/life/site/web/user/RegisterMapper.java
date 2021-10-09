package com.life.site.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RegisterMapper {
    public List<HashMap<String, Object>> selectCheckUser(Map<String, Object> param);
    public int insertRegisterUser(Map<String, Object> data);
    public int insertDefaultAuth(Map<String, Object> data);
    public List<HashMap<String, Object>> selectRules(Map<String, Object> param);
}
