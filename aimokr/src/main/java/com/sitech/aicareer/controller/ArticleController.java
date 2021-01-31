package com.sitech.aicareer.controller;

import com.sitech.aicareer.bean.JsonData;
import com.sitech.aicareer.bean.PageResult;
import com.sitech.aicareer.bean.query.ArticlePageQuery;
import com.sitech.aicareer.pojo.Article;
import com.sitech.aicareer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    /**
     * 采集帖子信息
     */
    @RequestMapping("/getAll")
    public JsonData getArticles(@RequestBody ArticlePageQuery pageQuery){
        PageResult<Article> articles = articleService.getArticlePageQuery(pageQuery);

        return JsonData.success(articles);
    }
}
