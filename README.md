AMQ Injector
============

This tool is for pushing text-files into ActiveMQ queues as messages. 

I had a need to populate a message queue with sample message for development/testing purposes. To ease this effort, I dropped the messages into files (one per message) and then wrote this utility to populate an AMQ queue. Pretty basic stuff but it proved to be quite handy.

Right now it only supports pushing messages to a queue, but this is easily extended.

It reads from a directory using a file mask and grabs each of those files and pushes it to the specified queue.

Basic usage:
amqInjectorApp inject -dir <directory> -mask <filemask/glob> -url <url to server> -port <port> -queue <queuename>

Sample usage: 
java -cp target/alternateLocation/\*:target/amqInjector-1.0-SNAPSHOT.jar:target/alternateLocation/commons-cli-1.2.jar:target/alternateLocation/commons-io-2.4.jar  com.joelhainley.java.amqInjector.amqInjectorApp inject -dir /tmp/sampleMessages -mask \*.xml -url tcp://localhost -port 61616 -queue queue2


TODO : 
- extend to support Topics?
- better packaging of Jars?
