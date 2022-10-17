package com.alpha.finance.models;

import com.alpha.finance.services.OnMessageReceivedListener;

import java.util.Map;

public class Consumer {
    Map<String, Integer> messageLog;
    OnMessageReceivedListener listener;

    public Consumer(Map<String, Integer> messageLog, OnMessageReceivedListener listener) {
        this.messageLog = messageLog;
        this.listener = listener;
    }

    public Map<String, Integer> getMessageLog() {
        return messageLog;
    }

    public OnMessageReceivedListener getListener() {
        return listener;
    }
}
