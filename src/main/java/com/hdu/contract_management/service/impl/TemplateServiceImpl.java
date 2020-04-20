package com.hdu.contract_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hdu.contract_management.entity.Contract;
import com.hdu.contract_management.entity.Template;
import com.hdu.contract_management.mapper.TemplateMapper;
import com.hdu.contract_management.service.TemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.contract_management.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * <p>
 * 合同模板表 服务实现类
 * </p>
 *
 * @author hyq
 * @since 2020-04-09
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Autowired
    TemplateService templateService;

    @Value("${prop.template-folder}")
    private String TEMPLATE_FOLDER;

    @Override
    public void deleteTemplate(String path) {
        String filepath = TEMPLATE_FOLDER + File.separator + path;
        Template template = templateService.getOne(new QueryWrapper<Template>().eq("path", path));
        String picpath = TEMPLATE_FOLDER + File.separator + template.getPicpath();

        System.out.println(path);

        File file = new File(filepath);
        File pic = new File(picpath);
        try {
            if (file.exists()) {
                file.delete();
                System.out.println(filepath + "文件已删除");
            } else
                System.out.println(filepath + "文件不存在");
            if (pic.exists()) {
                pic.delete();
                System.out.println(picpath + "文件已删除");
            } else
                System.out.println(picpath + "文件不存在");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        templateService.removeById(template.getId());
    }
}
