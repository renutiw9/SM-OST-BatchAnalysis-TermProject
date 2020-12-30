package com.elte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.util.Random;

@RestController
@AllArgsConstructor
public class KafkaPro {

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;


	//kafka
    @GetMapping("/send/{messge}")
    public String send(@PathVariable String messge) {

        String temp[] = new String[]{"192.168.1.10","192.168.1.10","192.168.1.10","192.168.1.10"} ;
        for(int i = 0 ; i< temp.length ; i++){
            Random r = new Random();
            String sendMessage = "1/"+temp[i]+"/"+r.nextInt(1000)  ;
            kafkaTemplate.send("topic1", sendMessage);
        }
        return "Data Generated Successfully.";
    }
}
