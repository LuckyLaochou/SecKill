package cn.laochou.seckill.access;

import cn.laochou.seckill.pojo.User;

/**
 * 记录我们的User用户
 * 用户上下文
 */
public class UserContext {

    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();


    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

}
