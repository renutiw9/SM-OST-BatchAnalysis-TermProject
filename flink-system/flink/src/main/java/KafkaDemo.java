import java.util.Properties;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

public class KafkaDemo {

    public static void main(String[] args) throws Exception {

        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //By default, checkpoints are disabled. To enable checkpointing, call the enableCheckpointing(n) method on StreamExecutionEnvironment，
        // Where n is the checkpoint interval in milliseconds. Every + 5000 ms to start a checkpoint, the next checkpoint will be started within 5 seconds after the previous checkpoint is completed
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");//The IP or hostName of the kafka node, separated by commas
        properties.setProperty("zookeeper.connect", "localhost:2181");//The IP or hostName of the zookeeper node, multiple separated by commas
        properties.setProperty("group.id", "test-consumer-group");//flink consumer The group.id of the flink consumer
        FlinkKafkaConsumer09<String> myConsumer = new FlinkKafkaConsumer09<String>("topic1", new SimpleStringSchema(),
                properties);//test0 is the topic opened in Kafka
        myConsumer.assignTimestampsAndWatermarks(new CustomWatermarkEmitter());

        DataStream<String> keyedStream = env.addSource(myConsumer);//Process the data sent by the Kafka producer. In this example, I have not processed any
        keyedStream.print();
        // execute program
        env.execute("Flink Streaming Java API Skeleton");

    }

}
