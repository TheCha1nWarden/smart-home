package com.smarthome.app.model;

public class WeatherRequest {
    private String latitude;
    private String longitude;
    private String format;

    public WeatherRequest(String latitude, String longitude, String format) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.format = format;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
