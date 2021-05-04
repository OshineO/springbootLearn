package com.mfk.ws.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("tb_goods")
public class WsBean implements Serializable {

    @TableId
    private Long goodsId;
    /**
     * 商品名
     */
    private String name;
    /**
     * 介绍
     */
    private String intro;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer num;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "WsBean{" +
                "goodsId=" + goodsId +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
