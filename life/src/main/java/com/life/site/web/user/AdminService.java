package com.life.site.web.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    	 String projectPath = targetPath.toString();
    	 log.info("변경 path : "+projectPath);
    	 
    	 File projectFile = new File(projectPath);
    	 multipartFile.transferTo(projectFile);
    }
    
    
    public Map<String, Object> getUserList(HashMap<String, Object> param) throws IOException {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("result", adminMapper.selectUserList(param));
    	 return result;
    }
    
    public Map<String, Object> getAccessList(HashMap<String, Object> param) throws IOException {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("result", adminMapper.selectUserAccessList(param));
    	 return result;
    }
    
    public Map<String, Object> getCodeList(HashMap<String, Object> param) throws IOException {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("result", adminMapper.selectCommCodeList(param));
    	 return result;
    }
    
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public String addComCode(HashMap<String, Object> param) throws IOException {
    	int cnt = 0;
    	String returnMsg = "";
    	String chk ="";
    	String codeSeq = param.get("codeSeq").toString();
    	
    	//공통 코드 제약 조건 확인
    	chk = adminMapper.selectCodeDuplicateChk(param).get("CHK").toString(); //1-0. 코드 중복 확인
    	log.info("chk :"+chk);
    	if(chk.equals("N")) {
    		returnMsg = "해당 그룹아이디 코드가 이미 존재합니다.";
    		return returnMsg;
    	}else {
    		if(adminMapper.selectCodeSeqDuplicateChk(param)!= null) {
    			chk = adminMapper.selectCodeSeqDuplicateChk(param).get("CHK").toString(); //1-1. 코드 순서 중복 확인
    			String[] chkArray = chk.split(",");
        		for(String seq : chkArray) {
        			if(seq.equals(codeSeq)) {
        				returnMsg ="해당 그룹아이디 코드 순번이 이미 존재합니다.";
        				return returnMsg;
        			}
        		}
    		}
    	}
    	
    	cnt = adminMapper.insertCommCodeList(param);
    	if(cnt == 0) {
    		returnMsg = "등록된 건이 없습니다.";
    	}
    	 return returnMsg;
    }
    
    
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public String updateComCode(HashMap<String, Object> param) throws IOException {
    	int cnt = 0;
    	String returnMsg = "";
    	String chk ="";
    	String codeSeq = param.get("codeSeq").toString();
    	String originalCodeSeq = param.get("originalCodeSeq").toString();
    	
    	//공통 코드 제약 조건 확인
    	if(!codeSeq.equals(originalCodeSeq)) {
    		if(adminMapper.selectCodeSeqDuplicateChk(param)!= null) {
    			chk = adminMapper.selectCodeSeqDuplicateChk(param).get("CHK").toString(); //1-1. 코드 순서 중복 확인
    			String[] chkArray = chk.split(",");
        		for(String seq : chkArray) {
        			if(seq.equals(codeSeq)) {
        				returnMsg ="해당 그룹아이디 코드 순번이 이미 존재합니다.";
        				return returnMsg;
        			}
        		}
    		}
    	}
    	
    	cnt = adminMapper.updateCommCodeList(param);
    	if(cnt == 0) {
    		returnMsg = "수정 된 건이 없습니다.";
    	}
    	 return returnMsg;
    }
    
    
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public String deleteComCode(HashMap<String, Object> param) throws IOException {
    	int cnt = 0;
    	String returnMsg = "";
    	
    	cnt = adminMapper.deleteCommCodeList(param);
    	if(cnt == 0) {
    		returnMsg = "삭제 된 건이 없습니다.";
    	}
    	 return returnMsg;
    }
}
