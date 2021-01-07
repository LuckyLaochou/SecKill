package cn.laochou.seckill.service;

import cn.laochou.seckill.dao.UserDao;
import cn.laochou.seckill.pojo.User;
import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.utils.MD5Util;
import cn.laochou.seckill.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    // 推荐使用@Resource，而不是Autowired
    @Resource(name = "userDao")
    private UserDao userDao;

    public User getUserByID(Long userId) {
        return userDao.getUserByID(userId);
    }

    public CodeMessage login(LoginVO loginVO) {
        if (loginVO == null) return CodeMessage.SERVER_ERROR;
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        // 判断手机号是否存在
        User user = userDao.getUserByID(Long.valueOf(mobile));
        if (user == null) {
            return CodeMessage.MOBILE_NOT_EXIST;
        }
        // 验证密码
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String generatePassword = MD5Util.formPasswordToDBPassword(password, dbSalt);
        if(generatePassword.equals(dbPassword)) {
            return CodeMessage.SUCCESS;
        }else {
            return CodeMessage.PASSWORD_ERROR;
        }
    }
}
