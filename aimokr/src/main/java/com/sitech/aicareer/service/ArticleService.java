package com.sitech.aicareer.service;

import com.sitech.aicareer.mapper.ArticleMapper;
import com.sitech.aicareer.mapper.AttachmentMapper;
import com.sitech.aicareer.pojo.Article;
import com.sitech.aicareer.pojo.Attachment;
import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @Transactional
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
    public int updateTask(Article article) {
        article.setLastUpdateTime(new Date());
        return articleMapper.updateByPrimaryKey(article);
    }

    public int deleteArticle(Long productGainId) {
        return articleMapper.deleteByPrimaryKey(productGainId);
    }

}
