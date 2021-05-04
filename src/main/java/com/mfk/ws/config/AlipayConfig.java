package com.mfk.ws.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 支付宝支付所需资质
 * @author qingzhi
 * @date 2020/4/2 14:56
 */
public class AlipayConfig {
    /**
     * 应用ID号（）
     */
    public static String APP_ID = "";//你的应用appId
    /**
     * 你的私钥
     */
    public static String APP_PRIVATE_KEY = "";
    /**
     * 编码
     */
    public static String CHARSET = "";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "";
    /**
     * 支付宝网关地址
     */
//    private static String GATEWAY = "https://openapi.alipay.com/gateway.do";//正式环境

    private static String GATEWAY = "";//沙箱环境
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "";//验签
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "";//姑且没用到
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "";//支付成功后返回哪个前端页面
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "TRADE_SUCCESS";
    /**
     * 交易关闭回调(当该笔订单全部退款完毕,则交易关闭)
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 支付宝开发平台中的支付宝账号（企业）
     */
    public static final String SELLER_ID = "";

    //签名算法类型(根据生成私钥的算法,RSA2或RSA)
    public static final String SIGNTYPE = "RSA2";
    /**
     * 支付宝请求客户端入口
     */
    private volatile static AlipayClient alipayClient = null;

    /**
     * 不可实例化
     */
    private AlipayConfig(){};

    /**
     * 双重锁单例
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if (alipayClient == null){
            synchronized (AlipayConfig.class){
                if (alipayClient == null){
                    alipayClient = new DefaultAlipayClient(GATEWAY,APP_ID,APP_PRIVATE_KEY,PARAM_TYPE,CHARSET,ALIPAY_PUBLIC_KEY,SIGNTYPE);
                }
            }
        }
        return alipayClient;
    }

}