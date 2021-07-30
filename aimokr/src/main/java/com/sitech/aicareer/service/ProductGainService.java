package com.sitech.aicareer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wrt.aicareer.power.bean.PageResult;
import com.sitech.aicareer.bean.query.ProductGainPageQuery;
import com.sitech.aicareer.mapper.ProductGainMapper;
import com.sitech.aicareer.pojo.ProductGain;
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
public class ProductGainService {

    @Resource
    ProductGainMapper productGainMapper;

    /**
     * 分页查询
     */
    public PageResult<ProductGain> getAllProductGain(ProductGainPageQuery pageQuery) {
        // 分页
        PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.getSortBy() + (pageQuery.getDesc() ? " DESC" : " ASC"));
        // 查询
        List<ProductGain> productGains = productGainMapper.getAllProductGain(pageQuery);

        PageInfo<ProductGain> info = new PageInfo<>(productGains);
        // 解析分页结果
        return new PageResult<ProductGain>(info.getTotal(), productGains);

    }
    public int insertProductGain(ProductGain productGain) {
        User user = RequestHolder.getCurrentUser();
        HttpServletRequest request = RequestHolder.getCurrentRequest();
        productGain.setCreateTime(new Date());
        productGain.setLastUpdateTime(new Date());
        productGain.setOperateIp(request.getRemoteAddr());
        return productGainMapper.insert(productGain);
    }
    public int updateTask(ProductGain productGain) {
        productGain.setLastUpdateTime(new Date());
        return productGainMapper.updateByPrimaryKey(productGain);
    }

    public int deleteTask(Long productGainId) {
        return productGainMapper.deleteByPrimaryKey(productGainId);
    }

}
