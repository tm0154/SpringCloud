package server.commom.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * The type Big decimal util.
 *
 * @Description
 * @Author tieminPan
 * @Date 2018 /10/13 22:23
 * @Param
 * @return
 */
public class BigDecimalUtil {

    public static Integer LENGTH = 2;

    /**
     * Add big decimal.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the big decimal
     * @desc 加法
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2);
    }

    /**
     * Sub big decimal.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the big decimal
     * @desc 减法
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2);
    }

    /**
     * Mul big decimal.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the big decimal
     * @desc 乘法
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2);
    }

    /**
     * Div big decimal.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the big decimal
     * @desc 除法
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        /**
         *@desc 四舍五入, 保留2位小数
         */
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较大小  v1>v2 1 v1=v2 0 v1<v2 -1
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return integer integer
     */
    public static Integer compareTo(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2);
    }


    /**
     * 格式化金额
     *
     * @param obj the obj
     * @return ###, ###.00
     */
    public static String formatToString(BigDecimal obj) {
        if (null == obj) {
            return "0.00";
        }
        if (obj.compareTo(BigDecimal.ZERO) == 0) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        String result = decimalFormat.format(obj);
        Integer res = result.indexOf(".");
        if (res == 0) {
            result = "0" + result;
        }
        return result;

    }

    /**
     * 金额转换
     *
     * @param amount the amount
     * @return integer integer
     */
    public static Integer bigDecimalToInteger(String amount) {
        if (amount == null) {
            return 0;
        }

        /**
         *
         *@desc 金额转化为分为单位 处理包含, ￥ 或者$的金额
         */
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return Integer.parseInt(amLong.toString());
    }

}
