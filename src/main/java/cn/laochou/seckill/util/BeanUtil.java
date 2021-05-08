package cn.laochou.seckill.util;

import com.alibaba.fastjson.JSON;

public class BeanUtil {

    private BeanUtil() {}

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

    public static <T> String beanToString(T value) {
        if(value == null) return null;
        Class<?> clazz = value.getClass();
        if(clazz == Integer.class) {
            return String.valueOf(value);
        }else if(clazz == String.class) {
            return (String) value;
        }else if(clazz == Long.class) {
            return String.valueOf(value);
        }else {
            return JSON.toJSONString(value);
        }
    }

}
