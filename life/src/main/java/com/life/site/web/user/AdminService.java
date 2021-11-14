package com.life.site.web.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.UserVo;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("AdminService")
public class AdminService {

    @Autowired
    AdminMapper adminMapper;
    
    public void changeImageFile( MultipartFile multipartFile , HashMap<String, Object> param) throws IOException {
    	// 업로드할 폴더 경로
    	String url = "static"+param.get("path").toString();
    	ClassPathResource resource = new ClassPathResource(url);
    	 Path targetPath = Paths.get(resource.getURI());
    	 String projectPath = targetPath.toString().replace("target", "src").replace("classes", "main/resources");
    	 
    	 File projectFile = new File(projectPath);
    	 multipartFile.transferTo(projectFile);
    }
    
    
    public Map<String, Object> getUserList(HashMap<String, Object> param) throws IOException {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("result", adminMapper.selectUserList(param));
    	 return result;
    }
}
