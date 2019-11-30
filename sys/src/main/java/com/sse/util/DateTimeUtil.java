package com.sse.util;

import sun.security.provider.SHA;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 14:05
 */

public class DateTimeUtil {

    public static final String SHANGHAI_OFF_TIME = "GMT+8";
    // 默认使用系统当前时区
    private static ZoneId ZONE = ZoneId.of(SHANGHAI_OFF_TIME);

    public DateTimeUtil(ZoneId zone) {
        ZONE = zone;
    }

    public static Instant getInstant(Object d) {
        if (d instanceof Date) {
            return ((Date) d).toInstant();
        } else if (d instanceof Long) {
            return new Date((Long) d).toInstant();
        }
        return null;
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZONE).toLocalDate();
    }

    public static Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZONE).toInstant());
    }

    /**
     * 将Date转换成LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZONE);
    }

    /**
     * 将LocalDateTime转换成Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of(SHANGHAI_OFF_TIME)));
    }

    /**
     * 将Date转换成LocalTime
     */
    public static LocalTime date2LocalTime(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * 获取Period（时间段）
     */
    public static Period localDateDiff(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        return p;
    }

    /**
     * 获取Duration（持续时间）
     */
    public static Duration localTimeDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }

    /**
     * 获取时间间隔（毫秒）
     */
    public static long millisDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }

    /**
     * 获取时间间隔（秒）
     */
    public static long secondDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.getSeconds();
    }

    /**
     * 获取时间间隔（天）
     */
    public static long daysDiff(LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }

    /**
     * 创建一个新的日期，它的值为当月的第一天
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 创建一个新的日期，它的值为当月的最后一天
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 创建一个新的日期，它的值为当年的第一天
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 创建一个新的日期，它的值为今年的最后一天
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * 获取当前时间
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZONE);
    }

    /**
     * 判断当前时间是否在时间范围内
     */
    public static boolean isInRange(Date startTime, Date endTime) throws Exception {
        LocalDateTime now = getCurrentLocalDateTime();
        LocalDateTime start = toLocalDateTime(startTime);
        LocalDateTime end = toLocalDateTime(endTime);
        return start.isBefore(now) && end.isAfter(now) || start.isEqual(now) || end.isEqual(now);
    }
}
