package com.sitech.aicareer.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dispatcher; // 派发任务用户ID
    private String tasker;     // 执行任务用户ID
    private String taskName;
    private Integer taskType;  //任务类型 1 开发编码 2 会议讨论 3 设计相关 4 文档编写 5 bug处处理 6 环境部署
    private Integer noticeType;// 通知类型 1 不提醒 2 邮件提醒 3 易信提醒
    private Boolean topping;  // 是否置顶
    private Date startDate;
    private Date endDate;
    private Integer state; // 0 新增 1 处理中 2 完成 3 挂起暂停

    private Date createTime;
    private Date lastUpdateTime;
    private String operateIp;

}
