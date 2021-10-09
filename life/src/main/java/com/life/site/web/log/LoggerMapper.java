package com.life.site.web.log;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

@Repository
@Mapper
public interface LoggerMapper {
    public int insertAccessLog(Map<String, Object> param);
    public int insertHistoryLog(Map<String, Object> param);
    public int insertUserDeleteAuthAllLog(Map<String, Object> param);
    public List<Map<String, Object>> getUserAccessLog(Map<String, Object> param);    
    public int checkAuthByUrl(Map<String, Object> param);
    public int checkAuthByPrgmcd(Map<String, Object> param);
    public int insertInterfaceLog(Map<String, Object> param);
}
