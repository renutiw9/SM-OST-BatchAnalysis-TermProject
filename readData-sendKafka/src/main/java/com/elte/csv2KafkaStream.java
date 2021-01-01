package com.elte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class csv2KafkaStream {

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/streamData")
    public String streamData() {
    	
    	StringBuilder htmlBuilder = new StringBuilder();
    	htmlBuilder.append("<html>");
    	htmlBuilder.append("<head><title>KAFKA STREAMING</title></head>");
    	htmlBuilder.append("<body><h2>STREAMING DATA - CSV to KAFKA</h2></body>");
    	
    	ArrayList<String> data = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(
            		new InputStreamReader(
            				new FileInputStream("D:/ELTE_Project/BatchAnalysis/FinalProject/part1/src/main/resources/OS_project1.csv"), 
            				"utf-8"));
            String rowData = "";
            int i =0;
            //Stream Data to Kafka row by row
            //Check if data is there or not, then proceed
            
            while((rowData=reader.readLine())!=null){
                kafkaTemplate.send("streaming", rowData);
                data.add("Row #"+Integer.toString(i)+"  [ "+rowData.toString() +" ]</br>");
                i++;
            }
            htmlBuilder.append(data);
            htmlBuilder.append("</html>");
        	String html = htmlBuilder.toString();
        	reader.close();
            return html; 
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
