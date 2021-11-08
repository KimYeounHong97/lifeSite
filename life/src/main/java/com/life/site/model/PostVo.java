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
    	@JsonProperty("POST_ID")
        String POST_ID;
        
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
        
        List<FileVo> postAttaches;
    }

    public  static FileVo ofPostAttach(Map<String, Object> attach) {
    	FileVo obj =	FileVo.builder()
				.ATTACH_ID(Integer.parseInt(attach.get("ATTACH_ID").toString()))
				.POST_ID(Integer.parseInt(attach.get("POST_ID").toString()))
				.ATTACH_TYPE(attach.get("ATTACH_TYPE").toString())
				.URL_PATH(attach.get("URL_PATH").toString())
				.FILE_STORE_NM(attach.get("FILE_STORE_NM").toString())
				.FILE_ORIGIN_NM(attach.get("FILE_ORIGIN_NM").toString())
				.TITLE_FL(attach.get("TITLE_YN").toString())
				.DEL_FL(attach.get("DEL_YN").toString())
				.REG_USER_ID(attach.get("REG_USER_ID").toString())
				.REG_DT(attach.get("REG_DT").toString())
				.MOD_USER_ID(attach.get("MOD_USER_ID").toString())
				.MOD_DT(attach.get("MOD_DT").toString())
				.build();
		return obj;
	}
    
 
}