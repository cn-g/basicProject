package com.gcp.basicproject.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gcp.basicproject.response.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Admin
 */
public class ToolsUtil {
    private static final Logger log = LoggerFactory.getLogger(ToolsUtil.class);
    private static MD5 md5 = new MD5();

    public ToolsUtil(){

    }

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return Calendar.getInstance().getTimeInMillis() + s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获取IP地址
     * @return
     * @throws UnknownHostException
     */
    public static String getServerIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            log.error("error:{}",e);
            return null;
        }
    }

    /**
     * 获取随机数
     * @param length
     * @return
     */
    public static String getIntegerRound(int length) {
        Random random = new Random();
        if (length <= 0) {
            return "0";
        } else {
            StringBuilder numBuffer = new StringBuilder();
            while(numBuffer.length() < length) {
                numBuffer.append(random.nextInt(9));
            }
            return numBuffer.toString();
        }
    }

    /**
     * 分页拷贝
     * @param page
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> IPage<T> convertType(IPage<?> page, Class<T> clazz) {
        return page.getRecords() != null && !page.getRecords().isEmpty() ? page.convert((data) -> {
            return convertType(data, clazz);
        }) : (IPage<T>) page.setRecords(Collections.emptyList());
    }

    /**
     * list拷贝
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertType(List<?> list, Class<T> clazz) {
        return list != null && !list.isEmpty() ? (List)list.stream().map((item) -> {
            return convertType(item, clazz);
        }).collect(Collectors.toList()) : Collections.emptyList();
    }

    private static <T> T convertObject(Object obj,Class<T> clazz){
        Object returnDto;
        try {
            returnDto = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException var4) {
            log.error("Tools转换Bean时出现异常！object={},clazz={}", new Object[]{obj, clazz, var4});
            throw new CommonException("Tools转换Bean时出现异常！");
        }
        if (returnDto != null) {
            BeanUtils.copyProperties(obj, returnDto);
        }
        return (T) returnDto;
    }

    /**
     * 拷贝实体类
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertType(Object object, Class<T> clazz) {
        return object == null ? null : convertObject(object, clazz);
    }

    /**
     * md5加密
     * @param password
     * @return
     */
    public static String getPasswordToMD5(String password){
        return md5.getMD5ofStr(password);
    }

    /**
     * 截取请求路径中域名后面的参数
     * @param url
     * @return
     */
    public String getUrl(String url){
        String[] urls = url.split("\\/");
        String params = new String();
        for(int i = 3;i< urls.length;i++){
            params = params+"/"+urls[i];
        }
        return params;
    }

}
