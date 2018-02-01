module org.im.common{
    requires netty.all;
    requires dom4j;
    requires org.apache.commons.lang3;
    exports org.im.common.packet;
    exports org.im.common;
}