package org.im.handler;

import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

public interface RoutableChannelHandler extends ChannelHandler<Packet> {

    /**
     * @return the XMPP address.
     */
    JID getAddress();
}
