package com.browse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

@RestController
@AllArgsConstructor
public class KafkaPro {

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

	//kafka Generate data
    @GetMapping("/send/{messge}")
    public String send(@PathVariable String messge) {
        try {
            //(Full file path), encoding format, read data
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("OS_project2.csv"), "utf-8"));
            String line = null;
            //Write data to kafka
            while((line=reader.readLine())!=null){
                kafkaTemplate.send("topic1", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Data generated successfully";
    }
}
