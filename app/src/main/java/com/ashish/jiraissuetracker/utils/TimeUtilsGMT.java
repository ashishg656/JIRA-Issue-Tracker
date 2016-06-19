package com.ashish.jiraissuetracker.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ashish on 19/06/16.
 */
public class TimeUtilsGMT extends TimeUtils {

    public static String getIssueDetailTime(String timeStamp) {
        try {
            return getChatTime(timeStamp) + "\n" + getChatDateDisplayed(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
            return "Moments ago";
        }
    }

    public static String getChatTime(String timestamp) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    PARSER_FORMAT_FOR_DATES);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = simpleDateFormat.parse(timestamp);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());

            String am_or_pm = calendar.get(Calendar.AM_PM) == Calendar.AM ? " AM"
                    : " PM";

            String minutes = Integer.toString(calendar.get(Calendar.MINUTE));

            if (calendar.get(Calendar.MINUTE) < 10) {
                minutes = "0" + Integer.toString(calendar.get(Calendar.MINUTE));
            }

            String hour = Integer.toString(calendar.get(Calendar.HOUR));
            if (hour.equals("0"))
                hour = "12";

            String simpleDate = hour + ":" + minutes + am_or_pm;
            return simpleDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Just Now";
    }

    public static String getChatDateDisplayed(String timestamp) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    PARSER_FORMAT_FOR_DATES);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date dateObj = simpleDateFormat.parse(timestamp);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateObj.getTime());

            SpannableStringBuilder builder1 = new SpannableStringBuilder();

            int day = calendar.get(Calendar.DAY_OF_MONTH);

            String dayName = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            builder1.append(dayName + ", ");

            String date = getMonth(calendar.get(Calendar.MONTH)) + " "
                    + String.format("%02d", day);

            SpannableString dateSpannable = new SpannableString(date);
            dateSpannable.setSpan(
                    new StyleSpan(android.graphics.Typeface.BOLD), 0,
                    date.length(), 0);
            builder1.append(dateSpannable);

            String year = " " + calendar.get(Calendar.YEAR);
            SpannableString yearSpannable = new SpannableString(year);
            yearSpannable.setSpan(
                    new StyleSpan(android.graphics.Typeface.BOLD), 0,
                    year.length(), 0);
            yearSpannable.setSpan(new RelativeSizeSpan(1.5f), 0, year.length(),
                    0);
            builder1.append(yearSpannable);

            return builder1.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
