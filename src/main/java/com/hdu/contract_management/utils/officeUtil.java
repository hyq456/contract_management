package com.hdu.contract_management.utils;

import com.spire.doc.Document;
import com.spire.doc.documents.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
    word首页转换为png格式
    @param "path" 文件夹路径 如"F:\\123\\"
    @param "name" 文件名 如"123.docx"
    @return 返回图片文件名
 */
public class officeUtil {


    public static String wordTopng(String path,String name) throws IOException {
        Document doc = new Document();
        doc.loadFromFile(path+File.separator+name);
        BufferedImage image= doc.saveToImages(0, ImageType.Bitmap);
        String filename = name.substring(0,name.lastIndexOf("."));
        File file= new File(path+File.separator+filename+".png");
        ImageIO.write(image, "PNG", file);
        System.out.println("word转png完成："+file.getName());
        return file.getName();
    }
}
