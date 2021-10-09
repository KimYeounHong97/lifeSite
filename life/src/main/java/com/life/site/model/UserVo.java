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

    @JsonProperty("USER_NM")
    String USER_NM;

    @JsonProperty("EMAIL")
    String EMAIL;

    @JsonProperty("COMP_CD")
    String COMP_CD;

    @JsonProperty("COMP_NM")
    String COMP_NM;
    
    @JsonProperty("ORG_CD")
    String ORG_CD;

    @JsonProperty("ORG_NM")
    String ORG_NM;

    @JsonProperty("DEPT_CD")
    String DEPT_CD;

    @JsonProperty("DEPT_NM")
    String DEPT_NM;
    
    @JsonProperty("GRADE_CD")
    String GRADE_CD;
    
    @JsonProperty("GRADE_NM")
    String GRADE_NM;

    @JsonProperty("LOCK_FL")
    String LOCK_FL;

    /* 접속 구분 (LOGIN, SSO) */
    @JsonProperty("ACC_GB")
    String ACC_GB;

    /* 권한그룹 CD */
    @JsonProperty("AUTH_CD")
    String AUTH_CD;
    
    /* 관리자 구분 (1:시스템관리자(XX_0001), 2:운영관리자(XX_0002)) => DB ADMIN_GUBUN_FN('아이디') 참조 */
    @JsonProperty("ADMIN_GUBUN")
    String ADMIN_GUBUN;

    /* 계정 구분 (0:임직원, 1:회원가입,2:비회원) */
    @JsonProperty("USER_GB")
    String USER_GB;

    /* API 권한 구분 */
    @JsonProperty("API_PERMISSION_ROLE")
    ApiPermission.Role API_PERMISSION_ROLE;

    List<Map<String, Object>> favoritesMenuList;
    
    public boolean matchPassword(String passwd) {
        if(this.PASSWD == null) return false;
        return this.PASSWD.equals(passwd);
    }
}