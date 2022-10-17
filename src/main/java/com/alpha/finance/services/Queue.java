package com.alpha.finance.services;

import com.alpha.finance.models.QueueElement;

public interface Queue {
    int enqueue(String msg) throws InterruptedException;
    String dequeue() throws InterruptedException;
    String peek();
}
