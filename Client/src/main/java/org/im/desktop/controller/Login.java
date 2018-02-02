package org.im.desktop.controller;


import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import org.im.netty.NettyClient;
import org.im.utils.Util;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import java.net.ConnectException;

public class Login implements Controller {
    public TextField accountNumber;
    public PasswordField password;
    public Label prompt;

    public void login(MouseEvent mouseEvent) throws InterruptedException, ConnectException {
        String an = accountNumber.getText();
        String pwd = password.getText();
        prompt.setText("");
        if (StringUtils.isBlank(an) || StringUtils.isBlank(pwd)){
            prompt.setText("账号和密码不能为空");
            return;
        }
        NettyClient.INSTANCE.doConnection();
        Presence presence = new Presence();
        presence.setType(Presence.Type.subscribe);
        JID jid = new JID(an.concat("@").concat(Util.getPropVal("server.host", "127.0.0.1")));
        presence.setTo(jid);
        presence.setFrom(jid);
        NettyClient.INSTANCE.writeAndFlush(presence,this);
    }

    @Override
    public void packetHandle(Packet packet) throws Exception {

        System.out.println(" packetHandle  "+ packet.toXML());
    }

    @Override
    public void call() throws Exception {

    }
}
