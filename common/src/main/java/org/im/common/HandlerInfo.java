package org.im.common;

/**
 *
 */
public class HandlerInfo {
    private String desc;
    private String xmlns;

    public HandlerInfo(String desc, String xmlns) {
        this.desc = desc;
        this.xmlns = xmlns;
    }

    public String getDesc() {
        return desc;
    }

    public String getXmlns() {
        return xmlns;
    }
}
