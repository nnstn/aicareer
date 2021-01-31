package com.sitech.aicareer.mapper;

import com.sitech.aicareer.bean.query.ProductPageQuery;
import com.sitech.aicareer.pojo.Product;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ProductMapper extends Mapper<Product> {

    @SelectProvider(type = DynamicTaskProvider.class, method = "getAllTaskByCondition")
    List<Product> getAllProduct(ProductPageQuery pageQuery);

    class DynamicTaskProvider{
        public String getAllTaskByCondition(ProductPageQuery pageQuery){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from product where 1=1");
            if(StringUtils.isNoneBlank(pageQuery.getProductName())){
                sqlBuilder.append(" and  product_name like CONCAT('%','"+pageQuery.getProductName()+"','%')");
            }
            if(StringUtils.isNoneBlank(pageQuery.getProductUrl())){
                sqlBuilder.append(" and  product_url like CONCAT('%','"+pageQuery.getProductUrl()+"','%')");
            }
            return sqlBuilder.toString();
        }
    }
}
