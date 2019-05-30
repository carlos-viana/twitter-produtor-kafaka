import org.apache.kafka.clients.producer.*;

import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    private static KafkaProducer<String, Tweet> producer;

    public Producer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TweetSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    public void SendMessage(String topic, Tweet tweet) {
        ProducerRecord<String, Tweet> record = new ProducerRecord<>(topic, tweet);
        producer.send(record);
    }

    public void Close() {
        producer.close();
    }
}
