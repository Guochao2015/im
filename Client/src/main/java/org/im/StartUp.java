package org.im;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;

public class StartUp extends Application {

    public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {

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
        }


//        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {


//        primaryStage.setTitle("java fx demo");
//        primaryStage.show();
    }
}
