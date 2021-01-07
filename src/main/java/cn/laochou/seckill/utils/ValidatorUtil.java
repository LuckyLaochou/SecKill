package cn.laochou.seckill.utils;

import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式校验
 */
public class ValidatorUtil {

    // 这里只是简单的做一个手机号校验
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile) {
        if(ObjectUtils.isEmpty(mobile)) return false;
        // 看是否匹配
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }

}
