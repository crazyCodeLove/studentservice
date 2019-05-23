package com.sse.util;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-05-23 14:05
 */

public class DateTimeUtil {
    // 默认使用系统当前时区
    private static ZoneId ZONE = ZoneId.systemDefault();

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

    /**
     * 将Date转换成LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Object d) {
        Instant instant = getInstant(d);
        return LocalDateTime.ofInstant(instant, ZONE);
    }

    /**
     * 将Date转换成LocalDate
     */
    public static LocalDate date2LocalDate(Object d) {
        Instant instant = getInstant(d);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
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
     * 将LocalDate转换成Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDateTime转换成Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
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
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_MONTH, 1));
    }

    /**
     * 创建一个新的日期，它的值为当月的最后一天
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_MONTH,
                temporal.range(ChronoField.DAY_OF_MONTH).getMaximum()));
    }

    /**
     * 创建一个新的日期，它的值为当年的第一天
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, 1));
    }

    /**
     * 创建一个新的日期，它的值为今年的最后一天
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(
                (temporal) -> temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum()));
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
        LocalDateTime start = date2LocalDateTime(startTime);
        LocalDateTime end = date2LocalDateTime(endTime);
        return start.isBefore(now) && end.isAfter(now) || start.isEqual(now) || end.isEqual(now);
    }
}
