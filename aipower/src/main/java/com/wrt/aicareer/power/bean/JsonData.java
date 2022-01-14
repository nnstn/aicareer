package com.wrt.aicareer.power.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjn_bj
 */
@Data
public class JsonData {

    private Boolean flag;
    private String message;
    private Object data;

    private JsonData(Boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public static JsonData success(Object object) {
        return new JsonData(true, "成功", object);
    }
    public static JsonData success(String msg,Object object) {
        return new JsonData(true, msg, object);
    }

    public static JsonData fail(String message) {
        return new JsonData(false, message, null);
    }

    public static JsonData fail(String message, Object obj) {
        return new JsonData(false, message, obj);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("flag", flag);
        result.put("message", message);
        result.put("data", data);
        return result;
    }
}
