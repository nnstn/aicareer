package com.sitech.aicareer.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachId;
    private Long articleId;
    private String attachName;
    private String attachUrl;
    private Date createTime;
    private Date lastUpdateTime;
    private String operateIp;
}