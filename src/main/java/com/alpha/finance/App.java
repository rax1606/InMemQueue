package com.alpha.finance;

import com.alpha.finance.services.Broker;
import com.alpha.finance.services.BrokerImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Welcome to InMemQueue Demo");
        Broker broker = new BrokerImpl(10);
        try {
            broker.createQueue("q1",5,25);
//            broker.createQueue("q2",5,1);
        }catch (Exception e){
            e.printStackTrace();
        }

        // Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    broker.produce("q1","{\"id\":\"m1\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m2\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m3\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m4\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m5\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m6\"}");
                    Thread.sleep(1*1000);
                    broker.produce("q1","{\"id\":\"m7\"}");
                    broker.produce("q1","{\"id\":\"m8\"}");
                    broker.produce("q1","{\"id\":\"m9\"}");
                    broker.produce("q1","{\"id\":\"m10\"}");
                    broker.produce("q1","{\"id\":\"m11\"}");
                    broker.produce("q1","{\"id\":\"m12\"}");
                    broker.produce("q1","{\"id\":\"m13\"}");
                    broker.produce("q1","{\"id\":\"m14\"}");
                    broker.produce("q1","{\"id\":\"m15\"}");
                    broker.produce("q1","{\"id\":\"m16\"}");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Producer1");

        // create consumer threads
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    broker.consume("q1");
                    Thread.sleep(4*1000);
                    broker.consume("q1");
                    Thread.sleep(2*1000);
                    broker.consume("q1");
                    Thread.sleep(10*1000);
                    broker.consume("q1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Consumer1");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    broker.consume("q1");
                    Thread.sleep(4*1000);
                    broker.consume("q1");
                    Thread.sleep(4*1000);
                    broker.consume("q1");
                    broker.consume("q1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer2");

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    broker.subscribeQueue("q1","consumer3");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer3");

        t1.start();
        Thread.sleep(7*1000);
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

    }
}
