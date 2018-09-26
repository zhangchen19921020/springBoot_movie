package com.dayuan.exception;

/**
 * 自定义异常  用户身份认证相关
 */
public class ParamsValidateException extends Exception {

    public ParamsValidateException() {
    }

    public ParamsValidateException(String message) {
        super(message);
    }
}
