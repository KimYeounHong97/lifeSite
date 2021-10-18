package com.life.site.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.life.site.config.annotation.ApiPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 세션에 저장할 사용자 정보 객체
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @JsonNaming(UpperSnakeCaseStrategy.class)
public class UserVo implements Serializable {
    
    private static final long serialVersionUID = 8499870567219168809L;

    @JsonProperty("USER_ID")
    String USER_ID;

    @JsonProperty("PASSWD")
    String PASSWD;

    @JsonProperty("FAVORITE_CD")
    String FAVORITE_CD;
    
    @JsonProperty("FAVORITE_NM")
    String FAVORITE_NM;

    @JsonProperty("PHONE")
    String PHONE;

    @JsonProperty("EMAIL")
    String EMAIL;

    @JsonProperty("ADDRESS")
    String ADDRESS;
    
    @JsonProperty("USER_GENDER")
    String USER_GENDER;

    /* 권한그룹 CD */
    @JsonProperty("USER_GRADE")
    String USER_GRADE;

    @JsonProperty("USER_GRADE_NM")
    String USER_GRADE_NM;

    /* API 권한 구분 */
    @JsonProperty("API_PERMISSION_ROLE")
    ApiPermission.Role API_PERMISSION_ROLE;

    
    public boolean matchPassword(String passwd) {
        if(this.PASSWD == null) return false;
        return this.PASSWD.equals(passwd);
    }
}