package com.life.site.web.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.life.site.config.param.CommonConstants;
import com.life.site.model.FileVo;
import com.life.site.model.PostVo;
import com.life.site.model.PostVo.Post;
import com.life.site.model.PostVo.Post.PostBuilder;
import com.life.site.model.PostVo.PostList;
import com.life.site.model.UserVo;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date : 2021. 1. 14.
 * @package : com.hansol.user
 * @file : UserService.java
 * @Author : sujin
 * @version : 1.0
 *
 *          =============================================== 수정내역
 *          =============================================== DATE AUTHOR NOTE
 *          ----------------------------------------------- 2021.1.14. sujin 최초
 *          생성
 */
@Slf4j
@Service("PostService")
public class PostService {
	@Autowired
	public PostMapper postMapper;
	
	/**
	 * 메인 화면 포스트 조회
	 * 
	 * @param input
	 * @throws Exception
	 */
	public PostList getpostPicklist(HashMap<String, Object> param) {
		List<HashMap<String, Object>> List = new ArrayList<HashMap<String, Object>>();
		PostBuilder builder = Post.builder();
		List<Post> postList = new ArrayList<PostVo.Post>();
		PostList obj = new PostList().builder().build();
		List = postMapper.selectPickPostList(param);
		
		log.info(List.toString());

		List.forEach(post -> {
								 builder.POST_ID(post.get("POST_ID").toString())
								 		.TYPE(post.get("TYPE").toString())
										.TITLE(post.get("TITLE").toString())
										.CONTENT(post.get("CONTENT").toString())
										.REG_USER_ID(post.get("REG_USER_ID").toString())
										.REG_DT(post.get("REG_DT").toString())
										.MOD_USER_ID(post.get("MOD_USER_ID").toString())
										.MOD_DT(post.get("MOD_DT").toString()).build();
								 
								// 첨부파일 조회
								  List<FileVo> postAttaches = new ArrayList<FileVo>();
								  
								  param.put("postId", post.get("POST_ID").toString());
								  param.put("postType", post.get("TYPE").toString());
								  log.info(param.toString());
								  List<Map<String, Object>> postAttach = postMapper.selectPostAttachList(param);
								  log.info(postAttach.toString());
								   postAttach.forEach(attach ->{ postAttaches.add(PostVo.ofPostAttach(attach));
								  });
								  
								   log.info(postAttaches.toString());
								 builder.postAttaches(postAttaches);
								 
								postList.add(builder.build());
					});
		
		obj.setPosts(postList);
		
		return obj;
	}

	/**
	 * 포스트 전체 조회
	 * 
	 * @param input
	 * @throws Exception
	 */
	public PostList getPostList(HashMap<String, Object> param) {
		String postType = param.get("postType").toString();
		String morePostYn = String.valueOf(param.get("morePostYn"));
		List<HashMap<String, Object>> List = new ArrayList<HashMap<String, Object>>();
		PostBuilder builder = Post.builder();
		List<Post> postList = new ArrayList<PostVo.Post>();
		PostList obj = new PostList().builder().build();
		switch (postType) {
			case "animals":
				List = postMapper.selectPostAnimalList(param);
				break;
			case "arts-entertainment":
				List = postMapper.selectPostArtEntertainmentList(param);
				break;
			case "destinations":
				List = postMapper.selectPostDestinationsList(param);
				break;
			case "history":
				List = postMapper.selectPostHistoryList(param);
				break;
			case "lifeStyle":
				List = postMapper.selectPostLifeStyleList(param);
				break;
			case "nature":
				List = postMapper.selectPostNatureList(param);
				break;
			case "people":
				List = postMapper.selectPostPeopleList(param);
				break;
		}

		List.forEach(post -> {
								 builder.POST_ID(post.get("POST_ID").toString())
										.TITLE(post.get("TITLE").toString())
										.CONTENT(post.get("CONTENT").toString())
										.REG_USER_ID(post.get("REG_USER_ID").toString())
										.REG_DT(post.get("REG_DT").toString())
										.MOD_USER_ID(post.get("MOD_USER_ID").toString())
										.MOD_DT(post.get("MOD_DT").toString())
										.TOTAL_COUNT(post.get("TOTAL_COUNT").toString())
										.TYPE(post.get("TYPE").toString());
								 
								 if(!morePostYn.equals("Y")) {
									
								 }
								 
								// 첨부파일 조회
								  List<FileVo> postAttaches = new ArrayList<FileVo>();
								  
								  param.put("postId", post.get("POST_ID").toString());
								  log.info(param.toString());
								  List<Map<String, Object>> postAttach = postMapper.selectPostAttachList(param);
								  log.info(postAttach.toString());
								   postAttach.forEach(attach ->{ postAttaches.add(PostVo.ofPostAttach(attach));
								  });
								  
								   log.info(postAttaches.toString());
								 builder.postAttaches(postAttaches);
								 
								postList.add(builder.build());
					});
		
		obj.setPosts(postList);
		
		return obj;
	}
	
