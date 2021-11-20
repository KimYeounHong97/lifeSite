package com.life.site.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVo {
	@JsonProperty("POST_ID")
	private int POST_ID;
	@JsonProperty("POST_TYPE")
	private String POST_TYPE;
	@JsonProperty("ATTACH_ID")
	private int ATTACH_ID;
    @JsonProperty("FILE_STORE_NM")
    private String FILE_STORE_NM;
    @JsonProperty("FILE_ORIGIN_NM")
    private String FILE_ORIGIN_NM;
    @JsonProperty("ATTACH_TYPE")
    private String ATTACH_TYPE;
    @JsonProperty("URL_PATH")
    private String URL_PATH;
    @JsonProperty("ATTACH_DIR")
    private String ATTACH_DIR;
    @JsonProperty("FILE_SIZE")
    private String FILE_SIZE;
    @JsonProperty("DEL_FL")
    private String DEL_FL;
    @JsonProperty("TITLE_FL")
    private String TITLE_FL;
    @JsonProperty("REFFER_FLG")
    private String REFFER_FLG;
    @JsonProperty("REG_USER_ID")
    private String REG_USER_ID;
    @JsonProperty("REG_DT")
    private String REG_DT;
    @JsonProperty("MOD_USER_ID")
    private String MOD_USER_ID;
    @JsonProperty("MOD_DT")
    private String MOD_DT;
}