package com.mfk.ws.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 支付宝支付所需资质
 * @author qingzhi
 * @date 2020/4/2 14:56
 */
public class AlipayConfig_bak {
    /**
     * 应用ID号（）
     */
    public static String APP_ID = "2021000117603069";//你的应用appId
    /**
     * 你的私钥
     */
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDfCYn7PxYIrq+fmkRhFfPm3/BeLcxlXXdwmNHZQ+D5z7pneeqb1PaR1VIr82fJX7qHBFx40byKC7xDeMH3S31xseG3MKzNa8WMwduRLZwQDqkWhDSU9z1FcnvoZ3TbcwPFUiychuGuz/mhzgXbgx2XkqZbkkG3zzctlgF/vTRLDy7NB8a6sAe6xGK8aJQBnLPoy9zPQmbt9jQCNn1biepzD/y3Yhz+RF7m9A0vOKNyrf/DsGIF+JaCtYvp12XAY6zc+Q0LHonV3e0WPVAJor1VYdwY7ZPQ49f7nqsHGa+jgxZ2hAb1mBdjbNoftd92D985wmY/kTkRd/q7TBpSTyqLAgMBAAECggEAFqs8h/nIfodquDkQvCFkEdIu9xOGk8LlMd+GbKO2iQ1w77V3f/EsQnRBDtCFevUs2y45S9pdZFZ/AwiSBlV44YZn35ul5JcQlBbb/yx4kTU8QFqdkYpzgsx5BY0GVouhFXwdpqca5w6iW6y58a6o4k/4cpQx88Gu5QY7DsWnO/2VX+lrncFU9cMSAjS+4FmqHYhYH3W/BYz4U7B1Bw8Tj5B5BLUCtvq3MYx3oReOwbqhc7X1f/F/NTVacF98jpQ2ZQZP+JTjG1jC4wwlTh98uT4Gf7FMlTlefelqrUTOjt4YrIfzTuwnWSKWmM4yLZlGGV8rCpcooy9+sXkVdMr+MQKBgQD/rIpoRGzxdkJB9ca2xQ3G1/Lx4jyEMhvOpB4SRoKdp4TJNNiPHSvJBSoLUv/AritFkOmRovuNZMIeMdU357gxH+AQRX7e+VYwx9jpzpP8trCteB2w0RpJAwh93RboPf9qbLi2TdGwWgEzYdCOHrzOl99oslvD9VoRn1MsecPyYwKBgQDfUlhC3E2DX9UwmXu8X3kKOod0I1VYjpRqL0ruAJ3kz2K/Zd2NQ8ZLY6qGKZR6Bw+nKTrB3OWf0qPFbsI61Yb5LHya41ZtPrE73yDcDg+alPpx25VX2lE8mVqfIQmWj6ZbHQZkaiZR7RkbtzdpGbMU1cyHpkJcsR1+E1VmmyZLuQKBgCZCXIfTzXWom6uhKrZI8JHT2OAHWcTydqTKfFGByaPr4Hv3c3yfAeFX6328UtczePg1Ai6v8/KppD6+eEigXTjEq1PkAmM9bn9oOHZsWkrTz8Z1lgPSlptcX9Xt0rK4LFNxp4ZoSLuaDFnzBjFuOhS0N1NEdWTyfyLp85lRM1g/AoGABMHIR4RR0l6UN/jM/fI6rdEZWYv3IW/IEYr4WaGBu4H/ZHxS/9NVwf1ZgIoaM4868iCas850xbbC7k2xrXcm7N/Zt6LoEGOpetEB7i4Ofwh1XT+EKt7npBbQK1uUzLQ6ou43WKQ6NLyMMF7yuzZk7iivtqP//eF381uOEiVz52ECgYApYjHCL+ZnZJ1Z1KcG6Q2sjteYLlRNzY9Izw9nt/fDw2yfBscfrz7/2WqnWKCjlbhmauPLejk9remMH2AmPvONwGlTK9UHkL493iiT7y6jo4Az/yt770Cfb6KcQHIoyIJVHSbvZHJjCODTzzvhfQuv/pIUwG7RE76DrTt3Kybbvg==";
    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm4QgO3o4PF9lurRAKeVQHjBHnlCQ/fCSiH/6lxIkznCTXlGqZUTUuodLdf9PIvdiJVRdx47HnNsVxes2pFtGvwpWS2m9/5rBga2N3C+8s7GR6fCD2oN3KCHM4BhY703pHpGtbjDzEl7cC/+dJjEDwoksqvPseP1KvE8sL53NT+/iiJSMfQHcvSSTAL1W9Rc1KB7TJQAVYXq0oQtRt946dcvbuBVjd4E15Wxzuhwrsv1pJqhUJscZA9F4NK0c8YbMohtfTnsHmrV5BH25esj1bZehoZqKRuEbO0Db7tDOINxblLeJN3gdQY8kzjvHvHWlMhOZpq4/odQG8mjzF8YjGQIDAQAB";
    /**
     * 支付宝网关地址
     */
//    private static String GATEWAY = "https://openapi.alipay.com/gateway.do";//正式环境

    private static String GATEWAY = "https://openapi.alipaydev.com/gateway.do";//沙箱环境
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "http://zshop.free.idcfengye.com/pay";//验签
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "";//姑且没用到
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "http://zshop.free.idcfengye.com/alipay/index";//支付成功后返回哪个前端页面
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
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
    private AlipayConfig_bak(){};

    /**
     * 双重锁单例
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if (alipayClient == null){
            synchronized (AlipayConfig_bak.class){
                if (alipayClient == null){
                    alipayClient = new DefaultAlipayClient(GATEWAY,APP_ID,APP_PRIVATE_KEY,PARAM_TYPE,CHARSET,ALIPAY_PUBLIC_KEY,SIGNTYPE);
                }
            }
        }
        return alipayClient;
    }

}