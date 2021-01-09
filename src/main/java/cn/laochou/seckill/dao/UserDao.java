package cn.laochou.seckill.dao;

import cn.laochou.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getUserByID(Long id);

}
