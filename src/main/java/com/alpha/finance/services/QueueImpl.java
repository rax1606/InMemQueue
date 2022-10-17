package com.alpha.finance.services;

import com.alpha.finance.models.QueueElement;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.LocalDateTime;

public class QueueImpl implements Queue
{
    private QueueElement[] elements;      // array to store queue elements
    private int front;      // front points to the front element in the queue
    private int rear;       // rear points to the last element in the queue
    private int capacity;   // maximum capacity of the queue
    private int count;      // current size of the queue
    private int timeToLive = 60;  // Time to live in seconds for now, defaulted to 1 minute

    private OnMessageReceivedListener onMessageReceivedListener;

    // Constructor to initialize a queue
    QueueImpl(int capacity)
    {
        elements = new QueueElement[capacity];
        this.capacity = capacity;
        front = 0;
        rear = -1;
        count = 0;
    }

    public QueueImpl(int capacity, int timeToLive) {
        elements = new QueueElement[capacity];
        this.capacity = capacity;
        front = 0;
        rear = -1;
        count = 0;
        this.timeToLive = timeToLive;
    }

    // Utility function to add an item to the queue
    public int enqueue(String msg) throws InterruptedException {
        synchronized (this){
            Gson gson = new Gson();
            JsonObject item = gson.fromJson(msg,JsonObject.class);
            QueueElement elem = new QueueElement(item);
            System.out.print(Thread.currentThread().getName() + " is trying to produce -> ");
            Thread.sleep(1*1000);
            // check for queue overflow
            if (isFull())
            {
                System.out.println("Inserting " + elem.getData().toString()+". Overflow...");
                notify();
                return -1;
            }
            rear = (rear + 1) % capacity;
            System.out.println("Inserting " + elem.getData().toString() );
            elements[rear] = elem;
            count++;
            notify();
            return 0;
        }
    }

    @Override
    // Utility function to dequeue the front element
    public String dequeue() throws InterruptedException {
        synchronized (this) {
            System.out.print(Thread.currentThread().getName() + " is trying to consume -> ");
            Thread.sleep(10*1000);
            // check for queue underflow
            if (isEmpty()) {
                System.out.println("Underflow...");
                notify();
                return null;
            }

//            System.out.print("position-"+front);
            QueueElement element = elements[front];
            front = (front + 1) % capacity;
            count--;

            Duration messageAge = Duration.between(element.getInceptionTime(), LocalDateTime.now());
            System.out.print("MessageAge-"+messageAge.toSeconds() +" : ");
            if (messageAge.toSeconds() > timeToLive) {
                System.out.println("Message expired with id : " + element.getData().get("id") );
//                return dequeue();
                notify();
                return null;
            }
            else {
                System.out.println("Consumed message with id : " + element.getData().get("id") );
                notify();
                return element.toString();
            }
        }
    }


    // Utility function to return the front element of the queue
    public String peek()
    {
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }
        return elements[front].toString();
    }

    // Utility function to return the size of the queue
    public int size() {
        return count;
    }

    // Utility function to check if the queue is empty or not
    public boolean isEmpty() {
        return (size() == 0);
    }

    // Utility function to check if the queue is full or not
    public boolean isFull() {
        return (size() == capacity);
    }

    public void registerOnMessageReceivedListener(OnMessageReceivedListener listener)
    {
        this.onMessageReceivedListener = listener;
    }
}