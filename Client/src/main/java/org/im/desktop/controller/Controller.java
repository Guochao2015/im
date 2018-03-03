package org.im.desktop.controller;

import org.xmpp.packet.Packet;

public abstract class Controller {

    public double xOffset = 0, yOffset = 0;

    /**
     * 处理服务器返回的 packet
     * @param packet
     * @throws Exception
     */
   public abstract void packetHandle(Packet packet) throws Exception ;

    public abstract  void call() throws Exception;
}
