package com.sitech.aicareer.mapper;

import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.ProductGainPageQuery;
import com.sitech.aicareer.pojo.ProductGain;
import com.sitech.aicareer.service.ProductGainService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ProductGainMapperTest extends BaseApplicationTest {
    @Autowired
    ProductGainService productGainService;

    @Test
    public void insertTest(){
        ProductGain productGain = new ProductGain();
        productGain.setProductId(1111L);
        productGain.setGainName("nang");
        productGain.setGainUrl("sdfsf");
        productGainService.insertProductGain(productGain);
    }

    @Test
    public void selectAll(){
        PageResult<ProductGain> allProductGain = productGainService.getAllProductGain(new ProductGainPageQuery());
        System.out.println(allProductGain);
    }
}