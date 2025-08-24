package com.bo.shirodemo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author bo
 * @DATE 2020/8/29
 **/

@Data
public class Result<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long count;

    public Result(){}

}
