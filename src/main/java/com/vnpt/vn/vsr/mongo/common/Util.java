package com.vnpt.vn.vsr.mongo.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
    public static final Locale CURRENT_FORMAT_LOCALE = Locale.US;

    public static void main(String [] args) {
        String formattedNumber = String.format("%,d", 123456);
        System.out.println(formattedNumber);
    }

    public static String parseDoubleToString(double b) {
        if(b % 1 == 0) {
            String bStr = b + "";
            return bStr.split("\\.")[0];
        }
        else {
            return b + "";
        }
    }

    public static String formatStringNumber(String number) {
        String result = number;
        if(!number.isEmpty()) {
            if(number.contains(".")) {
                BigDecimal bigDecimal = new BigDecimal(number);
                if(isIntegerValue(bigDecimal)) {
                    long val = bigDecimal.longValue();
                    result = String.format("%,d",val);
                }
                else {
                    result = NumberFormat.getNumberInstance(CURRENT_FORMAT_LOCALE).format(bigDecimal);
                }

            }
            else {
                long val = Long.parseLong(number);
                result = String.format("%,d",val);
            }
        }
        return result;
    }

    public static String formatStringNumberPhay(String number) {
        String result = number;
        if(!number.isEmpty()) {
            if(number.contains(",")) {
                BigDecimal bigDecimal = new BigDecimal(number.replaceAll(",","."));
                if(isIntegerValue(bigDecimal)) {
                    long val = bigDecimal.longValue();
                    result = String.format("%,d",val);
                }
                else {
                    result = NumberFormat.getNumberInstance(CURRENT_FORMAT_LOCALE).format(bigDecimal);
                }

            }
            else {
                long val = Long.parseLong(number);
                result = String.format("%,d",val);
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

    public static String concat(Object... numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            sb.append(numbers[i]);
        }
        return sb.toString();
    }

    // chưa thêm trường hợp start < 0
    public static String substrSQL(String s, int start, int len) {
        if (start == 0) {
            start++;
        }
        int length = s.length(), end;
        if (start + len == length) {
            end = length;
        } else if (start + len > length) {
            end = ++length;
        } else {
            end = start + len;
        }
        return s.substring(--start, --end);
    }

    public static String plusMonthForYYYYQ(String timeId) {
        long year = Long.parseLong(timeId.substring(0, 4));
        long quy = Long.parseLong(timeId.substring(4));
        quy++;
        year += (quy - 1) / 4;
        quy = (quy - 1) % 4 + 1;
        return concat(year, quy);
    }

    public static String dateFormat(String timeId, int submitType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter resultFormatter = null;
        LocalDate localDate = null;
        switch (submitType) {
            case 4:
                localDate = LocalDate.parse(timeId + "0101", formatter);
                localDate = localDate.plusMonths(-12);
                resultFormatter = DateTimeFormatter.ofPattern("yyyy");
                break;
            case 99:
            case 2:
            case 7:
            case 8:
                localDate = LocalDate.parse(timeId + "01", formatter);
                if (submitType == 99) localDate = localDate.plusMonths(1);
                if (submitType == 2) localDate = localDate.plusMonths(-1);
                if (submitType == 7) localDate = localDate.plusMonths(-2);
                if (submitType == 8) localDate = localDate.plusMonths(-6);
                resultFormatter = DateTimeFormatter.ofPattern("yyyyMM");
                break;
            case 5:
                localDate = LocalDate.parse(timeId, formatter);
                localDate = localDate.plusDays(-1);
                resultFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                break;
            case 3:
                return plusMonthForYYYYQ(timeId);
        }
        return localDate.format(resultFormatter);
    }
}
