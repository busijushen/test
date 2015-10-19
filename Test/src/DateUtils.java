/**
 * Copyright (c) Hitachi Systems, Ltd。 2014。 All rights reserved。
 * Please read the associated COPYRIGHTS file for more details。
 *
 * THE SOFTWARE IS PROVIDED BY Hitachi Systems, Ltd。,
 * WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT。
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDER BE LIABLE FOR ANY
 * CLAIM, DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES。
 */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {


    /** 格式長さ：４ */
    public static final int LENGTH4 = 4;

    /** 格式長さ：６ */
    public static final int LENGTH6 = 6;

    /** 格式長さ：７ */
    public static final int LENGTH7 = 7;

    /** 格式長さ：８ */
    public static final int LENGTH8 = 8;

    /** 格式長さ：１０ */
    public static final int LENGTH10 = 10;

    /** 格式長さ：１２ */
    public static final int LENGTH12 = 12;

    /** 格式長さ：１４ */
    public static final int LENGTH14 = 14;

    /** 格式長さ：１６ */
    public static final int LENGTH16 = 16;

    /** 格式長さ：１7 */
    public static final int LENGTH17 = 17;

    /** 格式長さ：１９ */
    public static final int LENGTH19 = 19;

    /** 格式：yyyyMM */
    public static SimpleDateFormat format4 = new SimpleDateFormat("yyyy");

    /** 格式：yyyyMM */
    public static SimpleDateFormat format6 = new SimpleDateFormat("yyyyMM");

    /** 格式：yyyyMMdd */
    public static SimpleDateFormat format8 = new SimpleDateFormat("yyyyMMdd");

    /** 格式：yyyyMMddHHmm */
    public static SimpleDateFormat format12 = new SimpleDateFormat("yyyyMMddHHmm");

    /** 格式：yyyyMMddHHmmss */
    public static SimpleDateFormat format14 = new SimpleDateFormat("yyyyMMddHHmmss");

    /** 格式：yyyy/MM */
    public static SimpleDateFormat formatSlash7 = new SimpleDateFormat("yyyy/MM");

    /** 格式：yyyy/MM/dd */
    public static SimpleDateFormat formatSlash10 = new SimpleDateFormat("yyyy/MM/dd");

    /** 格式：yyyy/MM/dd hh:mm */
    public static SimpleDateFormat formatSlash16 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    /** 格式：yyyy-MM-dd HH:mm:ss */
    private static SimpleDateFormat formatSlash17 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

    /** 格式：yyyy/MM/dd hh:mm:ss */
    public static SimpleDateFormat formatSlash19 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** 格式：yyyyMMddHHmmssSSS */
    public static SimpleDateFormat formatAll = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /** 格式：yyyy-MM-dd HH:mm:ss */
    public static SimpleDateFormat formatymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 格式：yyyy-MM-dd HH:mm:ss */
    public static SimpleDateFormat formatymd = new SimpleDateFormat("yyyy-MM-dd");

    /** 最大日付 9999/12/31 23:59:59 */
    public static String MAX_DATE = "20371231235959";

    public enum PrecisionUnit{ MINUTE, DAY, MONTH };


    /**
     * 期間のMinute数を取得する。
     * @param start
     * @param end
     * @return 期間のMinute数
     */
    public static long getMinute(Date start, Date end) {

    	long diff = end.getTime() - start.getTime();
        return diff / (60 * 1000);
    }

    /**
     * 期間のMinute数を取得する。
     * @param start
     * @param end
     * @return 期間のMinute数
     */
    public static long getMinuteForLifeLog(Date start, Date end) {

        long diff = end.getTime() - start.getTime();
        return diff / (60 * 1000) + 1;
    }

    /**
     * 日数差を計算する
     *
     * @param startDay 開始日付(yyyyMMdd)
     * @param endDay   終了日付(yyyyMMdd)
     * @return 日付差
     * @throws Exception
     */
    public static long DayDifference(String startDay, String endDay)
            throws Exception {

        SimpleDateFormat dTime = null;

        dTime = new SimpleDateFormat("yyyyMMdd");

        startDay = startDay.substring(0, 8);
        endDay   = endDay.substring(0, 8);

        Date dStartDate = dTime.parse(startDay);
        Date dEndDate = dTime.parse(endDay);

        long startTime = dStartDate.getTime();
        long endTime = dEndDate.getTime();

        long diff = Math.abs(endTime - startTime);

        diff /= 3600 * 1000 * 24;

        return diff;
    }


    /**
     * 分間数差を計算する
     *
     * @param startDay 開始日付(YYYYMMDDHHmm)
     * @param endDay   終了日付(YYYYMMDDHHmm)
     * @return 日付差
     * @throws Exception
     */
    public static long minuteDifference(String startDay, String endDay)
            throws Exception {

        SimpleDateFormat dTime = null;

        dTime = new SimpleDateFormat("yyyyMMddHHmm");

        startDay = startDay.substring(0, 12);
        endDay   = endDay.substring(0, 12);

        Date dStartDate = dTime.parse(startDay);
        Date dEndDate = dTime.parse(endDay);

        long startTime = dStartDate.getTime();
        long endTime = dEndDate.getTime();

        long diff = Math.abs(endTime - startTime);

        diff /= 60 * 1000;

        return diff;
    }

    /**
     * システムDateを返す。
     * @return　システムDate
     */
    public static Date getSystemDate() {

        Date date = new Date();

        return date;
    }

    /**
     * ｵﾌｾｯﾄによりUTC日付算出
     *
     * @param offset
     *            ｵﾌｾｯﾄ（例：+0900）
     * @param date
     *            指定された日付文字列(yyyyMMddHHmm)
     * @return UTC日付
     */
    public static Date getUtcTimeFromOffset(String offset, String date) {

        Date newDate = toDate(date, "yyyyMMddHHmm");

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(newDate);

        // 整数化
        int signNumber = Integer.parseInt(offset.substring(0, 3));

        // 算出
        cal.add(Calendar.HOUR, -signNumber);

        return new Date(cal.getTimeInMillis());
    }

    /**
     * ｵﾌｾｯﾄによりUTC日付からﾛｰｶﾙ日付を算出
     *
     * @param offset
     *            ｵﾌｾｯﾄ（例：+0900）
     * @param date
     *            指定された日付文字列(yyyyMMddHHmm)
     * @return UTC日付
     */
    public static Date getLocalTimeFromOffset(String offset, Date date) {

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        // 整数化
        int signNumber = Integer.parseInt(offset.substring(0, 3));

        // 算出
        cal.add(Calendar.HOUR, signNumber);

        return new Date(cal.getTimeInMillis());
    }

    /**
     * ｵﾌｾｯﾄによりUTC日付算出
     *
     * @param offset ｵﾌｾｯﾄ（例：+0900）
     * @param date   ﾛｰｶﾙ日付
     * @return UTC日付
     */
    public static Date getUtcTimeFromOffset(String offset, Date date) {

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        // 整数化
        int signNumber = Integer.parseInt(offset.substring(0, 3));

        // 算出
        cal.add(Calendar.HOUR, -signNumber);

        return new Date(cal.getTimeInMillis());
    }

    public static Date getUtcTimeFromOffset2(String offset, String date) {

        Date newDate = toDate(date, "yyyyMMddHHmm");

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(newDate);

        if("-".equals(offset.substring(0, 1))){

        }
        // 整数化
        int signNumber = Integer.parseInt(offset.substring(1, 3));

//        // 算出
//        if("-".equals(offset.substring(0, 1))){
//
//        	cal.add(Calendar.HOUR, signNumber);
//        }else{
//
//            cal.add(Calendar.HOUR, -signNumber);
//        }

        return new Date(cal.getTimeInMillis());
    }

    public static Date getUtcTimeFromOffset3(String offset, String date,String formatString) {

            Date newDate = toDate(date, formatString);

            // ｶﾚﾝﾀﾞｰ取得
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(newDate);

            if(!StringUtil.isBlank(offset)){
                // 整数化
                int signNumber = Integer.parseInt(offset.substring(1, 3));
                // 算出
                cal.add(Calendar.HOUR, -signNumber);
            }

            return new Date(cal.getTimeInMillis());

    }

    /**
     * ｵﾌｾｯﾄによりUTC日付算出
     *
     * @param offset
     *            ｵﾌｾｯﾄ（例：+0900）
     * @param date
     *            指定された日付文字列(yyyyMMddHHmm)
     * @return UTC日付
     */
    public static Date getNextUnitDate(Date date, PrecisionUnit type) {

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        // 算出
        if (type.equals(PrecisionUnit.MINUTE)) {

            cal.add(Calendar.MINUTE, 1);

        } else if (type.equals(PrecisionUnit.DAY)) {

            cal.add(Calendar.DATE, 1);

        } else {

            cal.add(Calendar.MONTH, 1);

        }


        return new Date(cal.getTimeInMillis());
    }

    /**
     * ｵﾌｾｯﾄによりUTC日付算出
     *
     * @param offset
     *            ｵﾌｾｯﾄ（例：+0900）
     * @param date
     *            指定された日付文字列(yyyyMMddHHmm)
     * @return UTC日付
     */
    public static Date getPreUnitDate(Date date, PrecisionUnit type) {

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        // 算出
        if (type.equals(PrecisionUnit.MINUTE)) {

            cal.add(Calendar.MINUTE, -1);

        } else if (type.equals(PrecisionUnit.DAY)) {

            cal.add(Calendar.DATE, -1);

        } else {

            cal.add(Calendar.MONTH, -1);

        }


        return new Date(cal.getTimeInMillis());
    }

    /**
     * タイムズンDateを返す。
     * @return　システムDate
     */
    public static Date getLocalSystemDate(String tzId) {

        TimeZone tz = TimeZone.getTimeZone(tzId);
        DateFormat df = DateFormat.getInstance();
        df.setTimeZone(tz);
        Date date;
        try {
            date = formatSlash17.parse(df.format(getSystemDate()));
        } catch (ParseException e) {
            date = getSystemDate();
        }

        return date;
    }

    /**
     * 指定日付より、UTC時間を取得する
     * @param tzId タイムズン
     * @param now 日付
     * @return UTC日付
     */
    public static Date getUTCDate(String tzId, Date now) {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tzId));
        cal.setTime(now);
        // 指定時間をUTC時間に変換
        Timestamp newTime = new Timestamp(cal.getTimeInMillis() -
                cal.get(Calendar.ZONE_OFFSET) - cal.get(Calendar.DST_OFFSET));

        return newTime;
    }

    /**
     * 指定日付より、UTC時間を取得する
     * @param tzId タイムズン
     * @param now 日付
     * @return UTC日付のﾀｲﾑｽﾀﾝﾌﾟ
     */
    public static Timestamp getUTCTimestamp(String tzId, Date now) {

        return new Timestamp(getUTCDate(tzId, now).getTime());
    }

    /**
     * ﾃﾞｨﾌｫﾙﾄﾀｲﾑｿﾞﾝより、UTC時間を取得する
     * @return UTC日付のﾀｲﾑｽﾀﾝﾌﾟ
     */
    public static Timestamp getUTCTimestamp() {

        String tzId = TimeZone.getDefault().getID();
        Date now = getSystemDate();
        Date utc = getUTCDate(tzId, now);

        return new Timestamp(utc.getTime());
    }

    /**
     * 指定UTC日付より、ローカル日付を取得する
     * @param tzId タイムズン
     * @param now 日付
     * @return ローカル日付
     */
    public static Date UTC2LocalDate(String tzId, Date now) {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tzId));
        cal.setTime(now);
        // 指定時間をUTC時間に変換
        Timestamp newTime = new Timestamp(cal.getTimeInMillis() +
                cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));

        return newTime;
    }

    /**
     * 指定日付ミリ秒より、UTC時間を取得する
     * @param tzId タイムズン
     * @param now 日付
     * @return UTC Timestamp
     */
    public static Timestamp getUTCDate(String tzId, long time) {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tzId));

        // 指定時間をUTC時間に変換
        Timestamp newTime = new Timestamp(time -
                cal.get(Calendar.ZONE_OFFSET) - cal.get(Calendar.DST_OFFSET));

        return newTime;
    }
    /**
     * 日付データから日付文字列に変換する
     *
     * @param date
     * @param stringLenth
     * @return 変換後の日付
     * @since 01-00
     */
    public static String parseDate2String(Date date, int stringLenth) {
        if (date == null) {
            return "";
        }
        switch (stringLenth) {
        case LENGTH19:
            return formatSlash19.format(date);
        case LENGTH16:
            return formatSlash16.format(date);
        case LENGTH14:
            return format14.format(date);
        case LENGTH12:
            return format12.format(date);
        case LENGTH10:
            return formatSlash10.format(date);
        case LENGTH8:
            return format8.format(date);
        case LENGTH7:
            return formatSlash7.format(date);
        case LENGTH6:
            return format6.format(date);
        case LENGTH4:
            return format4.format(date);
        case LENGTH17:
            return formatAll.format(date);
        default:
            break;
        }
        return null;
    }

    /**
     * 日付をﾌｫｰﾏｯﾄで文字列をﾌｫｰﾏｯﾄ
     *
     * @param date 入力日付
     * @return
     */
    public static String formatDate2String(Date date, String format) {

        // 存在ﾁｪｯｸ
        if (date == null) {
            return "";
        }

        if ("yyyy-MM-dd HH:mm:ss".equals(format)) {

            return formatymdhms.format(date);

        } else if ("yyyy-MM-dd".equals(format)) {

            return formatymd.format(date);
        }

        return null;
    }


    /**
     * 日付文字列から日付データに変換する
     *
     * @param string
     * yyyyMM、yyyyMMdd、yyyyMMddHHmm、yyyyMMddHHmmss
     * @param stringLenth
     * @return 変換後の日付
     * @since 01-00
     */
    public static Date praseString2Date(String string) {
        try {
            if (string == null || string.length() == 0) {
                return null;
            }
            switch (string.length()) {
            case LENGTH19:
            	return formatSlash19.parse(string);
            case LENGTH14:
                return format14.parse(string);
            case LENGTH12:
                return format12.parse(string);
            case LENGTH10:
                return formatSlash10.parse(string);
            case LENGTH8:
                return format8.parse(string);
            case LENGTH6:
                return format6.parse(string);
            case LENGTH4:
                return format4.parse(string);
            default:
                break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数値検査
     *
     * @param num 入力した文字列
     * @return 検査結果
     */
    public static String parseNumber(String num) {
        NumberFormat nf1 = NumberFormat.getInstance();
        String res = null;
        if (!StringUtil.isBlank(num)) {
            res = nf1.format(Double.parseDouble(num));
        }
        return res;
    }
    /**
     * yyyy/MM/dd 日付を取得する。
     * @param date
     * @return　日付　yyyy/MM/dd
     */
    public static String getFormattedYMD(String date) {

        StringBuilder sb = new StringBuilder();
        if(StringUtil.isBlank(date) || date.length() < 8) {
            return "";
        }
        sb.append(date.substring(0, 4));
        sb.append("/");
        sb.append(date.substring(4, 6));
        sb.append("/");
        sb.append(date.substring(6, 8));
        return sb.toString();
    }
    /**
     * 日付文字列をパースし日付オブジェクトに変換する。
     *
     * @param date 日付文字列
     * @param format 日付書式文字列
     * @return 変換結果の日付オブジェクト
     */
    public static Date toDate(String date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setLenient(false);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
           e.printStackTrace();
           return null;
        }
    }

    /**
     * 文字列からﾀｲﾑｽﾀﾝﾌﾟに変換する。
     *
     * @param date 入力文字列
     * @return ﾀｲﾑｽﾀﾝﾌﾟ
     */
    public static Timestamp string2Timestamp(String date) {

        Date newDate = toDate(date, "yyyyMMddHHmmss");

        return new Timestamp(newDate.getTime());

    }

    /**
     * 指定日数から現在日付以前または以降の日付を取得する。
     *
     * @param amount 日数
     * @return 算出した日付
     */
    public static Timestamp getAfterDateFrom(int amount) {

        Timestamp time = getUTCTimestamp();

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(time);

        // 前月取得
        cal.add(Calendar.DATE, amount);

        Date newDate = cal.getTime();

        return new Timestamp(newDate.getTime());

    }

    /**
     * yyyy-mm-dd hh:MM:ss→yyyymmddhhMMに変更
     * @param date16 変換する日付
     * @return 変換した日付
     */
    public static String date16ToDate12(String date16) {
        String date12 = null;
        if (date16 != null && date16.length() >= 16) {
            String year   = date16.substring(0, 4);
            String month  = date16.substring(5, 7);
            String day    = date16.substring(8, 10);
            String hour   = date16.substring(11, 13);
            String minute = date16.substring(14, 16);
            date12 = year + month + day + hour + minute;
        }

        return date12;
    }

    /**
     * 指定日数から現在日付以前または以降の日付を取得する。
     *
     * @param amount 日数
     * @return 算出した日付
     */
    public static Timestamp getNextDate(String date, String format) {

        Date inDate = toDate(date, format);
        Timestamp time = new Timestamp(inDate.getTime());

        // ｶﾚﾝﾀﾞｰ取得
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(time);

        // 翌日取得
        cal.add(Calendar.DATE, 1);

        Date newDate = cal.getTime();

        return new Timestamp(newDate.getTime());

    }
}
