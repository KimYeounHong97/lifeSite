package com.life.site.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.life.site.config.param.CommonConstants;
import com.life.site.web.log.LoggerService;

@Service("RegisterService")
public class RegisterService {
    @Autowired
    RegisterMapper mapper;

    @Autowired
    LoggerService loggerService;
    
    @Autowired
    PasswordManager passwdManager;

    public List<HashMap<String, Object>> checkId(Map<String, Object> param) {
        return  mapper.selectCheckUser(param);
    }

    /**
     * 회원가입 처리
     * - 사용자 INSERT, 권한 (기본 권한 부여) INSERT
     * @param data
     * @return
     * @throws Exception 
     */
    public int registerUser(Map<String, Object> data) throws Exception {
        int result = 0;

        String pwd = passwdManager.getSHA256(data.get(CommonConstants.Params.PASSWD).toString());
        data.put(CommonConstants.Params.PASSWD, pwd);

        result =  mapper.insertRegisterUser(data);
        loggerService.writeHistoryLog(data, CommonConstants.Params.USER_ID, CommonConstants.History.USER, CommonConstants.History.INSERT, "");

        mapper.insertDefaultAuth(data);
        loggerService.writeHistoryLog(data, CommonConstants.Params.USER_ID, CommonConstants.History.AUTH, CommonConstants.History.INSERT, "");
        return result;
    }

    public List<HashMap<String, Object>> getRules(Map<String, Object> param) {
        return mapper.selectRules(param);
    }
}
