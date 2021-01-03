package com.elteSparkStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.bson.Document;
import org.json4s.DateFormat;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import scala.Tuple2;

public class SparkSteamingTransferToMongoDB {

	static int i = 0 ;
	static  MongoClient mongoDbConn =  MongoDBConnect.getConn();

	public static void main(String[] args) throws InterruptedException {
		String brokers = "localhost:9092";
		String topics = "streaming";
		SparkConf conf = new SparkConf().setAppName("als").setMaster("local[5]");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		JavaStreamingContext ssc = new JavaStreamingContext(jsc, Durations.seconds(1));

		Collection<String> topicsSet = new HashSet<String>(Arrays.asList(topics.split(",")));
		//kafka Related parameters, necessary! Missing will report an error
		Map<String, Object> kafkaParams = new HashMap<String, Object>();
		kafkaParams.put("metadata.broker.list", brokers) ;
		kafkaParams.put("bootstrap.servers", brokers);
		kafkaParams.put("group.id", "group1");
		kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		Map offsets = new HashMap<>();
		offsets.put(new TopicPartition("streaming", 0), 2L);
		//Consumption data
		JavaInputDStream<ConsumerRecord<Object,Object>> lines = KafkaUtils.createDirectStream(
				ssc,
				LocationStrategies.PreferConsistent(),
				ConsumerStrategies.Subscribe(topicsSet, kafkaParams,offsets));
		
		JavaPairDStream<String, Integer> counts =
				lines.flatMap(x -> Arrays.asList(x.value().toString().split(" ")).iterator())
				.mapToPair(x -> new Tuple2<String, Integer>(x, 1))
				.reduceByKey((x, y) -> x + y);
		counts.print();
		//System.out.println("Lines count is "+lines.count().toString());
		lines.foreachRDD(rdd -> {
			rdd.foreach(x -> {
				System.out.println("RDD values : "+x.value());
				saveTCPNetworkData(x);
			});
		});
		
		ssc.start();
		ssc.awaitTermination();
		ssc.close();
	}
	//Filter and analysis
	private static void saveTCPNetworkData(Object value){

		MongoDatabase database = mongoDbConn.getDatabase("db_schoolweb"); 
		//Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("tcp_data");   //network_security_filter_data
		String[] dataFields = String.valueOf(value).split(",") ;
		try {
			//Insert only tcp data in database
			if(dataFields[12].contentEquals("TCP")){
				//Insert new
				Document document = new Document("No", dataFields[8])
						.append("Time", dataFields[9])
						.append("Source", dataFields[10])
						.append("Destination", dataFields[11])
						.append("Protocol", dataFields[12])
						.append("Length", dataFields[13])
						.append("Info", dataFields[14]);

				//Inserting document into the collection
				collection.insertOne(document);

			}
			mongoDbConn.close();
		}catch (Exception e){
			e.printStackTrace();
		}


	}
}
