package com.life.site.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo implements Serializable{
    private static final long serialVersionUID = -5139862155221558500L;
    
    private String userId;
    private String passwd;
    private String rememberId;
    private String message;
    private boolean swithOrg = false;
    
    @JsonProperty("ORG_CD")
    private String ORG_CD;
}
