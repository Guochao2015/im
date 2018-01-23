package org.im.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FirstLevelCache {

    private static final Map<String,Map<Object,Object>> MAP = new ConcurrentHashMap<String, Map<Object, Object>>(512);

    public static Map<Object, Object> get(String db){
        Map<Object, Object> map = MAP.get(db);
        if (map == null){
            map = new WeakHashMap();
            setMap(db,map);
        }
        return  map;
    }
    public static void setMap(String db,Map<Object,Object> map){
        MAP.put(db,map);
    }
}
