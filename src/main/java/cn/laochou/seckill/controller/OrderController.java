package cn.laochou.seckill.controller;

import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import cn.laochou.seckill.service.GoodsService;
import cn.laochou.seckill.service.OrderService;
import cn.laochou.seckill.vo.GoodsVO;
import cn.laochou.seckill.vo.OrderDetailVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "goodsService")
    private GoodsService goodsService;


    @RequestMapping("/detail/{id}")
    @ResponseBody
    public Result<OrderDetailVO> getOrderDetailById(@PathVariable("id") Long id, User user) {
        OrderInfo orderInfo = orderService.getOrderInfoById(id);
        if(orderInfo == null) return Result.error(CodeMessage.ORDER_NOT_EXIST);
        GoodsVO goodsVO = goodsService.getGoodsVOById(orderInfo.getGoodsId());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrderInfo(orderInfo);
        orderDetailVO.setGoodsVO(goodsVO);
        return Result.success(orderDetailVO);
    }


}
