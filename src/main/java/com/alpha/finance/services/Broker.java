package com.alpha.finance.services;

import com.alpha.finance.models.Consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Broker {
    Map<String, Queue> queues = new HashMap<>();
    Map<String, List<String>> producers = new HashMap<>();
    Map<String, Map<String , Consumer>> queueConsumerMap = new HashMap<>();
    void createQueue(String name, int capacity) throws Exception;
    void createQueue(String name, int capacity, int timeToLive) throws Exception;
    void subscribeQueue(String queueMane, String threadName) throws Exception;
    int getMaxRetries();
    void produce(String queueName, String msg) throws InterruptedException;
    String consume(String queueName) throws InterruptedException;
    default Map<String, Queue> getQueues(){
        return queues;
    }
}
