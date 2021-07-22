package cn.yugutou.reckoning.utils;

import java.util.Random;

public class NumberGenerator {
    public NumberGenerator() {
    }
    //随机数工具方法
    public static long getNumber(int length) {
        StringBuilder buf = new StringBuilder();
        Random random = new Random();
        /*开头不为0,建议数据量较少时只放开部分，比如1至3开头的数，等业务达到一定数量时，再逐步放开剩余的号码段，由于是固定位数，总数量一定，生成的数越多，重复的几率越大**/
        int firstNumber = random.nextInt(9) + 1;
        buf.append(firstNumber);

        for(int i = 0; i < length - 1; ++i) {
            buf.append(random.nextInt(10));
        }

        return Long.valueOf(buf.toString());
    }
}