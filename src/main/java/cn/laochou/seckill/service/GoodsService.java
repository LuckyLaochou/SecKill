package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.GoodsDao;
import cn.laochou.seckill.enums.SeckillStatusEnum;
import cn.laochou.seckill.redis.key.impl.GoodsKeyPrefix;
import cn.laochou.seckill.util.DateUtil;
import cn.laochou.seckill.vo.GoodsVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource(name = "goodsDao")
    private GoodsDao goodsDao;

    @Resource(name = "redisService")
    private RedisService redisService;


    public List<GoodsVO> getGoodsVOList() {
        return goodsDao.getGoodsVOList();
    }


    public GoodsVO getGoodsVOById(Long id) {
        return goodsDao.getGoodsVOById(id);
    }


    /**
     * 获取某一商品的秒杀状态
     * @return 秒杀状态
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
            // 秒杀还未开始
            seckillStatus = SeckillStatusEnum.SECKILL_NOT_BEGIN.getStatus();
        } else if(now > endDate) {
            // 秒杀已经结束了
            seckillStatus = SeckillStatusEnum.SECKILL_END.getStatus();
        }else {
            // 秒杀进行中
            seckillStatus = SeckillStatusEnum.SECKILL_ING.getStatus();
        }
        return seckillStatus;
    }

    /**
     * 获取倒计时时间
     * @param goodsVO
     * @return 倒计时时间
     */
    public int getRemainSeconds(GoodsVO goodsVO) {
        // 开始时间
        long startDate = DateUtil.getDateTime(goodsVO.getStartDate());
        // 当前时间
        long now = System.currentTimeMillis();
        // 秒杀还没开始
        return (int) ((startDate - now) / 1000);
    }

    /**
     * 减库存的方法
     * @param goodsId 商品ID
     */
    public boolean reduceStock(long goodsId) {
        int result = goodsDao.reduceStock(goodsId);
        return result > 0;
    }

    // 其实这里是有必要放入Redis的
    public int getGoodsStockById(Long goodsId) {
        return goodsDao.getGoodsStockById(goodsId);
    }

    public String getGoodsList() {
        String cacheGoodsVO = redisService.get(GoodsKeyPrefix.PREFIX_GOODSLIST, "", String.class);
        if(cacheGoodsVO == null) {
            List<GoodsVO> goodsVOList = getGoodsVOList();
            cacheGoodsVO = JSONObject.toJSONString(goodsVOList);
            redisService.set(GoodsKeyPrefix.PREFIX_GOODSLIST, "", cacheGoodsVO);
        }
        return cacheGoodsVO;
    }
}
