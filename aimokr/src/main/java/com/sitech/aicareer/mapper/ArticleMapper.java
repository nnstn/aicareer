package com.sitech.aicareer.mapper;

import com.sitech.aicareer.bean.query.ArticlePageQuery;
import com.sitech.aicareer.pojo.Article;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ArticleMapper extends Mapper<Article> {

    @SelectProvider(type = DynamicProvider.class, method = "getAllArticleByCondition")
    List<Article> getAllArticle(ArticlePageQuery pageQuery);

    class DynamicProvider{

        public String getAllArticleByCondition(ArticlePageQuery pageQuery){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from article where 1=1");
            if(StringUtils.isNoneBlank(pageQuery.getArticleTitle())){ //任务模糊搜索
                sqlBuilder.append(" and  article_title like CONCAT('%','"+pageQuery.getArticleTitle()+"','%')");
            }
            if(StringUtils.isNoneBlank(pageQuery.getAuthor())){ //任务模糊搜索
                sqlBuilder.append(" and  author like CONCAT('%','"+pageQuery.getAuthor()+"','%')");
            }
            return sqlBuilder.toString();
        }
    }
}
