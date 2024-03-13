package com.dmk.melodify.common.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoderUtil {
    public static String encode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

}
