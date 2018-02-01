package org.im.common.enums;

/**
 *
 */
public enum XMPPErrorType {

    /**
     * 不应该重试该动作，因为它总会失败
     */
    CANCEL("cancel"),

    /**
     * 警告
     */
    CONTINUE("continue"),

    /**
     * 发送的数据需要一些修改
     */
    MODIFY("modify"),

    /**
     * 通知实体在以某种方式进行身份验证之后重试该动作
     */
    AUTH("auth"),

    /**
     * 报告服务器临时遇到问题
     */
    WAIT("wait"),

    ;
    String type;

    XMPPErrorType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
