package com.life.site.web.user.exception;

public class UserAuthException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2036770425841627558L;
    
    public UserAuthException() {
        super();
    }
    
    public UserAuthException(String message){
        super(message);
    }
}
