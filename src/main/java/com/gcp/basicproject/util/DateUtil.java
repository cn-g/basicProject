package com.gcp.basicproject.util;


import com.gcp.basicproject.response.CommonException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算
 *
 * @author gcp
 */
public class DateUtil {

    /**
     * Date类型转为String
     *
     * @param date
     * @return String
     */
    public static String getDateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * String转LocalDate
     */
    public static LocalDate getStringToLocalDate(String dateStr){
        try{
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateStr, fmt);
            //判断是否符合日期格式
            if (dateStr.equals(localDate.format(fmt))) {
                return localDate;
            }else{
                throw new CommonException("时间格式不正确");
            }
        }catch (Exception e){
            // return null;
            throw new CommonException("时间格式不正确");
        }



    }

    /**
     * String类型转为Date
     *
     * @param dateStr
     * @return Date
     */
    public static Date getStrToDate(String dateStr){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = sdf.parse(dateStr);
            //判断是否符合日期格式
            if (dateStr.equals(sdf.format(newDate))) {
                return newDate;
            }else{
                throw new CommonException("时间格式不正确");
            }
        }catch (Exception e){
           // return null;
            throw new CommonException("时间格式不正确");
        }
    }

    /**
     * 组装日期（月）
     *
     * @param year,month
     * @return Date
     */
    public static Date makeUpTime(int year, int month){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return sdf.parse(year + "-" + month + "-01");
        }catch (Exception e){
            throw new CommonException("时间格式不正确");
        }
    }

    /**
     * 获取月份
     *
     * @param date
     * @return int
     */
    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //月份默认为0到11，因此需要加一
        c.add(Calendar.MONTH,+1);
        //因为默认最大为11，所以当月份等于0时，返回12，代表12月
        if(c.get(Calendar.MONTH) == 0){
            return 12;
        }
        return c.get(Calendar.MONTH);
    }

    /**
     * 获取年份
     *
     * @param date
     * @return int
     */
    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //月份默认为0到11，因此需要加一
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取昨天的日期
     *
     * @return Date
     */
    public static Date getYesterDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }



    /**
     * 计算 日/周/月 开始时间
     *
     * @param dateType,date
     * @return Date
     */
    public static Date getStartTime(String dateType, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String newDate = sdf.format(date);
            //判断是否符合日期格式
            if (newDate.equals(sdf.format(date))) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if (ParamUtil.notEmpty(dateType) && dateType.equals("week")) {
                    int dayNumber = c.get(Calendar.DAY_OF_WEEK);
                    if (dayNumber == 1) {
                        c.add(Calendar.DAY_OF_WEEK, -6);
                        //星期减六，因为一个礼拜默认是从星期天开始计算的，而要的是星期一
                        return c.getTime();
                    } else {
                        c.add(Calendar.DAY_OF_WEEK, -dayNumber + 2);
                        return c.getTime();
                    }
                } else if (ParamUtil.notEmpty(dateType) && dateType.equals("month")) {
                    int dayNumber = c.get(Calendar.DAY_OF_MONTH);
                    if (dayNumber == 1) {
                        c.add(Calendar.DAY_OF_MONTH, 0);
                        return c.getTime();
                    } else {
                        c.add(Calendar.DAY_OF_MONTH, -dayNumber + 1);
                        return c.getTime();
                    }
                } else {
                    return c.getTime();
                }
            }
            else{
                throw new CommonException("时间格式不正确");
            }
        } catch (Exception e) {
            throw new CommonException("时间格式不正确");
        }
    }

    /**
     * 计算上一 日/周/月 开始时间
     *
     * @param dateType,date
     * @return Date
     */
    public static Date getLastTime(String dateType, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String newDate = sdf.format(date);
            //判断是否符合日期格式
            if (newDate.equals(sdf.format(date))) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if (ParamUtil.notEmpty(dateType) && dateType.equals("week")) {
                    //计算上周同期
                    c.add(Calendar.WEEK_OF_MONTH,-1);
                    //计算上周开始的日期，即上周星期一的日期
                    c.setTime(getStartTime(dateType,c.getTime()));
                    return c.getTime();
                } else if (ParamUtil.notEmpty(dateType) && dateType.equals("month")) {
                    //计算上月同期
                    c.add(Calendar.MONTH,-1);
                    //计算上月开始的日期，即上月一号
                    c.setTime(getStartTime(dateType,c.getTime()));
                    return c.getTime();
                } else {
                    c.add(Calendar.DAY_OF_MONTH,-1);
                    return c.getTime();
                }
            }
            else{
                throw new CommonException("时间格式不正确");
            }
        } catch (Exception e) {
            throw new CommonException("时间格式不正确");
        }
    }

    /**
     * 计算 日/周/月 结束时间
     *
     * @param dateType,date
     * @return Date
     */
    public static Date getEndTime(String dateType, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String newDate = sdf.format(date);
            //判断是否符合日期格式
            if (newDate.equals(sdf.format(date))) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if (ParamUtil.notEmpty(dateType) && dateType.equals("month")) {
                    c.add(Calendar.MONTH, 1);
                } else if (ParamUtil.notEmpty(dateType) && dateType.equals("week")) {
                    c.setTime(getStartTime(dateType,date));
                    c.add(Calendar.DAY_OF_WEEK,+7);
                } else {
                    c.add(Calendar.DAY_OF_MONTH, 1);
                }
                return c.getTime();
            } else {
                throw new CommonException("时间格式不正确");
            }
        } catch (Exception e) {
            throw new CommonException("时间格式不正确");
        }
    }

    /**
     * 计算 上周同期，上月同周，上年同月
     *
     * @param dateType,date
     * @return Date
     */
    public static Date getSameLastTime(String dateType,Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            String newDate = sdf.format(date);
            if (newDate.equals(sdf.format(date))) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if (ParamUtil.notEmpty(dateType) && dateType.equals("month")) {
                    c.add(Calendar.YEAR, -1);
                    c.setTime(getStartTime(dateType,c.getTime()));
                } else if (ParamUtil.notEmpty(dateType) && dateType.equals("week")) {
                    c.add(Calendar.WEEK_OF_MONTH,-4);
                    c.setTime(getStartTime(dateType,c.getTime()));
                } else {
                    c.add(Calendar.WEEK_OF_MONTH, -1);
                }
                return c.getTime();
            }else{
                throw new CommonException("时间格式不正确");
            }
        }catch(Exception e){
            throw new CommonException("时间格式不正确");
        }
    }
}
