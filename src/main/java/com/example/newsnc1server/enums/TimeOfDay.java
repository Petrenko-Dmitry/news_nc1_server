package com.example.newsnc1server.enums;

public enum TimeOfDay {
    MORNING("06:00", "11:59"),
    DAY("12:00", "17:59"),
    EVENING("18:00", "23:59"),
    UNKNOWN("00:00", "23:59");

    private final String startHour;
    private final String endHour;

    TimeOfDay(String startHour, String endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getEndHour() {
        return endHour;
    }
}
