package com.tuke.fei.kpi.isvote.modules.auth.security.token;


public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
