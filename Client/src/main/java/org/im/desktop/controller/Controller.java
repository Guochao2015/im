package org.im.desktop.controller;

import org.xmpp.packet.Packet;

public interface Controller {

    /**
     * 处理服务器返回的 packet
     * @param packet
     * @throws Exception
     */
    void packetHandle(Packet packet) throws Exception ;

    void call() throws Exception;
}
