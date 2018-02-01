module gui{
    requires javafx.fxml;
    requires netty.all;
    requires org.apache.commons.lang3;
    requires slf4j.api;
    requires tinder.local;
    requires java.xml;
    requires javafx.graphics;
    requires dom4j;
    requires org.im.common;
    exports org.im;
    exports org.im.desktop.controller;
}