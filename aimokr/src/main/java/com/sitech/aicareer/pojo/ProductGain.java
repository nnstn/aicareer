package com.sitech.aicareer.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ProductGain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String gainName;
    private String gainUrl;
    private Date createTime;
    private Date lastUpdateTime;
    private String operateIp;

}
