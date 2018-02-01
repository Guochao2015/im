package org.im.common.enums;

/**
 * <presence type></presence>
 */
public enum  NodePresenceAttrType {
    /**
     * 在线
     */
    AVAILABLE("available"),
    /**
     * 离线
     */
    UNAVAILABLE("unavailable"),
    /**
     * 出席
     */
    SUBSCRIBE("subscribe"),
    /**
     * 取消出席
     */
    UNSUBSCRIBE("unsubscribe"),
    /**
     * 订阅
     */
    SUBSCRIBED("subscribed"),
    /**
     * 取消订阅
     */
    UNSUBSCRIBED("unsubscribed"),
;
    String type;
    NodePresenceAttrType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static NodePresenceAttrType valuesOf(String type){
        for (NodePresenceAttrType type1 : values()){
            if (type1.getType().equals(type)){
                return type1;
            }
        }
        return null;
    }
}
