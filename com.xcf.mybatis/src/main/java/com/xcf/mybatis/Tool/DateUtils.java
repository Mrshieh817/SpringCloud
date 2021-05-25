package com.xcf.mybatis.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年5月25日 上午9:43:58 
*/
@Slf4j
public class DateUtils {
	public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = CHINESE_DATETIME_FORMAT_LINE;
    //    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
//    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//    private static final SimpleDateFormat TIME_FORMAT_TWO = new SimpleDateFormat("HHmmssSSS");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
    private static final SimpleDateFormat TIME_FORMAT_SFM = new SimpleDateFormat("yyyyMMddHHmmss");
//    private static final SimpleDateFormat TIME_FORMAT_ThTWO = new SimpleDateFormat("yyyyMMdd");
//    private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);


    /**
     * 默认日期时间格式化
     */
    public static String datetimeFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 日期默认长格式化
     *
     * @param date
     * @return
     */
    public static String datetimeFormatLong(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 默认日期时间解析
     *
     * @throws ParseException
     */
    public static Date parseDatetime(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }


    /**
     * 将时间戳字符串转化为时间
     *
     * @param date
     * @return
     */
    public static Date getDateByDateString(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            return new Date(Long.parseLong(date));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 转化为实践
     *
     * @param date
     * @return
     */
    public static Date getDateByStringTwo(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            Date parse = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
            return parse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将 yyyy-MM-dd HH:mm:ss 转化为实践
     *
     * @param date
     * @return
     */
    public static Date getDateByString(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return parse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String parseDatetimeYYYYmmdd(Date date) {
        return TIME_FORMAT_SFM.format(date);
    }

    /**
     * 默认时间格式化
     */
    public static String timeFormat(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * 默认日期格式化
     */
    public static String dateFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    /**
     * 默认日期解析
     *
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    /**
     * 将日期增加月份或者减少月份(上一月:n = -1,,下一月:n = 1)
     *
     * @return
     */
    public static Date changeDateMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }


    /**
     * 当前时间往后推(多少年)
     *
     * @param n       年
     * @param regular
     * @return
     * @throws Exception
     */
    public static Date getAfterYearTime(int n, Date regular) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(regular);
        c.add(Calendar.YEAR, -n);
        return c.getTime();
    }

    /**
     * 当前时间往后推
     *
     * @param n
     * @param regular
     * @return
     * @throws Exception
     */
    public static Date getAfterTime(int n, Date regular) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(regular);
        c.add(Calendar.MINUTE, n);
//        return sdf.parse(sdf.format(c.getTime()));
        return c.getTime();
    }

    /**
     * 求前天的时间
     *
     * @return
     */
    public static Date getBeforeYesterdayByDate(Date today) {
        //用当天的日期减去昨天的日期
        Date yesterdayDate = new Date(today.getTime() - 172800000L);
        return yesterdayDate;
    }

    /**
     * 求昨天的时间
     *
     * @return
     */
    public static Date getYesterdayByDate(Date today) {
        //用当天的日期减去昨天的日期
        Date yesterdayDate = new Date(today.getTime() - 86400000L);
        return yesterdayDate;
    }


    /**
     * 求7天前的时间
     *
     * @return
     */
    public static Date getOneWeakByDate(Date today) {
        //用当天的日期减去昨天的日期
        Date yesterdayDate = new Date(today.getTime() - 604800000L);
        return yesterdayDate;
    }


    /**
     * 求30天前的时间
     *
     * @return
     */
    public static Date getOneMonthByDate(Date today) {
        //用当天的日期减去昨天的日期
        Date yesterdayDate = new Date(today.getTime() - 2592000000L);
        return yesterdayDate;
    }

    /**
     * 得到指定天数的开始时间
     *
     * @param today
     * @param day   几天前
     * @return
     */
    public static Date getNumDayByDate(Date today, long day) {
        Date yesterdayDate = new Date(getStartTimeOfToday(today).getTime() - (24 * 3600 * day * 1000));
        return yesterdayDate;
    }


    /**
     * 得到指定天数的结束时间
     *
     * @param today
     * @param day   几天前
     * @return
     */
    public static Date getNumEndDayByDate(Date today, long day) {
        Date yesterdayDate = new Date(getLastTimeOfToday(today).getTime() - (24 * 3600 * day * 1000));
        return yesterdayDate;
    }


    /**
     * 获取日期2017-10-10 的long值  即：2015-01-01 23：59：59的 long值
     *
     * @return
     */
    public static Long getLastLongDay(String date) {
        Date parse = parse(date, CHINESE_DATE_FORMAT_LINE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static String getTimeDay(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    /**
     * 时间格式化
     *
     * @return
     */
    public static String getTimeMS(Date date) {
        return new SimpleDateFormat("HHmmssSSS").format(date);
    }

    public static String getTimeNYR(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(date);
    }

    public static Date parse(String dateString, String pattern) {
        pattern = StringUtils.isEmpty(pattern) ? DEFAULT_DATE_FORMAT : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            log.warn(StrFormatter.format("解析时间失败:{format:{},str:{}}", pattern, dateString), e);
            return null;
//            throw new RuntimeException("parse String[" + dateString + "] to Date faulure with pattern[" + pattern
//                + "].");
        }
    }

    /**
     * 字符串转换成日期类型数据
     *
     * @param dateStr eg:2019-11-10
     * @param format  eg:"yyyy-MM-dd HH:mm:ss"
     * @return
     * @throws ParseException
     */
    public static Date fromString(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return today;
    }


    /**
     * 获取今天的最后时间 23:59:59
     */
    public static Date getLastTimeOfToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取指定天数后的时间
     */
    public static Date getDayTime(Date date, int day) {
        long l = date.getTime() + (3600 * 1000 * 24 * day);
        return new Date(l);
    }

    /**
     * 获取今天的开始时间 00:00:00
     *
     * @return
     */
    public static Date getStartTimeOfToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 自定义增加时间
     *
     * @return
     */
    public static Date addTimes(Date date, Integer years, Integer months, Integer weeks, Integer days, Integer hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (years != null && years != 0) cal.set(Calendar.YEAR, years);
        if (months != null && months != 0) cal.set(Calendar.MONTH, months);
        if (weeks != null && weeks != 0) cal.set(Calendar.WEEK_OF_YEAR, weeks);
        if (days != null && days != 0) cal.set(Calendar.DAY_OF_YEAR, days);
        if (hours != null && hours != 0) cal.set(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 增加时间格式验证
     * (格式：年-月-周-日-时)
     **/
    public static boolean timePattern(String effectiveTimes) {
        String pattern = "\\d{1,2}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(effectiveTimes);
        return m.matches();
    }

    /**
     * <h2>两个时间段中的所有日期的开始时间</h2>
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getStartTimeOfTimeRange(Date startDate, Date endDate) {

        if (startDate.after(endDate)) {
            return Collections.emptyList();
        }
        List<Date> list = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // startDate为00：00：00时，将其加入返回列表
        if (Objects.equals(startDate, cal.getTime())) {
            list.add(cal.getTime());
        }
        while (cal.getTime().before(getStartTimeOfToday(endDate))) {
            // 日历翻页 下一天的开始时间
            cal.add(Calendar.DAY_OF_MONTH, 1);
            list.add(cal.getTime());
        }
        return list;
    }


    /**
     * 获取昨天的开始时间 00:00:00
     *
     * @return
     */
    public static Date getQiantianStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取昨天的开始时间 00:00:00
     *
     * @return Date
     */
    public static Date getYesterdayStartTime() {
        Date yesterday = getYesterdayByDate(new Date());
        return getStartTimeOfToday(yesterday);
    }

    /**
     * 获取昨天的结束时间 23:59:59
     *
     * @return Date
     */
    public static Date getYesterdayLastTime() {
        Date yesterday = getYesterdayByDate(new Date());
        return getLastTimeOfToday(yesterday);
    }


    /**
     * 获取前几分钟的时间
     *
     * @param today
     * @return
     */
    public static Date getFrontDate(Date today, long minute) {
        Date date = new Date(today.getTime() - (minute * 60 * 1000));
        return date;
    }

    /**
     * 获取后几分钟的时间
     *
     * @param today
     * @return
     */
    public static Date getFrontDateAfter(Date today, long minute) {
        Date date = new Date(today.getTime() + (minute * 60 * 1000));
        return date;
    }

    /**
     * 得到两个时间之间的差值(秒)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDateDifference(Date startDate, Date endDate) {
        long end = endDate.getTime();
        long start = startDate.getTime();
        if (start > end) {
            return 0;
        }
        return (int) ((end - start) / 1000);
    }


    /**
     * utc时间转成local时间
     *
     * @param utcTime
     * @return
     */
    public static String utcToLocal(String utcTime) {
        if (utcTime == null || utcTime.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        String localTime = sdf.format(utcDate.getTime());
        return localTime;
    }

    /**
     * local时间转换成UTC时间
     *
     * @param localTime
     * @return
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }


    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
            || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间是否在一个时间区间(小时) true:在规定时间段,,false:不在
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date parse2 = null;
        Date parse = null;
        Date parse1 = null;
        try {
            //格式化当前时间格式
            parse2 = df.parse(df.format(nowTime));
            //定义开始时间
            parse = df.parse(beginTime);
            //定义结束时间
            parse1 = df.parse(endTime);
        } catch (Exception e) {
            System.out.println("日期转换异常!");
            e.printStackTrace();
            return false;
        }
        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(parse2);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(parse);
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(parse1);
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @return java.lang.String
     * @Author tianhao
     * @Description //获取年份
     * @Date 18:25 2020/6/10
     * @Param []
     **/
    public static String getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR) + "";
    }

    /**
     * @return java.lang.String
     * @Author tianhao
     * @Description //获取月份
     * @Date 18:25 2020/6/10
     * @Param []
     **/
    public static String getMonth() {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.MONTH) + 1) + "";
    }

    /**
     * @return java.lang.String
     * @Author tianhao
     * @Description //获取日期（只有日期，无年月）
     * @Date 18:25 2020/6/10
     * @Param []
     **/
    public static String getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH) + "";
    }

    /**
     * @return java.lang.Boolean
     * @Author tianhao
     * @Description //判断是否是周末
     * @Date 14:09 2020/7/13
     * @Param []
     **/
    public static Boolean isWeekend() {
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

}
