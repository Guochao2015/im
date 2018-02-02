package org.im;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.im.fx.javaFXUtil;
import org.im.netty.NettyClient;
import org.im.utils.Util;

import java.io.FileInputStream;


public class Starter extends Application {

    public static void main(String[] args) throws/* FrameGrabber.Exception,*/ InterruptedException {

        NettyClient.INSTANCE.doConnection();

/*
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        CanvasFrame canvas = new CanvasFrame("摄像头");

        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas.setAlwaysOnTop(true);

        while (true){
            if (canvas.isDisplayable()){
                grabber.stop();
                System.exit(2);
            }
            canvas.showImage(grabber.grab());
            Thread.sleep(50);
        }*/
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        setCLASS_URL();
        /*Pane root = javaFXUtil.Load("fxml/login/login.fxml");
        Scene scene = new Scene(root);*/

        javaFXUtil.setPrimaryStage("root",stage);

        Stage login = javaFXUtil.LoadStage(R.id.LOGIN, R.Layout.LOGIN);//StageStyle.UNDECORATED

        login.setTitle(R.id.LOGIN);

        login.setOnCloseRequest((event ) -> {
            try {
                NettyClient.INSTANCE.stop();
            }finally {
                System.exit(0);
            }
        });

        login.setResizable(false);
        login.getIcons().add(new Image(Util.getClassInputStream("img/icon.jpg")));

        javaFXUtil.show(R.id.LOGIN);

    }

    public void setCLASS_URL(){
        if ("jar".equals(getClass().getResource("").getProtocol())){
            Util.isJar = true;
        }else{
            Util.CLASSES_URL = getClass().getResource("").getFile().concat("../../");
            Util.isJar = false;
        }
    }
}
