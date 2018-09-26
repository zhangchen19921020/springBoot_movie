package com.dayuan.exception;

/**
 * 自定义异常  用户身份认证相关
 */
public class AuthException extends Exception {

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
