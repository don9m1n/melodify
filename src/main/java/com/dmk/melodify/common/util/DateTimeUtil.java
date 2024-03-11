package com.dmk.melodify.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    // 현재 날짜를 format에 맞게 변환해서 반환해주는 메서드
    public static String getCurrentDateFormat(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }
}
