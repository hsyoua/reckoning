package cn.yugutou.reckoning.utils;

import cn.yugutou.reckoning.exception.CustomException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  chenqun
 * @create  2021/8/8 23:23
 * @desc  时间类型转换
 **/
public class DateUtil {


    //传入Date类型，转化成SQL时间戳  yyyy-MM-dd HH:mm:ss
    public static Timestamp getSqlTimestamp(Date date){
       return new Timestamp(date.getTime());
    }
    //传入字符串类型，转化成SQL时间戳 yyyy-MM-dd HH:mm:ss
    public static Timestamp getSqlTimestamp(String time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse= df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return getSqlTimestamp(parse);

    }


}
