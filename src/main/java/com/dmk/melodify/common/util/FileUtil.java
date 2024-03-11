package com.dmk.melodify.common.util;

import java.util.Optional;

public class FileUtil {

    // 파일의 확장자를 반환해주는 메서드
    public static String getExt(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1))
                .orElse("");
    }

}
