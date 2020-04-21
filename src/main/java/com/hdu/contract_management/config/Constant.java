package com.hdu.contract_management.config;

public class Constant {
    /**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "dingf768556cbe96b3e9ffe93478753d9884";
    /**
     * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPKEY = "dingh9zrkoz6ytssbaqg";
    /**
     * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPSECRET = "-SLM_33Kj06ey-Za3OGuZc_0CJutrkt4N90aAFnVFju9gnSeh2v1Ti3-rCc0-5q9";

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "***";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "1234";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 691606685L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到
     */
    public static final String PROCESS_CODE = "PROC-99F0E520-E6A6-4DA6-BB6F-B49DB93DD3C8";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = "***";
}
