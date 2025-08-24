package com.bo.shirodemo.utils;

import java.io.Serializable;

/**
 * @Author bo
 * @DATE 2020/8/29
 **/


public class ReturnResult implements Serializable {

    public ReturnResult(){}

    public static Result<Object> success(Object object){
        Result<Object> result = new Result<>();
        result.setCode(Constant.SUCCESS_CODE);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    public static Result<?> success(){
        return new ReturnResult().success(null);
    }

    public static Result<Object> success(Object object, Long count){
        Result<Object> result = new Result<>();
        result.setCode(Constant.SUCCESS_CODE);
        result.setMsg("success");
        result.setData(object);
        result.setCount(count);
        return result;
    }

    public static Result<?> fail(Integer code, String message){
        Result<?> result = new Result<>();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

}
