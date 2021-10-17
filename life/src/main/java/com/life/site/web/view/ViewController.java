package com.life.site.web.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.LoginInfo;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.user.UserService;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

@Controller
@RequestMapping("/")
public class ViewController {
    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    ViewService viewService;

    @Autowired
    UserService userService;
    
    @Value("${env.editorimg-path}")
    private String editorimgPath;
    
    /**
     * @Date   : 2020. 3. 13.
     * @method : getLoginView
     * @desc   : 로그인 화면
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value  = "",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String getLoginView(@ModelAttribute LoginInfo loginInfo, Model model,
            @CookieValue(value=CommonConstants.COOKIE_REMEMBER_ID, required=false) Cookie cookie) throws Exception {
        if (null != cookie) {
            loginInfo.setUserId(cookie.getValue());
        }
        
        return "index";
    }
    
    /**
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value  = "info",
            method = {RequestMethod.POST, RequestMethod.GET})
    public String getInfoView(Model model, String message) throws Exception {
        model.addAttribute("message", message);
        return "info";
    }

    /**
     * @Date   : 2020. 12. 24.
     * @method : getTimeoutViewPost
     * @desc   : 
     *
     * @param loginInfo
     * @param model
     * @param cookie
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value  = "timeout",
            method = {RequestMethod.POST,RequestMethod.GET})
    public String getTimeoutView(@ModelAttribute LoginInfo loginInfo, Model model, 
            HttpServletRequest request, HttpServletResponse response,
            @CookieValue(value=CommonConstants.COOKIE_REMEMBER_ID, required=false) Cookie cookie_login_id,
            @CookieValue(value=CommonConstants.COOKIE_TYPE, required=false) Cookie cookie_type) throws Exception {

        if (null != cookie_login_id) {
            loginInfo.setUserId(cookie_login_id.getValue());
        }
        
        model.addAttribute("timeout", true);
        return "index";
    }


    /**
     * @Date   : 2020. 12. 07
     * @method : getWindowPopup
     * @desc   : 화면 return 화면 위치는 templates/{module}/{submodule}/{html} 로 맞춰 위치시킨다.
     *           
     * @param module
     * @param submodule
     * @param html
     * @param param
     * @return String (view path)
     * @throws Exception
     */
    @PostMapping("nsess/view/{module}/{html}")
    public String getNsessPost(HttpServletRequest request, @PathVariable String module, @PathVariable String html, 
            @ModelAttribute HashMap<String, Object> param) throws Exception {
        UserVo user = SessionManager.getUser(request);
        if (user != null) {
            loggerService.writeAccessLog(request, user, true, null, CommonConstants.AccessGubun.PAGE);
        }
        
        return "view/nsess/" + module + "/" + html;
    }
    
    @GetMapping("{module}/{html}")
    public String getView(HttpServletRequest request, @PathVariable String module, @PathVariable String html,
                            @ModelAttribute HashMap<String, Object> param) throws Exception {
        return "view/" + module+ "/" + html ;
    }

    
    /**
     * @Date   : 2020. 8. 20.
     * @method : getImageView
     * @desc   : CKEditor에 의해 본문 첨부된 이미지 전송을 위한 뷰
     *
     * @param html
     * @param param
     * @return
     * @throws Exception
     */
    @GetMapping("editorimg/{board_cd}/{dir}/{file_nm}")
    @ResponseBody
    public byte[] getImageView(HttpServletRequest request, @PathVariable String board_cd, @PathVariable String dir, @PathVariable String file_nm) throws Exception {
        
        FileInputStream in = new FileInputStream(new File(editorimgPath) + "/" + board_cd + "/" + dir + "/" + file_nm);
        
        return IOUtils.toByteArray(in);
    }

    @GetMapping("view/{a}/{b}/{c}")
    public String getSampleTestView(HttpServletRequest request, @PathVariable String a, @PathVariable String b, @PathVariable String c, 
                            @ModelAttribute HashMap<String, Object> param) throws Exception {
        return "view/" + a + "/" + b + "/" + c;
    }
}