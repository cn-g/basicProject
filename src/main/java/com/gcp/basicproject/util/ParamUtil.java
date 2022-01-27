package com.gcp.basicproject.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
public class ParamUtil {

    private static final String NULL_STRING = "NULL";

    public ParamUtil() {
    }

    public static boolean emptyOrNullString(String str) {
        if (empty((Object)str)) {
            return true;
        } else {
            return "NULL".equals(str.trim().toUpperCase());
        }
    }

    public static boolean empty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return StringUtils.isBlank((String)o);
        } else if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection)o);
        } else {
            return o instanceof Map ? MapUtils.isEmpty((Map)o) : false;
        }
    }


    /**
     * 判断两个字符串是否相等
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean equals(CharSequence cs1,CharSequence cs2){
        if (cs1 == cs2) {
            return true;
        } else if (cs1 != null && cs2 != null) {
            if (cs1.length() != cs2.length()) {
                return false;
            } else if (cs1 instanceof String && cs2 instanceof String) {
                return cs1.equals(cs2);
            } else {
                int length = cs1.length();

                for(int i = 0; i < length; ++i) {
                    if (cs1.charAt(i) != cs2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断其它类型是否相等
     * @param in1
     * @param in2
     * @return
     */
    public static boolean equals(Object in1,Object in2){
        if (in1 == in2) {
            return true;
        }
        if ((in1 == null) || (in2 == null)) {
            return false;
        }
        return in1.equals(in2);
    }

    /**
     * 判断字符串中是否存在该字符
     * @param str
     * @param strList
     * @return
     */
    public static boolean equals(String str, List<String> strList){
        return strList.contains(str);
    }



    public static boolean notEmpty(Object o) {
        return !empty(o);
    }

    public static boolean empty(Object[] ar) {
        return ar == null || ar.length == 0;
    }

    public static boolean notEmpty(Object[] ar) {
        return !empty(ar);
    }

}
