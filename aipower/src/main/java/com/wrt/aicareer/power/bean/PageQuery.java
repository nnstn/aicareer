package com.wrt.aicareer.power.bean;

import lombok.Data;

import javax.validation.constraints.Min;
import java.sql.Date;

@Data
public class PageQuery {

    private String key;		//关键字查询

    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    private Boolean desc = true;	//排序方式
    private String sortBy = "last_update_time"; // 需要排序的字段
    private int status;    //节点状态
    private Date startTime;
    private Date endTime;

}
