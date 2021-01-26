package com.sitech.aicareer.mapper;

import com.sitech.aicareer.bean.query.ProductGainPageQuery;
import com.sitech.aicareer.bean.query.TaskPageQuery;
import com.sitech.aicareer.pojo.ProductGain;
import com.sitech.aicareer.pojo.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ProductGainMapper extends Mapper<ProductGain> {

    @SelectProvider(type = DynamicProvider.class, method = "getAllProductGainByCondition")
    List<ProductGain> getAllProductGain(ProductGainPageQuery pageQuery);

    class DynamicProvider{

        public String getAllProductGainByCondition(ProductGainPageQuery pageQuery){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from product_gain where 1=1");
            if(StringUtils.isNoneBlank(pageQuery.getGainName())){ //任务模糊搜索
                sqlBuilder.append(" and  gain_name like CONCAT('%','"+pageQuery.getGainName()+"','%')");
            }
            if(StringUtils.isNoneBlank(pageQuery.getGainUrl())){ //任务模糊搜索
                sqlBuilder.append(" and  gain_name like CONCAT('%','"+pageQuery.getGainUrl()+"','%')");
            }
            return sqlBuilder.toString();
        }
    }
}
