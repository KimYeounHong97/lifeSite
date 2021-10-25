package com.life.site.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.life.site.config.annotation.ApiPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 포스트 객체
 */

public class PostVo implements Serializable {
    private static final long serialVersionUID = 8499870567219168809L;
    
    @Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PostList{
		private List<Post> posts;
	}
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post{
    	@JsonProperty("POST_TYPE_ID")
        String POST_TYPE_ID;

        @JsonProperty("MAGAZINE_ID")
        String MAGAZINE_ID;

        @JsonProperty("CATEGORY_ID")
        String CATEGORY_ID;
        
        @JsonProperty("TITLE")
        String TITLE;

        @JsonProperty("CONTENT")
        String CONTENT;

        @JsonProperty("REG_USER_ID")
        String REG_USER_ID;

        @JsonProperty("REG_DT")
        String REG_DT;
        
        @JsonProperty("MOD_USER_ID")
        String MOD_USER_ID;

        @JsonProperty("MOD_DT")
        String MOD_DT;
        
        @JsonProperty("TOTAL_COUNT")
        String TOTAL_COUNT;
        
        List<PostsAttach> postAttaches;
    }

    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostsAttach{
    	 @JsonProperty("ATTACH_ID")
    	 String ATTACH_ID;
    	 
    	 @JsonProperty("MAGAZINE_ID")
    	 String MAGAZINE_ID;
    	 
    	 @JsonProperty("CATEGORY_ID")
    	 String CATEGORY_ID;
    	 
    	 @JsonProperty("ATTACH_TYPE")
    	 String ATTACH_TYPE;
    	 
    	 @JsonProperty("DEL_YN")
    	 String DEL_YN;
    	 
    	 @JsonProperty("REG_USER_ID")
    	 String REG_USER_ID;
    	 
    	 @JsonProperty("REG_DT")
    	 String REG_DT;
    	 
    	 @JsonProperty("MOD_USER_ID")
    	 String MOD_USER_ID;
    	 
    	 @JsonProperty("MOD_DT")
    	 String MOD_DT;
    }
    
    public  static PostsAttach ofPostAttach(Map<String, Object> attach) {
    	PostsAttach obj =	PostsAttach.builder()
				.ATTACH_ID(attach.get("ATTACH_ID").toString())
				.MAGAZINE_ID(attach.get("MAGAZINE_ID").toString())
				.CATEGORY_ID(attach.get("CATEGORY_ID").toString())
				.ATTACH_TYPE(attach.get("ATTACH_TYPE").toString())
				.DEL_YN(attach.get("DEL_YN").toString())
				.REG_USER_ID(attach.get("REG_USER_ID").toString())
				.REG_DT(attach.get("REG_DT").toString())
				.MOD_USER_ID(attach.get("MOD_USER_ID").toString())
				.MOD_DT(attach.get("MOD_DT").toString())
				.build();
		return obj;
	}
    
 
}