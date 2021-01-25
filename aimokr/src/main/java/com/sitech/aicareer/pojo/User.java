package com.sitech.aicareer.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long leader;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String email;

    private String phone;

    private String salt;

    private Date createTime;

    private Date lastUpdateTime;
    @Column(name = "opeation_ip")
    private String opeationIp;
}
