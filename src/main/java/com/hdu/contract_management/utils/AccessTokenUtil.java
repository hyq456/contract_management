package com.hdu.contract_management.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.hdu.contract_management.config.Constant;
import com.taobao.api.ApiException;

import static com.hdu.contract_management.config.URLConstant.URL_GET_TOKKEN;

public class AccessTokenUtil {
    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(Constant.APPKEY);
            request.setAppsecret(Constant.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            throw new RuntimeException();
        }
    }
}
