package com.leon.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LeonMac
 * @description
 */

public class NumberUtils {

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /*
     * @description:
     * 判断字符串是否是数值格式
     * @param string
     * @return boolean
     */
    public static boolean isDigit(String string) {
        if (string == null || string.trim().equals("")) {
            return false;
        }
        return string.matches("^\\d+$");
    }

    /*
     * @description:
     * 将一个小数精确到指定位数
     * @param num
     * @param scale
     * @return double
     */
    public static double scale(double num, int scale) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /*
    * @description:
    * 从字符串中根据正则表达式寻找，返回找到的数字数组
    * @param value
	* @param regex
    * @return java.lang.Double[]
    */
    public static Double[] searchNumber(String value, String regex) {
        ArrayList<Double> doubles = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            for (int i = 1; i <= matchResult.groupCount(); i++) {
                doubles.add(Double.valueOf(matchResult.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }

    public static String generateCode(int len) {
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }





}
