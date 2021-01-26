package com.sitech.aicareer.bean.query;

import com.sitech.aicareer.bean.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangjn_bj 2020/5/25
 */
@Setter
@Getter
public class ProductPageQuery extends PageQuery {
    private String productName;
    private String productUrl;
}
