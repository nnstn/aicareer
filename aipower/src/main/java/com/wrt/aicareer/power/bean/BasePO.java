package com.wrt.aicareer.power.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author : wangjn_bj
 * @date : 2021/7/5 5:54
 */

public class BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

}
