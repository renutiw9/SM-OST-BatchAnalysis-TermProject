package com.elte;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @KafkaListener(topics = "topic1")
    public void onMessage(String message){
        System.out.println(message);
    }

}