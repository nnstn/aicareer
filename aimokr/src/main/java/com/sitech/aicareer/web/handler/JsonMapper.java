package com.sitech.aicareer.web.handler;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapper {
	static Logger log = LoggerFactory.getLogger(JsonMapper.class);
    
    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }
        try {
        	
            return src instanceof String ? (String) src : JSON.toJSONString(src);
        } catch (Exception e) {
            log.warn("parse object to String exception, error:{}", e);
            return null;
        }
    }

    public static <T> T string2Obj(String src, Class<T> typeReference) {
        if (src == null || typeReference == null) {
            return null;
        }
        try {
        	return JSON.parseObject(src, typeReference);
        } catch (Exception e) {
            log.warn("parse String to Object exception, String:{}, TypeReference<T>:{}, error:{}", src, typeReference.getClass(), e);
            return null;
        }
    }
}
