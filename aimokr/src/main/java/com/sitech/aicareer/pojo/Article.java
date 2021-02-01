package com.sitech.aicareer.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author wangjn_bj
 */
@Data
public class Article {
    @Id
    private Long articleId;
    private String author;
    private String articleUrl;
    private String articleTitle;

    private String communityName;
    private String communityUrl;
    private String teamName;
    private String teamUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date publishDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date updateDate;

    private Date createTime;
    private Date lastUpdateTime;
    private String operateIp;
    @Transient
    private List<Attachment> attachment;
}