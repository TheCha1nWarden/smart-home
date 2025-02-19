package com.smarthome.app.model;

public class Device {
    private Long id;
    private String name;
    private Status status;

    // Конструкторы, геттеры и сеттеры
    public Device(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status isStatus() {
        return status;
    }

    public void setOn(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

