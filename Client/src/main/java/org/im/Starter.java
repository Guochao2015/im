package org.im;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

    public void start(Stage primaryStage) throws Exception {

        setCLASS_URL();

        Pane root = FXMLLoader.load(Util.getURL("fxml/login/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Util.getClassInputStream("img/icon.jpg")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("IM");
        primaryStage.show();
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
