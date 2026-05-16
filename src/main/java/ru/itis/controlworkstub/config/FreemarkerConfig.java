package ru.itis.controlworkstub.config;

import freemarker.template.Configuration;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String TIME_PATTERN = "HH:mm";

    public FreemarkerConfig(Configuration freeMarkerConfiguration) {
        freeMarkerConfiguration.setDateTimeFormat(DATE_TIME_PATTERN);
        freeMarkerConfiguration.setDateFormat(DATE_PATTERN);
        freeMarkerConfiguration.setTimeFormat(TIME_PATTERN);
    }
}
