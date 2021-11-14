package com.life.site.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.life.site.config.param.CommonConstants;
import com.life.site.config.param.CommonParam;
import com.life.site.config.param.CommonResult;
import com.life.site.model.FileVo;
import com.life.site.model.UserVo;
import com.life.site.web.log.LoggerService;
import com.life.site.web.util.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    
    @PostMapping(value = "/changeImageFile")
    public CommonResult  changeImageFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam HashMap<String, Object> param) throws Exception {
    	 CommonResult result = new CommonResult();
    	
    	adminService.changeImageFile(multipartFile, param);
        return  result;
    }
    
    
    @PostMapping(value = "/userList")
    @ResponseBody
    public CommonResult getUserList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        CommonResult result = new CommonResult();
        result.setData(adminService.getUserList(param));
        return result;
    }
}
