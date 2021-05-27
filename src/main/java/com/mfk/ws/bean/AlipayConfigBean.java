package com.mfk.ws.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 支付宝支付配置(Alipayconf)实体类
 *
 * @author makejava
 * @since 2021-01-19 17:20:50
 *
 *
 CREATE TABLE `alipayconf` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`APPID` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户appid',
`RSA_PRIVATE_KEY` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '私钥 pkcs8格式的',
`notify_url` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '服务器异步通知页面路径',
`return_url` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '页面跳转同步通知页面路径',
`URL` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求网关地址',
`CHARSET` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '编码',
`FORMAT` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '返回格式',
`ALIPAY_PUBLIC_KEY` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '支付宝公钥',
`log_path` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '日志记录目录',
`SIGNTYPE` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT 'SIGNTYPE',
`state` char(1) DEFAULT 'Y' COMMENT '是否有效 Y,N',
`mark` varchar(128) DEFAULT NULL COMMENT '备注',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付宝支付配置'

 */
//@TableName("alipayconf")
public class AlipayConfigBean implements Serializable {
    private static final long serialVersionUID = 683168705647037128L;
    
    private Integer id;
    /**
    * 商户appid
    */
    private String appid;
    /**
    * 私钥 pkcs8格式的
    */
    private String rsaPrivateKey;
    /**
    * 服务器异步通知页面路径
    */
    private String notifyUrl;
    /**
    * 页面跳转同步通知页面路径
    */
    private String returnUrl;
    /**
    * 请求网关地址
    */
    private String url;
    /**
    * 编码
    */
    private String charset;
    /**
    * 返回格式
    */
    private String format;
    /**
    * 支付宝公钥
    */
    private String alipayPublicKey;
    /**
    * 日志记录目录
    */
    private String logPath;
    /**
    * state
    */
    private String state;

    /**
     * SIGNTYPE
     */
    private String signtype;
    /**
     * mark
     */
    private String mark;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    @Override
    public String toString() {
        return "AlipayConfigBean{" +
                "id=" + id +
                ", appid='" + appid + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", url='" + url + '\'' +
                ", charset='" + charset + '\'' +
                ", format='" + format + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", logPath='" + logPath + '\'' +
                ", signtype='" + signtype + '\'' +
                '}';
    }
}