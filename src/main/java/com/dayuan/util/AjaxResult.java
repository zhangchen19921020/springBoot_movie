package com.dayuan.util;

import java.io.Serializable;

/**
 * AJAX统一返回对象
 */
public class AjaxResult implements Serializable {
    private Integer errorCode;//如果接口错误，则有值
    private String errorMsg;//错误消息
    private Object data;  //如果接口正常，则data存返回的数据，如果接口错误，则data为空
    private Boolean success;//true：成功 代表本次请求是否成功

    public AjaxResult() {

    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    /************************简单封装*********************/
    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    public static AjaxResult success(Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult fail() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        return ajaxResult;
    }


    public static AjaxResult fail(Integer errorCode, String errorMsg) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        ajaxResult.setErrorCode(errorCode);
        ajaxResult.setErrorMsg(errorMsg);
        return ajaxResult;
    }

    public static AjaxResult fail(String errorMsg) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        ajaxResult.setErrorMsg(errorMsg);
        return ajaxResult;
    }
    /************************简单封装*********************/
}
