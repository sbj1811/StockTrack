package com.sjani.stocktrack.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberUtils {

    static final long MILLION = 1000000L;
    static final long BILLION = 1000000000L;
    static final long TRILLION = 1000000000000L;
    private static final String TAG = NumberUtils.class.getSimpleName();
    static DecimalFormat df = new DecimalFormat("#.##");

    public static String truncateNumber(String string) {
        Long x = Long.parseLong(string);
        return x < MILLION ? String.valueOf(x) :
                x < BILLION ? x / MILLION + "M" :
                        x < TRILLION ? x / BILLION + "B" :
                                x / TRILLION + "T";
    }

    public static String formatPrice(String string) {
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(Double.parseDouble(string));
    }

    public static String formatPercent(Double pr) {
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(pr) + "%";
    }

    public static Double getDoublePrice(String string) {
        String[] change = string.split("%");
        return Double.parseDouble(change[0]);
    }

    public static String convertTime(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("h:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertDate(String date, String type) {
        try {
            if (type.equals("daily weekly")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf.parse(date);
                return new SimpleDateFormat("dd").format(dateObj);
            } else if (type.equals("daily monthly")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf.parse(date);
                return new SimpleDateFormat("dd").format(dateObj);
            } else if (type.equals("daily three monthly")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf.parse(date);
                return new SimpleDateFormat("MMM").format(dateObj);
            } else if (type.equals("monthly one year")) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf.parse(date);
                return new SimpleDateFormat("MMM").format(dateObj);
            } else {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf.parse(date);
                return new SimpleDateFormat("dd").format(dateObj);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
