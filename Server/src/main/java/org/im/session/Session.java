package org.im.session;

import org.im.handler.RoutableChannelHandler;
import org.xmpp.packet.Packet;

import java.net.UnknownHostException;
import java.util.Date;

/**
 *
 */
public interface Session extends RoutableChannelHandler {

    int getStatus();

    Date getCreationDate();

    void close();

    boolean isSecure();

    String getHostAddress() throws UnknownHostException;

    String getHostName() throws UnknownHostException;

}