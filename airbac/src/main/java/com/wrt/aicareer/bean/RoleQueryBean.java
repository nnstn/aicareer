package com.wrt.aicareer.bean;

import com.wrt.aicareer.power.bean.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : wangjn_bj
 * @date : 2021/7/8 22:55
 */
@Setter
@Getter
public class RoleQueryBean extends PageQuery {
    private  String roleName;
    private String createUid;
}
