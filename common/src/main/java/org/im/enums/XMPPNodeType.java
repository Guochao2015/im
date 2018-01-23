package org.im.enums;

/**
 * XMPP 节点类型
 */
public enum XMPPNodeType {

    /**
     * 发给服务器
     *  <persence type="available"/> or <persence/>  在线
     *  <persence type="unavailable"/>  离线
     */
    PRESENCE("presence"),

    /**
     * 发送信息
     */
    MESSAGE("message"),
    /**
     * 信息与查询 必须有 id
     */
    IQ("iq"),
    ERROR("error"),
    ;
    private String node;
    XMPPNodeType(String node){
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public static XMPPNodeType valuesOf(String node){
        for (XMPPNodeType node1 : values()){
            if (node1.getNode().equals(node)){
                return node1;
            }
        }
        return null;
    }
}
