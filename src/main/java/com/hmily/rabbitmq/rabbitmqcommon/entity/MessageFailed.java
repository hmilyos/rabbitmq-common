package com.hmily.rabbitmq.rabbitmqcommon.entity;

import java.util.Date;

public class MessageFailed {
    private Long failId;

    private Long messageId;

    private String failTitle;

    private String failDesc;

    private Date createTime;

    private Date updateTime;

    public MessageFailed(Long failId, Long messageId, String failTitle, String failDesc, Date createTime, Date updateTime) {
        this.failId = failId;
        this.messageId = messageId;
        this.failTitle = failTitle;
        this.failDesc = failDesc;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public MessageFailed() {
        super();
    }

    public Long getFailId() {
        return failId;
    }

    public void setFailId(Long failId) {
        this.failId = failId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getFailTitle() {
        return failTitle;
    }

    public void setFailTitle(String failTitle) {
        this.failTitle = failTitle == null ? null : failTitle.trim();
    }

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc == null ? null : failDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}