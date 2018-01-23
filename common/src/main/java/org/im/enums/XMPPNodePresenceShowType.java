package org.im.enums;

/**
 * presence 节点下面的 show 节点
 */
public enum XMPPNodePresenceShowType {
    AWAY("away"),
    CHAT("chat"),
    DND("dnd"),
    XA("xa"),
    ;

    private String value;
    XMPPNodePresenceShowType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static XMPPNodePresenceShowType valuesOf(String value){
        for (XMPPNodePresenceShowType value1 : values()){
            if (value1.getValue().equals(value)){
                return value1;
            }
        }
        return null;
    }
}
