package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.GoodsDao;
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
}
