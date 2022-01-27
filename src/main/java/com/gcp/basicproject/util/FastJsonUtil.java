package com.gcp.basicproject.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Admin
 */
public class FastJsonUtil {

    /**
     * json字符串转为对象
     * @param o
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toBean(Object o, Type type){
        return o!=null&&o.toString().length()!=0? JSON.parseObject(o.toString(),type):null;
    }

    /**
     * json字符串转为List<Class>对象
     * @param o
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(Object o, Class<T> clazz){
        return o!=null&&o.toString().length()!=0? JSON.parseArray(o.toString(),clazz):null;
    }

    /**
     * 转json
     * @param o
     * @return
     */
    public static String toJSON(Object o){
        return o == null ? null : JSON.toJSONString(o);
    }

}
