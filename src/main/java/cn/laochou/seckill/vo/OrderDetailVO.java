package cn.laochou.seckill.vo;

import cn.laochou.seckill.pojo.OrderInfo;

public class OrderDetailVO {

    private GoodsVO goodsVO;

    private OrderInfo orderInfo;


    public GoodsVO getGoodsVO() {
        return goodsVO;
    }

    public void setGoodsVO(GoodsVO goodsVO) {
        this.goodsVO = goodsVO;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
