package org.im.session;

import io.netty.channel.ChannelHandlerContext;
import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

import java.net.UnknownHostException;
import java.util.Date;

public class SessionManage implements Session {

    /**
     * 用户状态
     */
    private int status;
    /**
     * 是否验证通过
     */
    private boolean validate;
    /**\
     * 账号
     */
    private String jid;

    private String hostAddress;

    private String hostName;
    /**
     * 通讯管道
     */
    private ChannelHandlerContext channel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
    public String getJid(){
        return this.jid;
    }


    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }


    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }


    @Override
    public Date getCreationDate() {
        return null;
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getHostAddress() throws UnknownHostException {
        return null;
    }

    @Override
    public String getHostName() throws UnknownHostException {
        return null;
    }

    @Override
    public JID getAddress() {
        return new JID(jid);
    }

    @Override
    public void process(Packet packet) throws UnauthorizedException, PacketException {

    }
}
