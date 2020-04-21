package com.hdu.contract_management.utils;

        import com.alibaba.fastjson.JSONObject;
        import com.dingtalk.api.DefaultDingTalkClient;
        import com.dingtalk.api.DingTalkClient;
        import com.dingtalk.api.request.OapiUserGetByMobileRequest;
        import com.dingtalk.api.response.OapiUserGetByMobileResponse;
        import com.taobao.api.ApiException;
        import static com.hdu.contract_management.utils.AccessTokenUtil.getToken;

public class DIngdingUIDUtil {
    public static String getByMobile(String tel){
        String access_token = getToken();
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest req = new OapiUserGetByMobileRequest();
            req.setMobile(tel);
            req.setHttpMethod("GET");
            OapiUserGetByMobileResponse rsp = client.execute(req, access_token);
            JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
            return jsonObject.getString("userid");
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
