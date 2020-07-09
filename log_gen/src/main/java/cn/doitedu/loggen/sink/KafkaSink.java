package cn.doitedu.loggen.sink;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class KafkaSink {
    static KafkaProducer<String, String> producer = null;
    static Properties props = null;
    static {
        try {
            props = new Properties();
            props.load(KafkaSink.class.getClassLoader().getResourceAsStream("kafka.properties"));
            producer = new KafkaProducer<>(props);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sink(String content) throws IOException {
        ProducerRecord<String, String> rec = new ProducerRecord<>(props.getProperty("dest.topic"), content);
        producer.send(rec);
        //producer.close();
    }
}
