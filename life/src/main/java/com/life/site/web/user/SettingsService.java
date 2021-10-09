package com.life.site.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonResult;
import com.life.site.web.util.MessageUtil;
import com.life.site.web.util.mail.EmailManager;

@Service("SettingsService")
public class SettingsService {
    @Autowired
    SettingsMapper mapper;

    @Autowired
    PasswordManager passwdManager;

    @Autowired
    EmailManager emailManager;

    public List<HashMap<String, Object>> getMyInfo(Map<String, Object> param) {
        return mapper.selectMyInfo(param);
    } 

    public int editMyInfo(HashMap<String, Object> param) {
        return mapper.updateMyInfo(param);
    } 

    public int unsubscribeMyInfo(HashMap<String, Object> param) {
        return mapper.deleteMyInfo(param);
    } 
    
    public HashMap<String, Object> getRecentAccessLog(String userId) {
        return mapper.selectRecentAccessLog(userId);
    }

    public HashMap<String, Object> getPasswd(Map<String, Object> param) throws Exception {
        String encoded = passwdManager.getSHA256(param.get(CommonConstants.Params.OLD_PW).toString());
        param.put(CommonConstants.Params.OLD_PW, encoded);
        return mapper.selectPassword(param);
    }

    /**
     * 암호 초기화 
     * - 사용자 암호 업데이트/실패회수 초기화 및 히스토리 추가
     * - 초기화 완료 후 메일로 변경된 비밀번호 전송
     * 
     * @param param
     * @return
     * @throws Exception 
     */
    public CommonResult initPasswd(Map<String, Object> param) throws Exception {
        CommonResult result = new CommonResult();
        CommonResult emailResult;
        String ran = null;

        ran = passwdManager.getRandomPassword();
        param.put(CommonConstants.Params.PASSWD, passwdManager.getSHA256(ran));
        result.setData(updatePasswd(param));
        
        emailManager.init(CommonConstants.Email.FROM_ADDR, CommonConstants.Email.FROM_NAME,
                MessageUtil.getMessage("mail.title.init-passwd", null),
                MessageUtil.getMessage("mail.content.init-passwd", new String[] { ran }));

        emailManager.addTo(param.get(CommonConstants.Params.EMAIL).toString(), param.get(CommonConstants.Params.USER_NM).toString());
        //emailResult = emailManager.send();
       // result.setStatus(emailResult.getStatus());
        //result.setMessage(emailResult.getMessage());

        return result;
    }

    /**
     * 비밀번호 변경
     * 
     * @param param
     * @return
     */
    public int updatePasswd(Map<String, Object> param) {
        int result = mapper.updatePasswd(param);
        mapper.insertPasswdHistory(param);

        return result;
    }
}
