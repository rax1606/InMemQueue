package com.alpha.finance.models;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;

public class QueueElement {
    JsonObject data;
    LocalDateTime inceptionTime;

    public QueueElement(JsonObject data) {
        this.data = data;
        this.inceptionTime = LocalDateTime.now();
    }

    public JsonObject getData() {
        return data;
    }

    public LocalDateTime getInceptionTime() {
        return inceptionTime;
    }
}
