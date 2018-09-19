package com.dtds.mobileplatform.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/*****************************************
 * @description:数字相关处理工具
 * @author:lixiaohui
 * @date: 2017/10/25
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class NumberUtil {

    private static final String TAG = "NumberUtil";

    /**
     * 安全不报错的强制转换成int
     *
     * @param numStr
     * @return
     */
    public static int parseIntSafely(String numStr) {
        int num = 0;
        if (null == numStr || "".equals(numStr)) {
            return num;
        }
        try {
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return num;
    }

    /**
     * 安全不报错的强制转换成long
     *
     * @param numStr
     * @return
     */
    public static long parseLongSafely(String numStr) {
        long num = 0;
        if (null == numStr || "".equals(numStr)) {
            return num;
        }
        try {
            num = Long.parseLong(numStr);
        } catch (NumberFormatException e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return num;
    }

    /**
     * 安全不报错的强制转换成double
     *
     * @param numStr
     * @return
     */
    public static float parseFloatSafely(String numStr) {
        float num = 0;
        if (null == numStr || "".equals(numStr)) {
            return num;
        }
        try {
            num = Float.parseFloat(numStr);
        } catch (NumberFormatException e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return num;
    }

    /**
     * 安全不报错的强制转换成double
     *
     * @param numStr
     * @return
     */
    public static double parseDoubleSafely(String numStr) {
        double num = 0;
        if (null == numStr || "".equals(numStr)) {
            return num;
        }
        try {
            num = Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return num;
    }

    /**
     * 保留指定位数将String强制转换成double数据
     *
     * @param numStr
     * @param scale
     * @return
     */
    public static double parseDoubleSafely(String numStr, int scale) {
        double num = 0;
        if (null == numStr || "".equals(numStr)) {
            return num;
        }
        try {
            num = new BigDecimal(numStr).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return num;
    }

    /**
     * 格式化显示
     *
     * @param num
     * @param retainCount
     * @return
     */
    public static String formatNum(double num, int retainCount) {
        StringBuilder patternSb = new StringBuilder("#,##0.");
        for (int i = 0; i < retainCount; i++) {
            patternSb.append("0");
        }
        DecimalFormat df = new DecimalFormat(patternSb.toString());
        return df.format(num);
    }

    /**
     * 格式化显示只保留两位数，保留0，如5.00
     *
     * @param num
     * @return
     */
    public static String formatNumDefault(double num) {
        return formatNum(num, 2);
    }

    /**
     * 四舍五入格式化double类型数 如：3.05显示为3.1
     *
     * @param numOfStr    待处理的数
     * @param retainCount 需要保留的位数
     * @return
     * @author lxh
     * @since 2015-6-12
     */
    public static String formatWithRound(String numOfStr, int retainCount) {
        if (TextUtils.isEmpty(numOfStr)) {
            return "";
        }
        BigDecimal dData = new BigDecimal(numOfStr);
        double data = dData.setScale(retainCount, BigDecimal.ROUND_HALF_UP).doubleValue();
        return "" + data;
    }

    /**
     * 四舍五入格式化double类型数 如：3.05显示为3.1
     *
     * @param num
     * @param retainCount
     * @return
     */
    public static String formatWithRound(double num, int retainCount) {
        return formatWithRound(String.valueOf(num), retainCount);
    }

    /**
     * 高精度计算两个数的和
     *
     * @param numA
     * @param numB
     * @return
     * @author lxh
     * @since 2015-9-10
     */
    public static BigDecimal addWithBig(String numA, String numB) {
        if (TextUtils.isEmpty(numA) || TextUtils.isEmpty(numB)) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.add(bigDecimalB);
    }

    /**
     * 高精度计算两个数的和
     *
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal addWithBig(double numA, double numB) {
        return addWithBig(String.valueOf(numA), String.valueOf(numB));
    }

    /**
     * 高精度计算两个数的差值
     *
     * @param numA
     * @param numB
     * @return
     * @author lxh
     * @since 2015-9-10
     */
    public static BigDecimal subtractWithBig(String numA, String numB) {
        if (TextUtils.isEmpty(numA) || TextUtils.isEmpty(numB)) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.subtract(bigDecimalB);
    }

    /**
     * 高精度计算两个数的差值
     *
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal subtractWithBig(double numA, double numB) {
        return subtractWithBig(String.valueOf(numA), String.valueOf(numB));
    }

    /**
     * 高精度计算两个数的乘积
     *
     * @param numA
     * @param numB
     * @return
     * @author lxh
     * @since 2015-9-10
     */
    public static BigDecimal multiplyWithBig(String numA, String numB) {
        if (TextUtils.isEmpty(numA) || TextUtils.isEmpty(numB)) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.multiply(bigDecimalB);
    }

    /**
     * 高精度计算两个数的乘积
     *
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal multiplyWithBig(double numA, double numB) {
        return multiplyWithBig(String.valueOf(numA), String.valueOf(numB));
    }

    /**
     * 高精度计算两个数的除数
     *
     * @param numA
     * @param numB
     * @param retainCount 保留多少位数
     * @return
     */
    public static BigDecimal divideWithBig(String numA, String numB, int retainCount) {
        if (TextUtils.isEmpty(numA) || TextUtils.isEmpty(numB)) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.divide(bigDecimalB).setScale(retainCount, BigDecimal.ROUND_HALF_UP);
    }

}
