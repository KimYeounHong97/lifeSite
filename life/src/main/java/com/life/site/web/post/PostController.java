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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.io.BaseEncoding;
import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
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


@RestController
@RequestMapping("/api/post")
public class PostController {

    protected Log log = LogFactory.getLog(this.getClass());
    
    @Autowired
    PostService postService;


    @PostMapping(value = "/list")
    public CommonResult getPostList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(postService.getPostList(param));
        return result;
    }
    
    @PostMapping(value = "/info")
    public CommonResult getPostInfo(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        CommonResult result = new CommonResult();
        
        result.setData(postService.getPostInfo(param));
        return result;
    }
    
    
    @PostMapping("/delete")
    public CommonResult deletePost(HttpServletRequest request, HttpSession session,HttpServletResponse response, @RequestParam HashMap<String, Object> param, RedirectAttributes redirectAttributes) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(postService.DeletePost(param));
        return result;
    }

}