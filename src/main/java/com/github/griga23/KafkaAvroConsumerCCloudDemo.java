package com.github.griga23;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaAvroConsumerCCloudDemo {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "Please provide: the path to an environment configuration file");
        }

        // initialize Kafka consumer
        final Properties props = loadProperties(args[0]);
        final String topic = props.getProperty("customer.topic.name");
        KafkaConsumer<String, Customer> kafkaConsumer = new KafkaConsumer<String, Customer>(props);

        // subscribe to single topic
        kafkaConsumer.subscribe(Collections.singleton(topic));

        System.out.println("Waiting for data from Kafka...");

        // polling for new data every 1000 ms
        while (true){
            ConsumerRecords<String, Customer> records = kafkaConsumer.poll(Duration.ofMillis(1000));

            // print all Customer objects returned
            for (ConsumerRecord<String, Customer> record : records){
                Customer customer = record.value();
                System.out.println(customer);
            }
            kafkaConsumer.commitSync();
        }

    }

    // load properties from some file
    public static Properties loadProperties(String fileName) throws IOException {
        final Properties envProps = new Properties();
        final FileInputStream input = new FileInputStream(fileName);
        envProps.load(input);
        input.close();

        return envProps;
    }

}
