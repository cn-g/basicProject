package com.gcp.basicproject.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 导出Excel
 * @since 2021/11/25
 * @author gcp
 */
public class ExcelUtil {

    /**
     * 用户信息导出类
     * @param response 响应
     * @param fileName 文件名
     * @param columnList 每列的标题名
     * @param dataList 需要导出的数据
     * @param headers 每列写入的值对应的属性名
     */
    public static <T> void uploadExcel(HttpServletResponse response, String fileName, List<String> columnList, List<T> dataList, List<String> headers){
        //声明输出流
        OutputStream os = null;
        //设置响应头
        setResponseHeader(response,fileName);
        try {
            //获取输出流
            os = response.getOutputStream();
            HSSFWorkbook wb = new HSSFWorkbook();
            //获取该工作区的第一个sheet
            Sheet sheet1 = wb.createSheet(fileName);
            int excelRow = 0;
            //创建标题行
            Row titleRow = sheet1.createRow(excelRow++);
            for(int i = 0;i<columnList.size();i++){
                //创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));
            }
            //设置内容行
            if(dataList!=null && dataList.size()>0){
                for (T dto : dataList) {
                    Row dataRow = sheet1.createRow(excelRow++);
                    Field[] fields = dto.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        int m = 0;
                        for (String header : headers) {
                            if (field.getName().equals(header)) {
                                Cell cell = dataRow.createCell(m);
                                //获取get方法
                                cell.setCellValue(invokeGet(dto, header)==null?null:invokeGet(dto, header).toString());
                                break;
                            }
                            m++;
                        }
                    }
                }
            }
            //将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *设置浏览器下载响应头
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName+".xlsx");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * java反射bean的get
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 执行get方法
     * @param o
     * @param fieldName
     */
    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * java反射bean的set方法
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行set方法
     * @param o
     * @param fieldName
     * @param value
     */
    public static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
