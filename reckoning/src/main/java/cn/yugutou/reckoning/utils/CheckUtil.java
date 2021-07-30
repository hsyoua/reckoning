package cn.yugutou.reckoning.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    /**
     * 验证手机号是否合法
     * @return
     */
    public boolean isMobileNO(String mobile){
        if(mobile.length() != 11)
        {
            return false;
        }else{
            /**
             * 移动号段正则表达式1
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(17[2,8])|(18[2-4,7-8])|(198))\\d{8}|(1705)\\d{7}$";

            /**
             * 联通号段正则表达式
             * */
            String pat2  = "^((13[0-2])|(145)|(15[5-6])|(166)|(17[1,5,6])|(18[5,6]))\\d{8}|(1709)\\d{7}$";

            /**
             * 电信号段正则表达式
             */
            String pat3  = "^((133)|(149)|(153)|(17[3,7])|(18[0,1,9])|(199))\\d{8}$";

            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(mobile);
            boolean isMatch1 = match1.matches();
            if(isMatch1){
                return true;
            }

            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(mobile);
            boolean isMatch2 = match2.matches();
            if(isMatch2){
                return true;
            }

            Pattern pattern3 = Pattern.compile(pat3);
            Matcher match3 = pattern3.matcher(mobile);
            boolean isMatch3 = match3.matches();
            if(isMatch3){
                return true;
            }

            Pattern pattern4 = Pattern.compile(pat4);
            Matcher match4 = pattern4.matcher(mobile);
            boolean isMatch4 = match4.matches();
            if(isMatch4){
                return true;
            }
            return false;
        }
    }

    /**
     * 校验接收的参数是不是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
