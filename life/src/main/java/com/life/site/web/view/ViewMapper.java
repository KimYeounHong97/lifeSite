package com.life.site.web.view;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ViewMapper {
    public Map<String, Object> getUrlByProgramCd(Map<String, Object> param);
}
