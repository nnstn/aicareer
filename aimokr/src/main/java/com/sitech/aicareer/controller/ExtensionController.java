package com.sitech.aicareer.controller;

import com.sitech.aicareer.bean.JsonData;
import com.sitech.aicareer.pojo.Article;
import com.sitech.aicareer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ExtensionController {
    @Autowired
    ArticleService articleService;

    /**
     * 采集帖子信息
     */
    @RequestMapping("/collect")
    public JsonData postArticle(@RequestBody Article article){

        articleService.insertArticle(article);

        return JsonData.success("添加帖子成功");
    }

    @PostMapping("delete/{id}")
    public JsonData deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
        return JsonData.success("删除成功");
    }
}
