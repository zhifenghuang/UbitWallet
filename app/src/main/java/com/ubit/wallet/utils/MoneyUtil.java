package com.ubit.wallet.utils;


import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 描述结果
 *
 * @author xyx on 2019/7/6 0006
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */
public class MoneyUtil {

    public static DecimalFormat fnum = new DecimalFormat("##0.00");
    public static DecimalFormat fnum8 = new DecimalFormat("##0.00000000");


    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(String value) {
        if (value == null || "".equals(value)) {
            value = "0.00";
        }
        return value;
//        return fnum.format(new BigDecimal(value));
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney8(String value) {
        if (value == null || "".equals(value)) {
            value = "0.00000000";
        }
        return value;
//        return fnum.format(new BigDecimal(value));
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return fnum.format(bigDecimal);
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney(BigDecimal value) {
        if (value == null) {
            value = new BigDecimal(0);
        }
        return fnum.format(value);
    }

    /**
     * 格式化金额
     *
     * @param value
     * @return
     */
    public static String formatMoney8(BigDecimal value) {
        if (value == null) {
            value = new BigDecimal(0);
        }
        return fnum8.format(value);
    }

    /**
     * 金额相加
     *
     * @param valueStr 基础值
     * @param addStr   被加数
     * @return
     */
    public static String moneyAdd(String valueStr, String addStr) {
        if (TextUtils.isEmpty(valueStr)) {
            valueStr = "0";
        }
        if (TextUtils.isEmpty(addStr)) {
            addStr = "0";
        }
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return fnum.format(value.add(augend));
    }

    /**
     * 金额相加
     *
     * @param value  基础值
     * @param addStr 被加数
     * @return
     */
    public static BigDecimal moneyAddD(BigDecimal value, String addStr) {
        if (TextUtils.isEmpty(addStr)) {
            return new BigDecimal(0);
        }
        BigDecimal augend = new BigDecimal(addStr);
        return value.add(augend);
    }

    /**
     * 金额相加
     *
     * @param value  基础值
     * @param augend 被加数
     * @return
     */
    public static BigDecimal moneyAdd(BigDecimal value, BigDecimal augend) {
        return value.add(augend);
    }

    /**
     * 金额相减
     *
     * @param valueStr 基础值
     * @param minusStr 减数
     * @return
     */
    public static String moneySub(String valueStr, String minusStr) {
        if (TextUtils.isEmpty(valueStr)) {
            valueStr = "0.00";
        }
        if (TextUtils.isEmpty(minusStr)) {
            minusStr = "0.00";
        }
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(minusStr);
        return fnum.format(value.subtract(subtrahend));
    }

    /**
     * 金额相减
     *
     * @param valueStr   基础值
     * @param subtrahend 减数
     * @return
     */
    public static String moneySub(String valueStr, BigDecimal subtrahend) {
        if (TextUtils.isEmpty(valueStr)) {
            return "0.00";
        }
        BigDecimal value = new BigDecimal(valueStr);
        return fnum.format(value.subtract(subtrahend));
    }

    /**
     * 金额相减
     *
     * @param value      基础值
     * @param subtrahend 减数
     * @return
     */
    public static BigDecimal moneySub(BigDecimal value, BigDecimal subtrahend) {
        return value.subtract(subtrahend);
    }


    /**
     * 金额相乘
     *
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static String moneyMul(String valueStr, String mulStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     *
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static String moneyMul(int valueStr, String mulStr) {
        if (TextUtils.isEmpty(mulStr)) {
            return "0.00";
        }
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     *
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static String moneyMul(String valueStr, float mulStr) {
        if (TextUtils.isEmpty(valueStr)) {
            return "0.00";
        }
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     *
     * @param value    基础值
     * @param mulValue 被乘数
     * @return
     */
    public static BigDecimal moneyMul(BigDecimal value, BigDecimal mulValue) {
        return value.multiply(mulValue);
    }

    /**
     * 金额相乘
     *
     * @param valueStr 基础值
     * @param mulStr   被乘数
     * @return
     */
    public static BigDecimal moneyMulD(int valueStr, String mulStr) {
        if (TextUtils.isEmpty(mulStr)) {
            return new BigDecimal(0);
        }
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return value.multiply(mulValue);
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     *
     * @param valueStr  基础值
     * @param divideStr 被乘数
     * @return
     */
    public static String moneydiv(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     *
     * @param valueStr  基础值
     * @param divideStr 被乘数
     * @return
     */
    public static String moneydiv8(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum8.format(value.divide(divideValue, 8, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     *
     * @param value       基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneydiv(BigDecimal value, BigDecimal divideValue) {
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static int moneyComp(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        //0:等于    >0:大于    <0:小于
        int result = value.compareTo(compValue);
        return result;
    }

    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static int moneyComp(BigDecimal valueStr, BigDecimal compValueStr) {
        //0:等于    >0:大于    <0:小于
        int result = valueStr.compareTo(compValueStr);
        return result;
    }

    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param value        (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static int moneyComp(BigDecimal value, String compValueStr) {
        BigDecimal compValue = new BigDecimal(compValueStr);
        //0:等于    >0:大于    <0:小于
        return value.compareTo(compValue);
    }

    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr  (需要消费金额)
     * @param compValue (可使用金额)
     * @return
     */
    public static int moneyComp(String valueStr, BigDecimal compValue) {
        BigDecimal value = new BigDecimal(valueStr);
        //0:等于    >0:大于    <0:小于
        return value.compareTo(compValue);
    }

    /**
     * 金额乘以，省去小数点
     *
     * @param valueStr 基础值
     * @return
     */
    public static String moneyMulOfNotPoint(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = fnum.format(value.multiply(mulValue));
        return fnum.format(value.multiply(mulValue)).substring(0, valueStr.length() - 3);
    }

    /**
     * 给金额加逗号切割
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        } catch (Exception e) {
            return str;
        }

    }


    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(int money) {
        if (money != 0) {
            DecimalFormat df2 = new DecimalFormat("###.00");
            String moneyStr = df2.format(money * 1.0d);
            if (moneyStr.startsWith(".")) {
                moneyStr = "0" + moneyStr;
            }
            return moneyStr;
        } else {
            return "0.00";
        }
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(long money) {
        if (money != 0) {
            DecimalFormat df2 = new DecimalFormat("###.00");
            String moneyStr = df2.format(money * 1.0d);
            if (moneyStr.startsWith(".")) {
                moneyStr = "0" + moneyStr;
            }
            return moneyStr;
        } else {
            return "0.00";
        }
    }


    /**
     * 获取百分比
     *
     * @param percent
     * @return
     */
    public static String getPercentUpGrade(float percent) {
        String s1;
        if (percent > 0) {
            if (percent < 0.01) {
                s1 = "0.01%";
            } else if (percent < 1) {
                BigDecimal bigDecimal = new BigDecimal(percent);
                DecimalFormat df2 = new DecimalFormat("###.00");
                s1 = "0" + df2.format(bigDecimal) + "%";
            } else if (percent >= 100) {
                s1 = "100%";
            } else {
                s1 = (int) percent + "%";
            }
        } else {
            s1 = "0%";
        }
        return s1;
    }

    /**
     * 获取百分比
     *
     * @param smallValue
     * @return
     */
    public static float getPercentUpGrade(long smallValue, long bigValue) {
        float percent;
        if (bigValue <= 0) {
            percent = 0;
        }
        percent = (100 * 1.0f * smallValue) / bigValue;
        if (percent > 0) {
            if (percent < 0.01) {
                percent = 0.01f;
            } else if (percent >= 100) {
                percent = 100;
            }
        }
        return percent;
    }

    /**
     * 格式化M币
     *
     * @param money
     * @return
     */
    public static String formatMb(String money) {
        if (!TextUtils.isEmpty(money)) {
            return money;
        } else {
            return "0";
        }
    }


    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatCoupon(String money) {
        if (money == null) {
            return "0";
        } else {
            return money;
        }
    }

    /**
     * 获取金额折扣
     *
     * @param highMoney
     * @param lowMoney
     * @return
     */
    public static int getDiscount(String highMoney, String lowMoney) {
        if (highMoney == null || lowMoney == null) {
            return 0;
        }
        BigDecimal highValue = new BigDecimal(highMoney);
        BigDecimal lowValue = new BigDecimal(lowMoney);
        BigDecimal value = lowValue.divide(highValue, 2, BigDecimal.ROUND_HALF_UP);
        value = value.multiply(new BigDecimal(100));
        return value.intValue();
    }

    /**
     * 关于赚PRD优化文档
     *
     * @param groupPrice 活动价
     * @param price      日常价
     * @param vipPrice   VIP价
     * @param costPrice  成本价
     * @param isActive   限时折扣
     * @return
     */
    public static String getEarnMoney(String groupPrice, String price, String vipPrice, String costPrice, int isActive) {
        String earn = "";
        if (isActive == 1) {
            earn = moneySub(groupPrice, vipPrice);
//            if (BaseUserUtils.getInstance().getUserType() == 1) {
//                earn = moneySub(groupPrice, vipPrice);
//            } else if (BaseUserUtils.getInstance().getUserType() > 1) {
//                earn = moneyAdd(
//                        moneySub(groupPrice, vipPrice),
//                        moneyMul(
//                                moneySub(vipPrice, costPrice),
//                                0.6f));
//            }
        } else {
            earn = moneySub(price, vipPrice);
//            if (BaseUserUtils.getInstance().getUserType() == 1) {
//                earn = moneySub(price, vipPrice);
//            } else if (BaseUserUtils.getInstance().getUserType() > 1) {
//                earn = moneyAdd(
//                        moneySub(price, vipPrice),
//                        moneyMul(
//                                moneySub(vipPrice, costPrice),
//                                0.6f));
//            }
        }
        return earn;
    }
}
