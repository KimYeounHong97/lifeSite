package com.life.site.web.view;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date    : 2020. 12. 22.
 * @package : com.hansol.view
 * @file    : UserService.java
 * @Author  : hana
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 12. 22.  hana        최초 생성
 *
 */
@Service("ViewService")
public class ViewService {
    @Autowired
    ViewMapper mapper;

    protected Log log = LogFactory.getLog(ViewService.class);

    public Map<String, Object> getUrlByProgramCd(Map<String, Object> param) {
        return mapper.getUrlByProgramCd(param);
    }
}
