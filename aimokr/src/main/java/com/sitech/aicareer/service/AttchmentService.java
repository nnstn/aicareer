package com.sitech.aicareer.service;

import com.sitech.aicareer.mapper.AttachmentMapper;
import com.sitech.aicareer.pojo.Attachment;
import com.sitech.aicareer.web.handler.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wangjn_bj
 */
@Slf4j
@Service
public class AttchmentService {

    @Resource
    AttachmentMapper attachmentMapper;

    public int insertAttachment(Attachment attachment) {
        HttpServletRequest request = RequestHolder.getCurrentRequest();
        attachment.setCreateTime(new Date());
        attachment.setLastUpdateTime(new Date());
        attachment.setOperateIp(request.getRemoteAddr());
        return attachmentMapper.insert(attachment);
    }
    public int updateAttachment(Attachment attachment) {
        attachment.setLastUpdateTime(new Date());
        return attachmentMapper.updateByPrimaryKey(attachment);
    }
    public int deleteAttachmentByArticle(Long articleId) {
        return attachmentMapper.deleteByArticle(articleId);
    }
    public int deleteAttachment(Long attachmentId) {
        return attachmentMapper.deleteByPrimaryKey(attachmentId);
    }

}
