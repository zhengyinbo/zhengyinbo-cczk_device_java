package com.bo.shirodemo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class LoginResult {
    public static Object success(Object token){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "登陆成功");
        jsonObject.put("data", token);
        return jsonObject;
    }

    public static Object fail(Integer status, String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    public static Object successLogout(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "10002");
        return jsonObject;
    }

    // 未登录
    public static Object failLogin(Integer status, String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

}
