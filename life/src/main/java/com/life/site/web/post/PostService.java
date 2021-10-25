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
import com.life.site.model.PostVo;
import com.life.site.model.PostVo.Post;
import com.life.site.model.PostVo.Post.PostBuilder;
import com.life.site.model.PostVo.PostList;
import com.life.site.model.PostVo.PostsAttach;
import com.life.site.model.UserVo;
import com.life.site.web.util.StringUtil;
import com.life.site.web.util.session.SessionManager;

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
@Service("PostService")
public class PostService {
	@Autowired
	public PostMapper postMapper;

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
		}

		List.forEach(post -> {
								 builder.MAGAZINE_ID(post.get("MAGAZINE_ID").toString())
										.POST_TYPE_ID(post.get("POST_TYPE_ID").toString())
										.CATEGORY_ID(post.get("CATEGORY_ID").toString())
										.TITLE(post.get("TITLE").toString())
										.CONTENT(post.get("CONTENT").toString())
										.REG_USER_ID(post.get("REG_USER_ID").toString())
										.REG_DT(post.get("REG_DT").toString())
										.MOD_USER_ID(post.get("MOD_USER_ID").toString())
										.MOD_DT(post.get("MOD_DT").toString())
										.TOTAL_COUNT(post.get("TOTAL_COUNT").toString());
								 
								 if(!morePostYn.equals("Y")) {
									// 첨부파일 조회
									  List<PostsAttach> postAttaches = new ArrayList<PostsAttach>();
									  
									  param.put("MAGAZINE_ID", post.get("MAGAZINE_ID").toString());
									  param.put("CATEGORY_ID", post.get("CATEGORY_ID").toString());
									  List<Map<String, Object>> postAttach =
											  postMapper.selectPostAttachList(param);
									 
									   postAttach.forEach(attach ->{ postAttaches.add(PostVo.ofPostAttach(attach));
									  });
									  
									 builder.postAttaches(postAttaches);
								 }
								 
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
		
		switch (postType) {
		case "animals":
			info = postMapper.selectPostAnimalInfo(param);
			break;
		}
			 builder.MAGAZINE_ID(info.get("MAGAZINE_ID").toString())
					.POST_TYPE_ID(info.get("POST_TYPE_ID").toString())
					.CATEGORY_ID(info.get("CATEGORY_ID").toString())
					.TITLE(info.get("TITLE").toString())
					.CONTENT(info.get("CONTENT").toString())
					.REG_USER_ID(info.get("REG_USER_ID").toString())
					.REG_DT(info.get("REG_DT").toString())
					.MOD_USER_ID(info.get("MOD_USER_ID").toString())
					.MOD_DT(info.get("MOD_DT").toString());

		// 첨부파일 조회
		  List<PostsAttach> postAttaches = new ArrayList<PostsAttach>();
		  
		  param.put("MAGAZINE_ID", info.get("MAGAZINE_ID").toString());
		  param.put("CATEGORY_ID", info.get("CATEGORY_ID").toString());
		  List<Map<String, Object>> postAttach =postMapper.selectPostAttachList(param);
		 
		   postAttach.forEach(attach ->{ postAttaches.add(PostVo.ofPostAttach(attach));
		  });
		   
		   builder.postAttaches(postAttaches);
		
		return builder.build();
	}

}
