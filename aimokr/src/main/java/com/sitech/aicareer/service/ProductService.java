package com.sitech.aicareer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.ProductPageQuery;
import com.sitech.aicareer.mapper.ProductMapper;
import com.sitech.aicareer.pojo.Product;
import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author wangjn_bj
 */
@Slf4j
@Service
public class ProductService {

    @Resource
    ProductMapper productMapper;

    /**
     * 分页查询
     */
    public PageResult<Product> getAllProductGain(ProductPageQuery pageQuery) {
        // 分页
        PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.getSortBy() + (pageQuery.getDesc() ? " DESC" : " ASC"));
        // 查询
        List<Product> productGains = productMapper.getAllProduct(pageQuery);

        PageInfo<Product> info = new PageInfo<Product>(productGains);
        // 解析分页结果
        return new PageResult<Product>(info.getTotal(), productGains);

    }
    public int insertProductGain(Product product) {
        User user = RequestHolder.getCurrentUser();
        HttpServletRequest request = RequestHolder.getCurrentRequest();
        product.setCreateTime(new Date());
        product.setLastUpdateTime(new Date());
        product.setOperateIp(request.getRemoteAddr());
        return productMapper.insert(product);
    }
    public int updateTask(Product product) {
        product.setLastUpdateTime(new Date());
        return productMapper.updateByPrimaryKey(product);
    }

    public int deleteTask(Long productId) {
        return productMapper.deleteByPrimaryKey(productId);
    }

}
