package com.lab.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Visit {
    private Long id;
    private LocalDateTime visitTime;
    private String userAgent;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final ZoneId ZONE = ZoneId.of("Europe/Moscow");

    public Visit() {}

    public Visit(Long id, LocalDateTime visitTime, String userAgent) {
        this.id = id;
        this.visitTime = visitTime;
        this.userAgent = userAgent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getVisitTime() { return visitTime; }
    public void setVisitTime(LocalDateTime visitTime) { this.visitTime = visitTime; }

    public String getFormattedTime() {
        if (visitTime == null) return "-";
        // Время в БД хранится в UTC, добавляем +3 для Москвы
        return visitTime.atZone(ZoneId.of("UTC"))
                        .withZoneSameInstant(ZONE)
                        .toLocalDateTime()
                        .format(FORMATTER);
    }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
