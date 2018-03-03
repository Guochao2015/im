package org.im.utils;

import org.im.desktop.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    private static final Map<String,URL> CACHE_FXML = new WeakHashMap<>(20);

    private static final Map<String,Controller> CONTROLLER_MAP = new HashMap<>();

    public  static String  CLASSES_URL;
    public static boolean isJar = false;

    public static ExecutorService getExecutorService() {
        return executorService;
    }


    public static ResourceBundle getConfig(){
        return CONFIG;
    }
    public static <T>  T getPropVal(String key,T defaultValue){
        String val = getConfig().getString(key);
        if (val == null){
            return defaultValue;
        }

        if(defaultValue instanceof Integer){
            return (T) Integer.valueOf(val);
        }else if (defaultValue instanceof Boolean){
            return (T)Boolean.valueOf(val);
        }else if (defaultValue instanceof Float){
            return (T)Float.valueOf(val);
        }else if (defaultValue instanceof Double){
            return (T)Double.valueOf(val);
        }else if (defaultValue instanceof Byte){
            return (T)Byte.valueOf(val);
        }else if (defaultValue instanceof Character){
        }

        return (T) val;
    }

    public static ClassLoader getClassLoader(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null){
            classLoader = Util.class.getClassLoader();
            if (classLoader == null){
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    public static URL getURL(String path) throws MalformedURLException {
        URL url = CACHE_FXML.get(path);
        if (url == null){
            if (isJar){
                url = ClassLoader.getSystemResource(path);
            }else {
                url = new File(CLASSES_URL.concat(path)).toURI().toURL();
            }
            LOGGER.info("读取文件 [{}]",url);
            synchronized (CACHE_FXML){
                CACHE_FXML.put(path,url);
            }
        }
        return url;
    }
    public static InputStream getClassInputStream(String path) throws MalformedURLException, FileNotFoundException {
        if (!isJar){
            return new FileInputStream(getURL(path).getFile());
        }else {
            return ClassLoader.getSystemResourceAsStream(path);
        }
    }

    public static void setCallController(String key,Controller controller){
        CONTROLLER_MAP.put(key,controller);
    }
    public static Controller getCallController(String key){
        return CONTROLLER_MAP.remove(key);
    }
}
