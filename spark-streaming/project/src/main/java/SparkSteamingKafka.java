import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import org.json4s.DateFormat;
import scala.Tuple2;

public class SparkSteamingKafka {

    static int i = 0 ;
    static  Connection conn =  DBConnect.getConn();

    public static void main(String[] args) throws InterruptedException {
        String brokers = "localhost:9092";
        String topics = "topic1";
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
        offsets.put(new TopicPartition("topic1", 0), 2L);
        //Consumption data
        JavaInputDStream<ConsumerRecord<Object,Object>> lines = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams,offsets)
        );
        JavaPairDStream<String, Integer> counts =
                lines.flatMap(x -> Arrays.asList(x.value().toString().split(" ")).iterator())
                        .mapToPair(x -> new Tuple2<String, Integer>(x, 1))
                        .reduceByKey((x, y) -> x + y);
        counts.print();
        lines.foreachRDD(rdd -> {
            rdd.foreach(x -> {
            System.out.println("success:"+x.value());
                saveMessage(x);
             });
         });
        ssc.start();
        ssc.awaitTermination();
        ssc.close();
    }
    //Filter and analysis
    private static void saveMessage(Object value){
        String[] temp = String.valueOf(value).split(",") ;
        Statement st = null ;
        try {
            //Filter abnormal data based on length
            if(temp.length>10&&temp[12].length()>1){
                st = conn.createStatement() ;
                st.executeUpdate("insert  part5(name1,value1) values ( '"+temp[12] +"',"+ temp[13]+")") ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
