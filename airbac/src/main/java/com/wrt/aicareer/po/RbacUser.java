package com.wrt.aicareer.po;

import com.wrt.aicareer.power.bean.BasePO;
import com.wrt.aicareer.power.constants.ConstantRbac;
import com.wrt.aicareer.web.holder.RequestHolder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 19:49
 */
@Data
@Table(name="rbac_user")
public class RbacUser  extends BasePO {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long userId;
    private String username;
    private String password;
    private String publicKey;
    private String telephone;
    private String email;
    private String rbacUserIcon;
    private String deptId;
    private String ssoCode;
    private Integer status; //状态，0:正常,1：冻结状态，2：删除，3：待审批，4：未通过
    private String lastRoleId;
    private Long lastUpdateTime;
    private String lastUpdateIp;
    private String lastUpdateUid;
    private Long lastLoginTime; // 最后登录时间
    private String lastLoginIp; // 最后登录IP
    private String remark;


    public void update(){
        this.setLastUpdateTime(System.currentTimeMillis());
        this.setLastUpdateIp(RequestHolder.getRemoteIp());
    }
    public void login(){
        this.setLastLoginIp(RequestHolder.getRemoteIp());
        this.setLastLoginTime(System.currentTimeMillis());
        RequestHolder.getCurrentRequest().getSession().setAttribute(ConstantRbac.SESSION_USER,this);
    }
}
