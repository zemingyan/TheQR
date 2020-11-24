package com.example.demo;


import jdk.internal.util.xml.impl.Input;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController

public class QRController {

    @GetMapping(value = "/generate")
    @ResponseBody
    public void generateQR(@RequestParam(value = "content")String content, HttpServletResponse response) {
        BufferedImage image;
        // 禁止图像缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        image = QRCodeUtil.createImage(content);

        String fileDirPath = getClass().getResource("/static").getPath();
        //+ File.separator + "logo.png";

        System.out.println(fileDirPath);
        String filePath = fileDirPath + File.separator + "logo.png";
        System.out.println(filePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //InputStream inputStream = this.getClass().getResourceAsStream("/static/logo.png");

        System.out.println((inputStream == null));
        QRCodeUtil.insertImage(image, inputStream, Boolean.TRUE);

        // 创建二进制的输出流
        try(ServletOutputStream sos = response.getOutputStream()){
            // 将图像输出到Servlet输出流中。
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping(value = "/index")
    @ResponseBody
    public String index(){
        return "index";
    }
}