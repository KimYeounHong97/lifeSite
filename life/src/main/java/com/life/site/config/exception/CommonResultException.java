package com.life.site.config.exception;


import com.life.site.config.param.CommonResult;

import lombok.Getter;

@Getter
public class CommonResultException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6267182274009726476L;
	private boolean status;
	private String message;
	private Object data;

	/**
     * 
     */
    //private static final long serialVersionUID
    
    public CommonResultException() {
        super();
    }
    
    public CommonResultException(boolean status,String message){
        super(message);
        this.status = status;
        this.message = message;
        this.data = null;
    }
    
    public CommonResultException(boolean status,String message, Object data){
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public CommonResultException(CommonResult commonResult){
    	super(commonResult.getMessage());
        this.status = commonResult.getStatus();
        this.message = commonResult.getMessage();
        this.data = commonResult.getData();
    }
    
}
