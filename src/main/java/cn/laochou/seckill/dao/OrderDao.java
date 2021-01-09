package cn.laochou.seckill.dao;

import cn.laochou.seckill.pojo.OrderInfo;
import cn.laochou.seckill.pojo.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * 订单Mapper
 */
@Mapper
public interface OrderDao {


    @Select("select * from seckill_order where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") int goodsId);


    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) values (#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);

    @Insert("insert into seckill_order(user_id, goods_id, order_id) values (#{userId}, #{goodsId}, #{orderId})")
    void insertSeckillOrder(SeckillOrder seckillOrder);
}
