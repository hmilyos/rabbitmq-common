package com.hmily.rabbitmq.rabbitmqcommon.entity;

import java.util.Date;

public class Message {
    private Long messageId;

    private Integer type;

    private String message;

    private Integer tryCount;

    private Integer status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

    public Message(Long messageId, Integer type, String message, Integer tryCount, Integer status, Date nextRetry, Date createTime, Date updateTime) {
        this.messageId = messageId;
        this.type = type;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Message() {
        super();
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
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