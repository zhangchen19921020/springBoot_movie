package com.dayuan.exception;

/**
 * 自定义异常  转账异常
 */
public class BizException extends Exception {

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }
}
