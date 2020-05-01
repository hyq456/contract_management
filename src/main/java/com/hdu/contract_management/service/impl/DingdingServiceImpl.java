package com.hdu.contract_management.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.hdu.contract_management.config.WebMvcConfig;
import com.hdu.contract_management.entity.User;
import com.hdu.contract_management.entity.vo.WorkRecordVo;
import com.hdu.contract_management.service.DingdingService;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hdu.contract_management.config.URLConstant.URL_WORKRECORD_ADD;
import static com.hdu.contract_management.utils.AccessTokenUtil.getToken;
import static com.hdu.contract_management.utils.DIngdingUIDUtil.getByMobile;

@Service
public class DingdingServiceImpl implements DingdingService {
    @Override
    public void sendWorkRecord(WorkRecordVo workRecordVo) throws RuntimeException {
        String access_token = getToken();
        String uid = getByMobile(workRecordVo.getUser().getTel());
        try{
            DingTalkClient client = new DefaultDingTalkClient(URL_WORKRECORD_ADD);
            OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
            req.setUserid(uid);
            req.setCreateTime(new Date().getTime());
            req.setTitle(workRecordVo.getTitle());
            req.setUrl("http://118.89.113.175");//118.89.113.175
            List<OapiWorkrecordAddRequest.FormItemVo> list2 = new ArrayList<>();
            OapiWorkrecordAddRequest.FormItemVo obj3 = new OapiWorkrecordAddRequest.FormItemVo();
            list2.add(obj3);
            obj3.setTitle(workRecordVo.getSubTitle());
            obj3.setContent(workRecordVo.getContent());
            req.setFormItemList(list2);
            req.setSourceName("合同管理助手");
            OapiWorkrecordAddResponse rsp = client.execute(req, access_token);
            System.out.println(rsp.getBody());
        }
        catch (ApiException e) {
            throw new RuntimeException();
        }
    }
}
