package com.hmily.rabbitmq.rabbitmqcommon.common;

public enum MSGStatusEnum{
    //    	 消息投递状态
    SENDING(0, "投递中"),
    SEND_SUCCESS(1, "投递成功"),
    SEND_FAILURE(2, "投递失败"),
    PROCESSING_IN(3, "处理中"),
    PROCESSING_SUCCESS(4, "处理完成"),
    PROCESSING_FAILED(5, "处理失败");

    private int code;
    private String value;

    MSGStatusEnum(int code,String value){
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }
}