	/**
	 * 포스트 상세 조회
	 * 
	 * @param input
	 * @throws Exception
	 */
	public Post getPostInfo(HashMap<String, Object> param) {
		String postId = param.get("postId").toString();
		String postType = param.get("postType").toString();
		HashMap<String, Object> info = new HashMap<String, Object>();
		PostBuilder builder = Post.builder();
		param.put("postId", postId);
		log.info(param.toString());
		
		switch (postType) {
			case "animals":
				info = postMapper.selectPostAnimalInfo(param);
				break;
			case "arts-entertainment":
				info = postMapper.selectPostArtEntertainmentInfo(param);
				break;
			case "destinations":
				info = postMapper.selectPostDestinationsInfo(param);
				break;
			case "history":
				info = postMapper.selectPostHistoryInfo(param);
				break;
			case "lifeStyle":
				info = postMapper.selectPostLifeStyleInfo(param);
				break;
			case "nature":
				info = postMapper.selectPostNatureInfo(param);
				break;
			case "people":
				info = postMapper.selectPostPeopleInfo(param);
				break;
		}
			 builder.POST_ID(info.get("POST_ID").toString())
					.TITLE(info.get("TITLE").toString())
					.CONTENT(info.get("CONTENT").toString())
					.REG_USER_ID(info.get("REG_USER_ID").toString())
					.REG_DT(info.get("REG_DT").toString())
					.MOD_USER_ID(info.get("MOD_USER_ID").toString())
					.MOD_DT(info.get("MOD_DT").toString());

		// 첨부파일 조회
		  List<FileVo> postAttaches = new ArrayList<FileVo>();
		  
		  param.put("postId", info.get("POST_ID").toString());
		  List<Map<String, Object>> postAttach =postMapper.selectPostAttachList(param);
		 
		 
		   postAttach.forEach(attach ->{ postAttaches.add(PostVo.ofPostAttach(attach));
		  });
		   
		   builder.postAttaches(postAttaches);
		
		return builder.build();
	}
	
	
	 /**
     * 포스트 삭제
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public int  DeletePost(HashMap<String, Object> param) throws Exception {
    	String postType = param.get("postType").toString();
    	int cnt = 0;
    	switch (postType) {
			case "animals":
				cnt = postMapper.deleteAnimals(param);
				break;
			case "arts-entertainment":
				cnt = postMapper.deleteArtEntertainment(param);
				break;
			case "destinations":
				cnt = postMapper.deleteDestinations(param);
				break;
			case "history":
				cnt = postMapper.deleteHistory(param);
				break;
			case "lifeStyle":
				cnt = postMapper.deleteLifeStyle(param);
				break;
			case "nature":
				cnt = postMapper.deleteNature(param);
				break;
			case "people":
				cnt = postMapper.deletePeople(param);
				break;
		}
    	
    	if(cnt == 0)
    		return cnt;
    	//첨부파일 삭제
    	cnt = postMapper.deletePostAttachList(param);
    	return cnt;
    }
    
    
    /**
     * 포스트 메인 화면 표시
     *  
     * @param input
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)	// CUD 작업시 반드시 추가해야 에러 발생시 롤백 됨
    public int  pickPost(HashMap<String, Object> param) throws Exception {
    	String postType = param.get("postType").toString();
    	int cnt = 0;
    	switch (postType) {
			case "animals":
				cnt = postMapper.updatePostPickAnimals(param);
				break;
			case "arts-entertainment":
				cnt = postMapper.updatePostPickArtEntertainment(param);
				break;
			case "destinations":
				cnt = postMapper.updatePostPickDestinations(param);
				break;
			case "history":
				cnt = postMapper.updatePostPickHistory(param);
				break;
			case "lifeStyle":
				cnt = postMapper.updatePostPickLifeStyle(param);
				break;
			case "nature":
				cnt = postMapper.updatePostPickNature(param);
				break;
			case "people":
				cnt = postMapper.updatePostPickPeople(param);
				break;
		}
    	return cnt;
    }

}
