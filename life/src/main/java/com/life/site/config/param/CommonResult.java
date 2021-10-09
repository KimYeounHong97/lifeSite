package com.life.site.config.param;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean status = true;
    private String message = "";
    private int totalItems = 0;
    private Object data;
    
    /** constructor */
    public CommonResult() { }
    public CommonResult(Object data) {
        this.data = data;
    }
    public CommonResult(boolean status) {
        this.status = status;
    }
    @Builder
    public CommonResult(Object data,boolean status) {
        this.data = data; 
        this.status = status;
    }
    @Builder
    public CommonResult(Object data,boolean status,String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
    
    /**
     * CommonResult 반환
     */
    public static CommonResult ofSuccess() {
        return new CommonResult(true);
    }
    public static CommonResult ofSuccess(Object data) {
        return new CommonResult(data);
    }
    public static CommonResult ofSuccess(String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setMessage(message);
        commonResult.setStatus(true);
        return commonResult;
    }
    public static CommonResult ofSuccess(Object data, String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setData(data);
        commonResult.setMessage(message);
        commonResult.setStatus(true);
        return commonResult;
    }
    public static CommonResult ofFail(Object data,String message) {
        return new CommonResult(data,false,message);
    }
    public static CommonResult ofFail(String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setMessage(message);
        commonResult.setStatus(false);
        return commonResult;
    }
    
    /**
     * User Define Getter
     */
    public boolean getStatus() {
        return this.status;
    }
    /**
     * User Define Setter
     */
    public void setStatus(boolean status) {
        this.status  =  status;
    }
    public void setData(Object data) {
        this.data = data;
        //list 형이면 size를 자동으로 넣어줌 
        if(data instanceof List) {
            this.setTotalItems(((List<?>)data).size());
        } 
    }
    public void setData(Object data, boolean status) {
        this.setData(data);
        this.setStatus(status);
    }
    public void setData(boolean status, String message) {
        this.setStatus(status);
        this.setMessage("");
    }
    public void setData(Object data, boolean status, String message) {
        this.setData(data);
        this.setStatus(status);
        this.setMessage(message);
    }
    public CommonResult setSuccess() {
        this.status = true;
        this.message = "정상처리 되었습니다.";
        return this;
    }

    // Message 상수
    public static final String MESSAGE_DB_DUPLICATE = "KEY_DUPLICATE";
    public static final String MESSAGE_DB_INSERT_FAIL = "INSERT_FAIL";
    public static final String MESSAGE_DB_UPDATE_FAIL = "UPDATE_FAIL";
    public static final String MESSAGE_DB_RESULT_EMPTY = "SELECT_RESULT_EMPTY";
    public static final String MESSAGE_DB_RESULT_MANY = "SELECT_RESULT_TOO_MANY";
    public static final String MESSAGE_DB_RESULT_ONLY_ONE = "SELECT_RESULT_ONLY_ONE";
    public static final String MESSAGE_DB_UPDATE_NOTHING = "UPDATE_NOTHING";

    public static final String MESSAGE_DB_INSERT_RESULT_EMPTY = "INSERT_RESULT_EMPTY";
    public static final String MESSAGE_DB_INSERT_RESULT_MANY = "INSERT_RESULT_MANY";

    // DB 칼럼 중복
    public static final String MESSAGE_DB_MAIL_DUPLICATE = "MAIL_DUPLICATE";
    public static final String MESSAGE_DB_NICKNAME_DUPLICATE = "NICKNAME_DUPLICATE";

    public static final String MESSAGE_PARAM_EMPTY="PARAMETER_EMPTY";
    public static final String MESSAGE_SESSION_EMPTY ="SESSION_EMPTY";

    public static final String UNKNOWN_DAO_EXCEPTION = "UNKNOWN_DAO_EXCEPTION";
    public static final String UNKNOWN_CONTROLLER_EXCEPTION = "UNKNOWN_CONTROLLER_EXCEPTION";
}
