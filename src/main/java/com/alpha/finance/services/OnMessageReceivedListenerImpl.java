package com.alpha.finance.services;

public class OnMessageReceivedListenerImpl implements OnMessageReceivedListener{

    @Override
    public void onMessageReceived(String consumerName) {
        System.out.println("Sending msg to Consumer "+ consumerName);
    }
}
