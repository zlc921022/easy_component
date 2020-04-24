package com.xiaochen.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : json解析工具类
 */
public class JsonUtil {

    /**
     * json转bean对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> tClass) {
        try {
            final Gson gson = new Gson();
            return gson.fromJson(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * bean转json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> String beanToJson(Class<T> tClass) {
        try {
            final Gson gson = new Gson();
            return gson.toJson(tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转数组对象
     * @param json
     * @param aClass
     * @param <T>
     * @return
     */
    public static <T> T[] jsonToArray(String json, Class<T[]> aClass) {
        try {
            final Gson gson = new Gson();
            return gson.fromJson(json, aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数组转json
     * @param tArr
     * @param <T>
     * @return
     */
    public static <T> String arrayToJson(T[] tArr){
        try{
            final Gson gson = new Gson();
            return gson.toJson(tArr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转list集合
     * @param json
     * @param typeToken
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json,TypeToken<List<T>> typeToken){
        try{
            final Gson gson = new Gson();
            return gson.fromJson(json, typeToken.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * list集合转json
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> String listToJson(List<T> tList){
        try{
            final Gson gson = new Gson();
            return gson.toJson(tList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转map
     * @param json
     * @param typeToken
     * @param <T>
     * @return
     */
    public static <T,V> Map<T,V> jsonToMap(String json,TypeToken<Map<T,V>> typeToken){
        try {
            final Gson gson = new Gson();
            return gson.fromJson(json,typeToken.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map集合转json
     * @param map
     * @param <T>
     * @return
     */
    public static <T,V> String mapToJson(Map<T,V> map){
        try{
            final Gson gson = new Gson();
            return gson.toJson(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
