package cn.laochou.seckill.dao;

import cn.laochou.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getUserByID(Long id);

    @Update("update user set password = #{password} where id = #{id}")
    void update(User user);
}
