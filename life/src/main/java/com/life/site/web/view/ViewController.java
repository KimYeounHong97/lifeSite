package com.life.site.web.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.life.site.config.param.CommonConstants;
import com.life.site.model.FileVo;
import com.life.site.model.LoginInfo;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.post.RegisterService;
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
    
    @Autowired
    ResourceLoader resourceLoader;
    
    @Autowired
    RegisterService registerService;
    
    
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
    
    @GetMapping("{postType}/detail/{title}")
    public String getPostDetailView(HttpServletRequest request, @PathVariable String postType, @PathVariable String title,
                            @ModelAttribute HashMap<String, Object> param) throws Exception {
        return "view/" + postType+ "/detail" ;
    }
    
    @GetMapping("{module}/{html}")
    public String getView(HttpServletRequest request, @PathVariable String module, @PathVariable String html,
                            @ModelAttribute HashMap<String, Object> param) throws Exception {
        return "view/" + module+ "/" + html ;
    }

    @GetMapping("view/{a}/{b}/{c}")
    public String getSampleTestView(HttpServletRequest request, @PathVariable String a, @PathVariable String b, @PathVariable String c, 
                            @ModelAttribute HashMap<String, Object> param) throws Exception {
        return "view/" + a + "/" + b + "/" + c;
    }
    
    @GetMapping("image/{fileId}")                                                         
	public ResponseEntity<?> serveFile(@PathVariable Long fileId){
		FileVo uploadFile = registerService.loadById(fileId);
		Resource resource = resourceLoader.getResource("file:" + uploadFile.getATTACH_DIR());
		return ResponseEntity.ok().body(resource);
	}
}