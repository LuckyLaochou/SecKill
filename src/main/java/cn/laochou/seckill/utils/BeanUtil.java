package cn.laochou.seckill.utils;

import com.alibaba.fastjson.JSON;

public class BeanUtil {

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String value, Class<T> clazz) {
        if(clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        }else if(clazz == String.class) {
            return (T) value;
        }else if(clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        }else {
            return JSON.parseObject(value, clazz);
        }
    }

}
