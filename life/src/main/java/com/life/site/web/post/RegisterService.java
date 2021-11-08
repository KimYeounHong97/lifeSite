package com.life.site.web.post;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.life.site.config.param.CommonConstants;
import com.life.site.model.FileVo;
import com.life.site.model.PostVo;
import com.life.site.model.PostVo.Post;
import com.life.site.model.PostVo.Post.PostBuilder;
import com.life.site.model.PostVo.PostList;
import com.life.site.model.UserVo;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Service("RegisterService")
@Slf4j
public class RegisterService {
	@Autowired
	public RegisterMapper registerMapper;
	
	@Value("${env.editorimg-path}")
    private String editorimgPath;
	
	@Value("${env.upload-path}")
    private String uploadPath;
	
	public FileVo uploadImageFile(MultipartFile file, HashMap<String, Object> param ,HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		log.info("--------------------------");
		log.info(param.toString());
		String userId = param.get("loginUserId").toString();
		// 업로드할 폴더 경로
		String realFolder = editorimgPath;
		UUID uuid = UUID.randomUUID();

		String org_filename = file.getOriginalFilename(); // 업로드할 파일 이름
		String originalFileExtension = org_filename.substring(org_filename.lastIndexOf(".")+1);
		String str_filename = uuid.toString() +"."+originalFileExtension; // 랜덤 UUID+확장자로 저장될 savedFileName
		log.info("원본 파일명 : " + org_filename);
		log.info("저장할 파일명 : " + str_filename);
		
		Date dt =new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
        String datafolder=sdf.format(dt).toString();

		//String filepath = realFolder + "\\"+dt+"\\" + str_filename;
		String filepath = realFolder;
        
        File dir=new File(filepath+"/", datafolder);
        log.info("dir :"+dir.toString());
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        
        File targetFile = new File(dir+"/" + str_filename);
        log.info("targetFile :"+targetFile.toString());
		file.transferTo(targetFile);
		
		FileVo newFile = new FileVo();
		
		newFile.setFILE_STORE_NM(str_filename);
        newFile.setFILE_ORIGIN_NM(org_filename);
        newFile.setATTACH_TYPE(originalFileExtension);
        newFile.setATTACH_DIR(filepath+"/"+ datafolder);
        newFile.setFILE_SIZE(Long.toString(file.getSize()));
        newFile.setDEL_FL("N");
        newFile.setREG_USER_ID(userId);
        newFile.setURL_PATH(datafolder);
        registerMapper.insertEidtSave(newFile);

		return newFile;
	}
	
	public FileVo loadById(Long fileId) {
		return registerMapper.loadById(fileId);
	}
	
	 /**
     * 포스트 등록
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public int  insertPost(HashMap<String, Object> param, MultipartFile multipartFile) throws Exception {
    	String postType = param.get("postType").toString();
    	int cnt = 0;
    	switch (postType) {
		case "animals":
			cnt = animalsInsert(param,multipartFile);
			break;
		}
    	return cnt;
    }
    
    /**
     * 포스트 수정
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public int  updatePost(HashMap<String, Object> param, MultipartFile multipartFile) throws Exception {
    	String postType = param.get("postType").toString();
    	int cnt = 0;
    	switch (postType) {
		case "animals":
			cnt = animalsUpdate(param,multipartFile);
			break;
		}
    	return cnt;
    }
    
    /**
     * 동물 포스트
     *  
     * @param input
     * @throws Exception
     */
    
    private int animalsInsert(HashMap<String, Object> param , MultipartFile multipartFile) throws Exception {
    	int cnt = 0;
    	String fileInsert = param.get("fileInsert").toString();
    	String[] fileIdArray; 
    	
    	//포스트 저장
    	cnt = registerMapper.insertAnimals(param);
    		if(cnt == 0) return cnt;
    	//대표 이미지 저장
    	cnt = uploadTitleImageFile(param, multipartFile);
    		if(cnt == 0) return cnt;
    		
    	//editor 첨부파일 체크
    	if(fileInsert.equals("Y")) {
    		fileIdArray = param.get("fileIdArray").toString().split(",");
    		Arrays.stream(fileIdArray).forEach(id->{
    			param.put("attach_id", id);
    			registerMapper.updateEditorAttach(param);
    		});
    	}
    	
    	return cnt;
    }
    
    
    /**
     * 동물 포스트
     *  
     * @param input
     * @throws Exception
     */
    
    private int animalsUpdate(HashMap<String, Object> param , MultipartFile multipartFile) throws Exception {
    	int cnt = 0;
    	String fileInsert = param.get("fileInsert").toString();
    	String[] fileIdArray; 
    	
    	//포스트 저장
    	cnt = registerMapper.insertAnimals(param);
    		if(cnt == 0) return cnt;
    	//대표 이미지 저장
    	cnt = uploadTitleImageFile(param, multipartFile);
    		if(cnt == 0) return cnt;
    		
    	//editor 첨부파일 체크
    	if(fileInsert.equals("Y")) {
    		fileIdArray = param.get("fileIdArray").toString().split(",");
    		Arrays.stream(fileIdArray).forEach(id->{
    			param.put("attach_id", id);
    			registerMapper.updateEditorAttach(param);
    		});
    	}
    	
    	return cnt;
    }
    
    private int uploadTitleImageFile(HashMap<String, Object> param , MultipartFile multipartFile) throws Exception {
		int attachId = 0;
		// 업로드할 폴더 경로
		String realFolder = uploadPath;
		UUID uuid = UUID.randomUUID();

		String org_filename = multipartFile.getOriginalFilename(); // 업로드할 파일 이름
		String originalFileExtension = org_filename.substring(org_filename.lastIndexOf(".")+1);
		String str_filename = uuid.toString() +"."+originalFileExtension; // 랜덤 UUID+확장자로 저장될 savedFileName
		log.info("원본 파일명 : " + org_filename);
		log.info("저장할 파일명 : " + str_filename);
		
		Date dt =new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
        String datafolder=sdf.format(dt).toString();

		String filepath = realFolder;
        
        File dir=new File(filepath+"/", datafolder);
        log.info("dir :"+dir.toString());
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        
        File targetFile = new File(dir+"/" + str_filename);
        log.info("targetFile :"+targetFile.toString());
        multipartFile.transferTo(targetFile);
		
		FileVo newFile = new FileVo();
		newFile.setPOST_ID(Integer.parseInt(param.get("POST_ID").toString()));
		newFile.setFILE_STORE_NM(str_filename);
        newFile.setFILE_ORIGIN_NM(org_filename);
        newFile.setATTACH_TYPE(originalFileExtension);
        newFile.setATTACH_DIR(filepath+"/"+ datafolder);
        newFile.setFILE_SIZE(Long.toString(multipartFile.getSize()));
        newFile.setDEL_FL("N");
        newFile.setREG_USER_ID(param.get("loginUserId").toString());
        newFile.setURL_PATH(datafolder);
        registerMapper.insertTitleSave(newFile);
        
        attachId = newFile.getATTACH_ID();
		return attachId;
	}
}
