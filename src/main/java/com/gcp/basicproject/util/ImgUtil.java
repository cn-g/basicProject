package com.gcp.basicproject.util;

import com.gcp.basicproject.response.CommonException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Admin
 */
public class ImgUtil {

    public static String addImg(MultipartFile file,String url){
        //获取文件名称
        String fileOrigName = file.getOriginalFilename();
        //设置文件存储路径
        String filepath = url+fileOrigName;
        File targetFile = new File(filepath);
        if(targetFile.exists()){
            //判断该文件是否存在
            throw new CommonException("文件已经存在");
        }
        //判断文件路径是否存在
        if(!targetFile.getParentFile().exists()){
            //文件路径不存在就创建路径
            targetFile.getParentFile().mkdirs();
        }
        try {
            //再次判断,写入文件
            targetFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            byte[] bytes = file.getBytes();
            outputStream.write(bytes);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return targetFile.getAbsolutePath();
    }

}
