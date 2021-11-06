package com.life.site.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVo {
	@JsonProperty("ATTACH_ID")
	private int ATTACH_ID;
    @JsonProperty("FILE_STORE_NM")
    private String FILE_STORE_NM;
    @JsonProperty("FILE_ORIGIN_NM")
    private String FILE_ORIGIN_NM;
    @JsonProperty("ATTACH_TYPE")
    private String ATTACH_TYPE;
    @JsonProperty("ATTACH_DIR")
    private String ATTACH_DIR;
    @JsonProperty("FILE_SIZE")
    private String FILE_SIZE;
    @JsonProperty("DEL_FL")
    private String DEL_FL;
    @JsonProperty("REG_USER_ID")
    private String REG_USER_ID;
    @JsonProperty("REG_DT")
    private String REG_DT;
    @JsonProperty("MOD_USER_ID")
    private String MOD_USER_ID;
    @JsonProperty("MOD_DT")
    private String MOD_DT;
    @JsonProperty("URL_PATH")
    private String URL_PATH;
}