package com.dayuan.util;

import java.util.Random;

public class MyRandomUtil {

    /**
     * 产生多少位的随机数
     *
     * @param num
     * @return
     */
    public static String getRandom(int num) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandom(100));
    }
}
