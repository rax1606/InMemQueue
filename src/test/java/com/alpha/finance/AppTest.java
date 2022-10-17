package com.alpha.finance;

import com.alpha.finance.services.Broker;
import com.alpha.finance.services.BrokerImpl;
import com.sun.source.tree.AssertTree;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertThrows;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    Broker broker;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */

    public void testCreateQueue()
    {
        broker = new BrokerImpl(3);
        try {
            broker.createQueue("q1",5,2);
        }catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertTrue(broker.getQueues().containsKey("q1"));
    }

    public void testCreateDuplicateQueue() throws Exception {
        broker = new BrokerImpl(3);
        broker.createQueue("q1",5,2);

        Exception exception = assertThrows(
                Exception.class
                ,() -> {broker.createQueue("q1",5);}
        );
        String expectedMsg = "Queue name alreday taken. Try a differnt name";
        assertTrue(expectedMsg.equals(exception.getMessage()));
    }


}
