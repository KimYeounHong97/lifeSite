package com.life.site.web.post;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.io.BaseEncoding;
import com.google.gson.JsonObject;
import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.FileVo;
import com.life.site.model.LoginInfo;
import com.life.site.model.UserVo;
import com.life.site.model.PostVo.Post;
import com.life.site.model.PostVo.PostList;
import com.life.site.web.log.LoggerService;
import com.life.site.web.user.exception.UserAuthException;
import com.life.site.web.util.MessageUtil;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;
import com.sun.mail.util.BASE64EncoderStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    RegisterService registerService;
    
    @Autowired
    ResourceLoader resourceLoader;

    @PostMapping(value = "/uploadImageFile")
    public JsonObject  getPostList(@RequestParam("file") MultipartFile multipartFile,@RequestParam HashMap<String, Object> param, HttpServletRequest request) throws Exception {
    	JsonObject jsonObject = new JsonObject();
    	FileVo file = null;
    	file = registerService.uploadImageFile(multipartFile, param,request);
    	
    	if (file == null) {
    		jsonObject.addProperty("url", "");
            jsonObject.addProperty("responseCode", "error");
    	} 
    	 
		jsonObject.addProperty("url", "/editorimg/"+ file.getURL_PATH() +"/"+ file.getFILE_STORE_NM());
		jsonObject.addProperty("attach_id", file.getATTACH_ID());
        jsonObject.addProperty("responseCode", "success");
        
        return  jsonObject;
    }
    
    @ResponseBody
    @PostMapping("/save")
    public CommonResult insertPost(HttpServletRequest request, HttpSession session,
            HttpServletResponse response, @RequestParam HashMap<String, Object> param, @RequestPart(value="file",required = false) @RequestParam("file") MultipartFile multipartFile) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(registerService.insertPost(param,multipartFile));
        return result;
    }
    
    @ResponseBody
    @PostMapping("/update")
    public CommonResult updatePost(HttpServletRequest request, HttpSession session,
            HttpServletResponse response, @RequestParam HashMap<String, Object> param, @RequestPart(value="file",required = false) @RequestParam("file") MultipartFile multipartFile) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(registerService.insertPost(param,multipartFile));
        return result;
    }
}