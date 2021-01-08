package cn.laochou.seckill.dao;

import cn.laochou.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsDao {


    @Select("select g.*, sg.seckillPrice, sg.stock_count, sg.start_date, sg.end_date from seckill_goods sg left join goods g on sg.goods_id = g.id")
    List<GoodsVO> getGoodsVOList();

    @Select("select g.*, sg.seckillPrice, sg.stock_count, sg.start_date, sg.end_date from seckill_goods sg left join goods g on sg.goods_id = g.id where g.id = #{id}")
    GoodsVO getGoodsVOById(@Param("id") int id);
}
