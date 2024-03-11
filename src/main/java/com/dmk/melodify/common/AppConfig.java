package com.dmk.melodify.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static String FILE_DIR_PATH;

    @Value("${file.directory}")
    public void setFileDirPath(String fileDirPath) {
        FILE_DIR_PATH = fileDirPath;
    }
}
