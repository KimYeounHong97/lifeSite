package com.life.site.web.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.life.site.model.UserVo;
import com.life.site.web.util.Util;


@Service("CommCodeService")
public class ComCodeService {
    
    @Autowired
    ComCodeMapper mapper;
    
    // 그룹별 공통코드 가져오기 (called from thymeleaf combo)
    public List<Map<String,Object>> getCodeList(String code_grp_cd) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("GRP_ID", code_grp_cd);
        return mapper.getCodeList(param);
    }
    
    // 그룹별 공통코드 가져오기 (called from thymeleaf combo)
    public Map<String,Object> getCodeInfo(String code_grp_cd , String code_cd) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("GRP_ID", code_grp_cd);
        param.put("CODE_CD", code_cd);
        return mapper.getCodeInfo(param);
    }
    

}