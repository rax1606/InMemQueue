package com.alpha.finance.services;

import com.alpha.finance.models.Consumer;

import java.util.HashMap;
import java.util.Map;

public class BrokerImpl implements Broker{
    int maxRetries;
    OnMessageReceivedListener listener;

    public BrokerImpl(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public void createQueue(String name, int capacity) throws Exception {
        if(!queues.containsKey(name)) {
            queues.put(name, new QueueImpl(capacity));
            queueConsumerMap.put(name, new HashMap<>());
        }
        else
            throw new Exception("Queue name alreday taken. Try a differnt name");
    }

    @Override
    public void createQueue(String name, int capacity, int timeToLive) throws Exception {
        if(!queues.containsKey(name)) {
            queues.put(name, new QueueImpl(capacity,timeToLive));
            queueConsumerMap.put(name, new HashMap<>());
        }
        else
            throw new Exception("Queue name alreday taken. Try a differnt name");
    }

    @Override
    public void subscribeQueue(String queueName, String threadName) throws Exception {
        if(queues.containsKey(queueName)){
            Map<String, Consumer> currentQueueConsumers = queueConsumerMap.get(queueName);
            if(currentQueueConsumers.containsKey(threadName)){
                throw new Exception(threadName +" has already subscribed "+ queueName);
            }
            else {
                Map<String, Consumer> newConsumer = new HashMap<>();
                newConsumer.put(threadName, new Consumer(new HashMap<>(), new OnMessageReceivedListenerImpl()));
                queueConsumerMap.put(queueName, newConsumer);
            }
        }
        else {
            throw new Exception("Queue doesn't exist");
        }

    }

    @Override
    public int getMaxRetries() {
        return maxRetries;
    }

    @Override
    public void produce(String queueName, String msg) throws InterruptedException {
        Queue queue = queues.get(queueName);
//        System.out.println("Trying to produce msg to "+queueName);
        int retries = 1;
        while( queue.enqueue(msg) == -1 && retries < maxRetries){
            retries++;
        }


        // for subscribers
        if(retries != maxRetries){
            Map<String, Consumer> currentQueueConsumers = queueConsumerMap.get(queueName);
            for(String consumerName : currentQueueConsumers.keySet()) {
                System.out.println("Consumer found for "+ consumerName);
                Consumer currentConsumer = currentQueueConsumers.get(consumerName);
                OnMessageReceivedListener currentlistener = currentConsumer.getListener();
                if( currentlistener != null){
                    currentlistener.onMessageReceived(consumerName);
                }
            }
        }
    }

    @Override
    public String consume(String queueName) throws InterruptedException {
        Queue queue = queues.get(queueName);
//        System.out.println("Trying to consume msg from "+queueName);
        return queue.dequeue();
    }

}
