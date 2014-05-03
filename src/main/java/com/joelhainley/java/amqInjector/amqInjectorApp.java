package com.joelhainley.java.amqInjector;

import main.java.com.joelhainley.java.amqInjector.AMQInjector;
import org.apache.commons.cli.*;
import org.apache.commons.cli.Parser.*;

/**
 * Hello world!
 */
public class amqInjectorApp {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        Option optHelp =  OptionBuilder.withArgName("help")
                                       .hasArg(false)
                                       .withDescription("print this message")
                                       .create("help");

        Option optInject =  OptionBuilder.withArgName("inject")
                .hasArg(false)
                .withDescription("inject messages")
                .create("inject");

        Option optSourceDir = OptionBuilder.withArgName("dir")
                                            .hasArg(true)
                                            .withDescription("the directory to retrieve the messages from")
                                            .create("dir");

        Option optFileMask = OptionBuilder.withArgName("mask")
                                          .hasArg(true)
                                         .withDescription("the glob to use for file retrieval")
                                         .create("mask");

        Option optUrl = OptionBuilder.withArgName("url")
                                          .hasArg(true)
                                         .withDescription("the url/ipaddress to connect to")
                                         .create("url");

        Option optPort = OptionBuilder.withArgName("port")
                                          .hasArg(true)
                                         .withDescription("the port to connect to")
                                         .create("port");

        Option optQueue = OptionBuilder.withArgName("queue")
                                          .hasArg(true)
                                         .withDescription("the name of the queue to inject the messages into")
                                         .create("queue");



        options.addOption(optHelp);
        options.addOption(optSourceDir);
        options.addOption(optInject);
        options.addOption(optFileMask);
        options.addOption(optUrl);
        options.addOption(optPort);
        options.addOption(optQueue);


        CommandLineParser parser = new BasicParser();

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            if(args[0].toString().equals("inject")){
                System.out.println("we are injecting...");
                if(!line.hasOption("dir")){
                    System.out.println("you must supply the dir option");
                }
                if(!line.hasOption("mask")){
                    System.out.println("you must supply the mask option");
                }
                if(!line.hasOption("url")){
                    System.out.println("you must supply the url option");
                }
                if(!line.hasOption("port")){
                    System.out.println("you must supply the port option");
                }
                if(!line.hasOption("queue")){
                    System.out.println("you must supply the queue option");
                }

                String dir = line.getOptionValue("dir");
                String mask = line.getOptionValue("mask");
                String url = line.getOptionValue("url");
                String port = line.getOptionValue("port");
                String queue = line.getOptionValue("queue");

                AMQInjector injector = new AMQInjector();
                injector.injectMessagesIntoQueue(dir, mask, url, port, queue);
            }
            else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("help", options);
            }

        }
        catch(ParseException exp){
            System.err.println("Error: " + exp.getMessage());
        }


//        String sourceDir =
//        , String fileMask
//        , String jmsServerUrl
//        , String jmsServerPort
//        , String jmsQueueName
//        Option opt
    }
}