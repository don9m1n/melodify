package com.dmk.melodify.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.directory}")
    private String fileDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * http://localhost/file/membmer/cover.png 로 요청이 들어오면,
         * /Users/dongmin/Desktop/melodify_file/member/cover.png 에서 찾음
         */
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:///" + fileDirectory + "/");
    }
}
