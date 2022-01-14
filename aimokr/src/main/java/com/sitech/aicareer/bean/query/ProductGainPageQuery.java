package com.sitech.aicareer.bean.query;

import com.wrt.aicareer.power.bean.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangjn_bj 2020/5/25
 */
@Setter
@Getter
public class ProductGainPageQuery extends PageQuery {
    private String gainName;
    private String gainUrl;
}
