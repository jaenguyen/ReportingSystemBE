package com.vnpt.vn.vsr.mongo.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {

    public static final Locale CURRENT_FORMAT_LOCALE = Locale.US;

    public static void main(String[] args) {
        String formattedNumber = String.format("%,d", 123456);
        System.out.println(formattedNumber);
    }

    public static String parseDoubleToString(double b) {
        if (b % 1 == 0) {
            String bStr = b + "";
            return bStr.split("\\.")[0];
        } else {
            return b + "";
        }
    }

    public static String formatStringNumber(String number) {
        String result = number;
        if (!number.isEmpty()) {
            if (number.contains(".")) {
                BigDecimal bigDecimal = new BigDecimal(number);
                if (isIntegerValue(bigDecimal)) {
                    long val = bigDecimal.longValue();
                    result = String.format("%,d", val);
                } else {
                    result = NumberFormat.getNumberInstance(CURRENT_FORMAT_LOCALE).format(bigDecimal);
                }
            } else {
                long val = Long.parseLong(number);
                result = String.format("%,d", val);
            }
        }
        return result;
    }

    public static String formatStringNumberPhay(String number) {
        String result = number;
        if (!number.isEmpty()) {
            if (number.contains(",")) {
                BigDecimal bigDecimal = new BigDecimal(number.replaceAll(",", "."));
                if (isIntegerValue(bigDecimal)) {
                    long val = bigDecimal.longValue();
                    result = String.format("%,d", val);
                } else {
                    result = NumberFormat.getNumberInstance(CURRENT_FORMAT_LOCALE).format(bigDecimal);
                }
            } else {
                long val = Long.parseLong(number);
                result = String.format("%,d", val);
            }
        }
        return result;
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    public static Double roundDouble(Double val) {
        if (val % 1 > 0)
            return new BigDecimal(val.toString()).setScale(3, RoundingMode.HALF_UP).doubleValue();
        else
            return val;
    }

    public static String dateFormat(String dateInput, int type) {
        long count = 0;
        DateTimeFormatter formatter = null;
        LocalDate localDate = null;
        String resultString;
        if (type == 1 || type == 2 || type == 7 || type == 8) {
            if (type == 1) {
                dateInput += "-01";
                count = 1;
            }
            if (type == 2) count = -1;
            if (type == 7) count = -2;
            if (type == 8) count = -6;
            formatter = DateTimeFormatter.ofPattern("yyyyMM");
            localDate = LocalDate.parse(dateInput, formatter);
            localDate = localDate.plusMonths(count);
        } else if (type == 4) {
            count = -12;
            formatter = DateTimeFormatter.ofPattern("yyyy");
            localDate = LocalDate.parse(dateInput, formatter);
            localDate = localDate.plusMonths(count);
        } else if (type == 5) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            localDate = LocalDate.parse(dateInput, formatter);
            localDate = localDate.plusDays(count);
        } else if (type == 3) {
//            localDate = LocalDate.parse(dateInput, formatter);
//            localDate = localDate.plusDays(count);
//            resultFormatter = DateTimeFormatter.ofPattern("yyyyQ");
        }
        resultString = localDate.format(formatter);
        return resultString;
    }
}
