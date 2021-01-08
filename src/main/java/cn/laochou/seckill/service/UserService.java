package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.UserDao;
import cn.laochou.seckill.exception.GlobalException;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.redis.key.base.KeyPrefix;
import cn.laochou.seckill.redis.key.impl.UserKeyPrefix;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.util.MD5Util;
import cn.laochou.seckill.util.UUIDUtil;
import cn.laochou.seckill.vo.LoginVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    public static final String COOKIE_NAME = "token";

    // 推荐使用@Resource，而不是Autowired
    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "redisService")
    private RedisService redisService;

    public User getUserByID(Long userId) {
        return userDao.getUserByID(userId);
    }

    public boolean login(HttpServletResponse response, LoginVO loginVO) {
        System.out.println(loginVO);
        if (loginVO == null) throw new GlobalException(CodeMessage.SERVER_ERROR);
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        // 判断手机号是否存在
        User user = userDao.getUserByID(Long.valueOf(mobile));
        if (user == null) {
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String generatePassword = MD5Util.formPasswordToDBPassword(password, dbSalt);
        if(!generatePassword.equals(dbPassword)) {
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }
        // 生成Cookie
        addCookie(response, user);
        return true;
    }

    public User getUserByToken(HttpServletResponse response, String token) {
        if(ObjectUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKeyPrefix.PREFIX_BY_TOKEN, token, User.class);
        if(user != null){
            // 延长有效期
            addCookie(response, user, token);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, User user) {
        addCookie(response, user, null);
    }

    /**
     * 从 response中加入Cookie或者更新Cookie
     * @param response
     * @param user
     * @param token
     */
    private void addCookie(HttpServletResponse response, User user, String token) {
        // 生成Cookie
        token = token == null ? UUIDUtil.getUUID() : token;
        KeyPrefix keyPrefix = UserKeyPrefix.PREFIX_BY_TOKEN;
        redisService.set(keyPrefix, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        // 将Cookie的过期时间设置为我们Key的过期时间。
        cookie.setMaxAge(keyPrefix.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
