package com.jenius.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by liyongjun on 17/2/22.
 */
@Controller
public class FileUploadController {


    public String fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestAttribute("fileName")String fileName) throws IOException {
        // 判断文件是否为空
        if (!file.isEmpty())  {
                // 文件保存路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));

        }
        // 重定向
        return "redirect:/list.html";
    }
}
