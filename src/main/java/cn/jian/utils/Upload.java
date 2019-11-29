package cn.jian.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

public class Upload {

    public static String fileUpload(MultipartFile imageFile, HttpServletRequest request) throws Exception{
        String fileName = null;
        if(!imageFile.isEmpty()){

            File dir = new File("C:\\uploads");
            if(!dir.exists()){
                dir.mkdirs();
            }

            //重新生成文件名字
            String uuid = UUID.randomUUID().toString();
            fileName = uuid+"."+imageFile.getOriginalFilename().split("\\.")[1];
            //将文件保存到指定目录
            File targetFile = new File("C:\\uploads" + File.separator + fileName);
            imageFile.transferTo(targetFile);
        }
        //返回文件名字供保存
        return fileName;
    }
}
