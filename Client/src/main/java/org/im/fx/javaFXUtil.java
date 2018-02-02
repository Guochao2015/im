package org.im.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.im.desktop.controller.Controller;
import org.im.utils.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class javaFXUtil {

    private static final Map<String,Stage> STAGES = new HashMap<>();


    private static final Map<String,Controller> CONTROLLER = new HashMap<>(100);

    public static Stage LoadStage(String name,String uri, StageStyle... stageStyles) throws IOException {



        FXMLLoader loader = new FXMLLoader(Util.getURL(uri));
        loader.setResources(ResourceBundle.getBundle("i18n/message"));

        Pane pane = loader.load();

        Controller controller = loader.getController();

        CONTROLLER.put(name,controller);

        Stage result = new Stage();
        Scene tempScene = new Scene(pane);
        result.setScene(tempScene);
        for (StageStyle style : stageStyles)
            result.initStyle(style);

        addStage(name,result);

        return result;
    }

    private static void addStage(String name, Stage result) {
        STAGES.put(name,result);
    }

    public static Controller getController(String uri){
        return CONTROLLER.get(uri);
    }
    public static Stage getStageBy(String name){
        return    STAGES.get(name);
    }
    public static void setPrimaryStage(String name,Stage stage){
        addStage(name,stage);
    }

    public static Stage show(String name){
        Stage stage = getStageBy(name);
        if (stage != null){
            stage.show();
        }
        return stage;
    }

    public static void  switchStage(String show,String close){
        show(show);
        closeStage(close);
    }
    public static void closeStage(String close){
        getStageBy(close).close();
    }


}
