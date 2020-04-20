package com.hdu.contract_management.service;

import com.hdu.contract_management.entity.Template;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 合同模板表 服务类
 * </p>
 *
 * @author hyq
 * @since 2020-04-09
 */
public interface TemplateService extends IService<Template> {
    public void deleteTemplate(String path);
}
