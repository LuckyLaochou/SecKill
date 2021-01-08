package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.GoodsDao;
import cn.laochou.seckill.util.DateUtil;
import cn.laochou.seckill.vo.GoodsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource(name = "goodsDao")
    private GoodsDao goodsDao;


    public List<GoodsVO> getGoodsVOList() {
        return goodsDao.getGoodsVOList();
    }


    public GoodsVO getGoodsVOById(int id) {
        return goodsDao.getGoodsVOById(id);
    }


    /**
     * 获取某一商品的秒杀状态
     * @return
     */
    public int getSeckillStatus(GoodsVO goodsVO) {
        int seckillStatus;
        // 逻辑判断
        // 开始时间
        long startDate = DateUtil.getDateTime(goodsVO.getStartDate());
        // 截止时间
        long endDate = DateUtil.getDateTime(goodsVO.getEndDate());
        // 当前时间
        long now = System.currentTimeMillis();
        if(now < startDate) {
          seckillStatus = 0;
        } else if(now > endDate) {
            // 秒杀已经结束了
            seckillStatus = 2;
        }else {
            // 秒杀进行中
            seckillStatus = 1;
        }
        return seckillStatus;
    }

    /**
     * 获取倒计时时间
     * @param goodsVO
     * @return
     */
    public int getRemainSeconds(GoodsVO goodsVO) {
        // 开始时间
        long startDate = DateUtil.getDateTime(goodsVO.getStartDate());
        // 当前时间
        long now = System.currentTimeMillis();
        // 秒杀还没开始
        return (int) ((startDate - now) / 1000);
    }
}
