package cn.laochou.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String MD5(String str) {
        return DigestUtils.md5Hex(str);
    }


    public static String inputPasswordToFormPassword(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(salt.charAt(0));
        stringBuilder.append(salt.charAt(2));
        stringBuilder.append(password);
        stringBuilder.append(salt.charAt(4));
        stringBuilder.append(salt.charAt(5));
        return MD5(stringBuilder.toString());
    }

    public static String formPasswordToDBPassword(String password, String salt) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(salt.charAt(0));
        stringBuilder.append(salt.charAt(2));
        stringBuilder.append(password);
        stringBuilder.append(salt.charAt(4));
        stringBuilder.append(salt.charAt(5));
        return MD5(stringBuilder.toString());
    }

    public static String inputPasswordToDBPassword(String password, String dbSalt) {
        String formPassword = inputPasswordToFormPassword(password);
        return formPasswordToDBPassword(formPassword, dbSalt);
    }

}
