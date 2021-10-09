package com.life.site.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVo {
    @JsonProperty("FILE_GRP_ID")
    private String FILE_GRP_ID;
    @JsonProperty("FILE_UID")
    private String FILE_UID;
    @JsonProperty("FILE_ORIGIN_NM")
    private String FILE_ORIGIN_NM;
    @JsonProperty("FILE_TYPE")
    private String FILE_TYPE;
    @JsonProperty("FILE_PATH")
    private String FILE_PATH;
    @JsonProperty("FILE_NM")
    private String FILE_NM;
    @JsonProperty("FILE_SIZE")
    private String FILE_SIZE;
    @JsonProperty("MENU_CD")
    private String MENU_CD;
    @JsonProperty("DEL_FL")
    private String DEL_FL;
    @JsonProperty("INPUT_USER_ID")
    private String INPUT_USER_ID;
    @JsonProperty("INPUT_DT")
    private String INPUT_DT;
    @JsonProperty("URL_PATH")
    private String URL_PATH;
    @JsonProperty("COMP_CD")
    private String COMP_CD;
    @JsonProperty("REF01")
    private String REF01;
    @JsonProperty("REF02")
    private String REF02;
    @JsonProperty("REF03")
    private String REF03;
    @JsonProperty("REF04")
    private String REF04;
    @JsonProperty("REF05")
    private String REF05;
    @JsonProperty("REF06")
    private String REF06;
    @JsonProperty("REF07")
    private String REF07;
    @JsonProperty("REF08")
    private String REF08;
    @JsonProperty("REF09")
    private String REF09;
    @JsonProperty("REF10")
    private String REF10;
}