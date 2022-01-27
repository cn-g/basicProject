package com.gcp.basicproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Admin
 */
public class RequestUtil {

    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);

    public static final String TOKEN = "token";

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        HttpServletRequest request = getRequestWithNull();
        return request == null ? null : request.getHeader(TOKEN);
    }

    public static HttpServletRequest getRequestWithNull() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        } else {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            return request;
        }
    }

    /**
     * 截取token前半段数据(获取用户id)
     * @return
     */
    public static String getUserId(){
        String userId = getToken();
        return userId==null?null:userId.split("\\|")[0];
    }

}
