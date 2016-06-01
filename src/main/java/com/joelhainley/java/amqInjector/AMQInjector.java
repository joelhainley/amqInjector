package main.java.com.joelhainley.java.amqInjector;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import java.io.*;
import java.util.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * Created by jhainley on 3/27/14.
 */
public class AMQInjector {

    public void injectMessagesIntoQueue(String dir
                                      , String mask
                                      , String serverUrl
                                      , String serverPort
                                      , String queueName){

        System.out.println("dir " + dir);
        System.out.println("mask " + mask);
        System.out.println("serverUrl " + serverUrl);
        System.out.println("serverPort " + serverPort);
        System.out.println("queueName " + queueName);


        File folder = new File(dir);
        if(!folder.exists()){
            System.out.println("Specified folder does not exist");
            return;
        }

        FileFilter filter = new WildcardFileFilter(mask);
        File[] fileList = folder.listFiles(filter);


        // if we found files then let's go ahead and connect to the server
        // and do some stuff

        if(fileList.length == 0){
            System.out.println("No files found");
            return;
        }

        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(serverUrl + ":" + serverPort);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(queueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


            for(int i=0;i<fileList.length;i++){

                System.out.println(fileList[i]);

                // Create a messages
                TextMessage message = session.createTextMessage(getFileAsString(fileList[i]));

                // Tell the producer to send the message
                producer.send(message);
            }
            // Clean up
            session.close();
            connection.close();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    private String getFileAsString(File file){


        List<String> lines = null;

        try {

            FileInputStream fis = new FileInputStream(file);

            lines = IOUtils.readLines(fis);

            return(listToString(lines));
        }
        catch(Exception e){

        }

        return "";
    }

    private String listToString(List<String> stringList){
        StringBuilder sb = new StringBuilder();

        Iterator iter = stringList.iterator();

        while(iter.hasNext()){
            sb.append(iter.next());
        }

        return(sb.toString());
    }
}
