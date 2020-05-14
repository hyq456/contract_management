package com.hdu.contract_management.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.contract_management.entity.Template;
import com.hdu.contract_management.service.TemplateService;
import com.hdu.contract_management.utils.ResultUtil;
import com.hdu.contract_management.utils.officeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 合同模板表 前端控制器
 * </p>
 *
 * @author hyq
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @Value("${prop.template-folder}")
    private String TEMPLATE_FOLDER;

    @GetMapping("")
    public ResultUtil getAllTemplate() {
        QueryWrapper<Template> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return ResultUtil.success("查询所有模板成功", templateService.list(wrapper));
    }

    @PostMapping("")
    public ResultUtil createTemplate(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        Template template = new Template();

        if (file.isEmpty()) {
            System.out.println("文件为空");
            return ResultUtil.error("文件为空");
        } else {
            String fileName = file.getOriginalFilename();
            template.setFilename(fileName);
            template.setAuthor(Integer.parseInt(request.getParameter("id")));
            template.setUploadTime(LocalDateTime.now());
            template.setName(fileName.substring(0, fileName.lastIndexOf(".")));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            String newFileName = uuid + "." + suffixName;
            template.setPath(newFileName);
            String path = new File(TEMPLATE_FOLDER).getAbsolutePath() + File.separator + newFileName;
            File fileDir = new File(path);
            if (!fileDir.getParentFile().exists() && !fileDir.isDirectory()) {
                fileDir.getParentFile().mkdirs();
            }
            try {
                //以原来的名称命名,覆盖掉旧的
                System.out.println("上传的文件：" + fileName + "," + "，保存的路径为：" + fileDir);
                file.transferTo(fileDir);
                //word 转 png
                String picPath = officeUtil.wordTopng(TEMPLATE_FOLDER, newFileName);
                template.setPicpath("img" + File.separator + picPath);
                templateService.save(template);
            } catch (Exception e) {
                return ResultUtil.error(e.getMessage());
            }
            return ResultUtil.success("保存模板成功");
        }
    }

    @DeleteMapping("")
    public ResultUtil deleteTemplate(HttpServletRequest request) {
        String path = request.getParameter("path");
        templateService.deleteTemplate(path);
        return ResultUtil.success("删除模板成功");
    }

    @GetMapping("/download")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getParameter("path");
        Template template = templateService.getOne(new QueryWrapper<Template>().eq("path", path));
        String filePath = TEMPLATE_FOLDER + File.separator + path;
        File file = new File(filePath);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            //判断文件父目录是否存在
            if (file.exists()) {
                //设置返回文件信息
                if (path.endsWith(".docx")) {
                    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8");
                } else if (path.endsWith(".doc")) {
                    response.setContentType("application/msword;charset=UTF-8");
                }
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(template.getFilename(), "UTF-8"));
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
                System.out.println("下载模板：" + file.getPath());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

