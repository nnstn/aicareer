package com.wrt.aicareer.po;

import com.wrt.aicareer.power.bean.BasePO;
import com.wrt.aicareer.web.holder.RequestHolder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 19:49
 */
@Data
public class RbacMenu extends BasePO {

    private static final long serialVersionUID = 1L;
    /**菜单id**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long menuId;

    private String menuName;
    private String menuUrl;
    private String menuIndex;
    /** 0:激活状态 1:无效状态**/
    private Integer status;
    private String icon;
    private String pid;
    private Long createUid;
    private Long createTime;
    private Long lastUpdateTime;
    private String lastUpdateIp;
    private String lastUpdateUid;
    private String remark;

    public void update(){
        this.setLastUpdateTime(System.currentTimeMillis());
        this.setLastUpdateIp(RequestHolder.getRemoteIp());
    }
    public void create(){
        this.setCreateTime(System.currentTimeMillis());
        this.setCreateUid(RequestHolder.getUser().getUserId());
        this.setLastUpdateTime(System.currentTimeMillis());
        this.setLastUpdateIp(RequestHolder.getRemoteIp());
    }
}
