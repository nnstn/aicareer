package com.sitech.aicareer.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.ArticlePageQuery;
import com.sitech.aicareer.mapper.ArticleMapper;
import com.sitech.aicareer.pojo.Article;
import com.sitech.aicareer.pojo.Attachment;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author wangjn_bj
 */
@Slf4j
@Service
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    AttchmentService attchmentService;

    /**
     * 分页查询
     */
    public PageResult<Article> getArticlePageQuery(ArticlePageQuery pageQuery) {
        if(pageQuery.getSortBy()==null||pageQuery.getSortBy().length()==0){
            pageQuery.setSortBy("last_update_time");
        }
        // 分页
        PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize(), pageQuery.getSortBy() + (pageQuery.getDesc() ? " DESC" : " ASC"));
        // 查询
        List<Article> articles = articleMapper.getAllArticle(pageQuery);

        PageInfo<Article> info = new PageInfo<>(articles);
        // 解析分页结果
        return new PageResult<Article>(info.getTotal(), articles);

    }

    @Transactional(rollbackFor = Exception.class)
    public int insertArticle(Article article) {
        HttpServletRequest request = RequestHolder.getCurrentRequest();
        if(article.getAttachment().size()>0){
            for (Attachment attachment:article.getAttachment()){
                attachment.setArticleId(article.getArticleId());
                attchmentService.insertAttachment(attachment);
            }
        }
        article.setCreateTime(new Date());
        article.setLastUpdateTime(new Date());
        article.setOperateIp(request.getRemoteAddr());
        return articleMapper.insert(article);
    }

    public int updateArticle(Article article) {
        article.setLastUpdateTime(new Date());
        return articleMapper.updateByPrimaryKey(article);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteArticle(Long articleId) {
        //删除关联附件
        int i = attchmentService.deleteAttachmentByArticle(articleId);
        //删除数据
        return articleMapper.deleteByPrimaryKey(articleId);
    }
}
