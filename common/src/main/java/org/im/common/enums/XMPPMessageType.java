package org.im.common.enums;

/**
 * 默认是NORMAL
 */
public enum XMPPMessageType {
    /**
     * 单个的消息
     */
    NORMAL("normal"),
    /**
     * 两个实体间的实时对话
     */
    CHAT("chat"),
    /**
     * 多用户聊天室
     */
    GROUPCHAT("groupchat"),
    /**
     * 用于发送警示和通告
     */
    HEADLINE("headline"),
    /**
     * 发生错误
     */
    ERROR("error"),
    ;

    private String type;
    XMPPMessageType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public static XMPPMessageType valuesOf(String type){
        for (XMPPMessageType type1 : values()){
            if (type1.getType().equals(type)){
                return type1;
            }
        }
        return null;
    }
}
