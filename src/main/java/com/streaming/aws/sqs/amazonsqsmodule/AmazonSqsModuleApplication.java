package com.streaming.aws.sqs.amazonsqsmodule;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;  

@SpringBootApplication
@RestController
public class AmazonSqsModuleApplication {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(AmazonSqsModuleApplication.class);

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @GetMapping("/send/{message}")
    public void sendMessageToQueue(@PathVariable String message){
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
    }


    public static void main(String[] args) {
        try {
            System.setProperty("aws.region", "us-east-1");
            SpringApplication.run(AmazonSqsModuleApplication.class, args);
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }


}
