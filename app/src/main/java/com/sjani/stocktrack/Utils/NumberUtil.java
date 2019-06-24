package com.sjani.stocktrack.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
    static final long MILLION = 1000000L;
    static final long BILLION = 1000000000L;
    static final long TRILLION = 1000000000000L;
    static DecimalFormat df = new DecimalFormat("#.##");

    public static String truncateNumber(String string) {
        Long x = Long.parseLong(string);
        return x < MILLION ?  String.valueOf(x) :
                x < BILLION ?  x / MILLION + "M" :
                        x < TRILLION ? x / BILLION + "B" :
                                x / TRILLION + "T";
    }

    public static String formatPrice(String string){
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(Double.parseDouble(string));
    }

    public static String formatPercent(Double pr){
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(pr)+"%";
    }

    public static Double getDoublePrice(String string){
        String[] change = string.split("%");
        return Double.parseDouble(change[0]);
    }

}
