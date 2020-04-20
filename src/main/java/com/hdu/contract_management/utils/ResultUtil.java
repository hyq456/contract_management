package com.hdu.contract_management.utils;

public class ResultUtil {
    private String code;
    private String message;
    private Object data;

    public ResultUtil(){

    }

    public ResultUtil(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultUtil result(String code , String message , Object data){
        return new ResultUtil(code , message , data);
    }
    public static ResultUtil success(String message, Object data){
        return new ResultUtil("200", message, data);
    }
    public static ResultUtil error(String message){
        return new ResultUtil("500", message,null);
    }
    public static ResultUtil success(String message){
        return new ResultUtil("200" , message ,null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
