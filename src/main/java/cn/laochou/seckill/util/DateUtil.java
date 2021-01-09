package cn.laochou.seckill.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String format = "yyyy-MM-dd HH:mm:ss";


    public static String dateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }


    public static Date stringToDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getDateTime(String dateStr) {
        Date date = stringToDate(dateStr);
        if(date != null) {
            return date.getTime();
        }
        return 0;
    }

}
