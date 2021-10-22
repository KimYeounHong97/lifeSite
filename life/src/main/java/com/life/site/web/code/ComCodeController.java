package com.life.site.web.code;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.web.util.Util;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class ComCodeController {

    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    ComCodeService service;

    @PostMapping("/list")
    public CommonResult getCodeList(HttpServletRequest request, CommonParam param) throws Exception {
        CommonResult commonResult  = new CommonResult();

        Map<String, Object> input = param.getData();
        input.put(CommonConstants.Params.LANG, Util.getLang());
        
        commonResult.setData(service.getCodeList(input.get("GRP_ID").toString()));
        
        return commonResult;
    }
}
