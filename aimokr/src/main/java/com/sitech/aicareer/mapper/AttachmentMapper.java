package com.sitech.aicareer.mapper;

import com.sitech.aicareer.pojo.Attachment;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface AttachmentMapper extends Mapper<Attachment> {

    @Delete("delete from attachment where attachment.article_id = #{articleId}")
    int deleteByArticle(Long articleId);
}
