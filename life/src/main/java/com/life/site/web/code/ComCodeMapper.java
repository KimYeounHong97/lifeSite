package com.life.site.web.code;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ComCodeMapper {
    
    public List<Map<String, Object>> getCodeList(Map<String, Object> param);
    public Map<String, Object> getCodeInfo(Map<String, Object> param);
    
}