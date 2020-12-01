package com.github.griga23;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

public class KafkaAvroProducerGenericJsonToAvroDemo {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "Please provide: the path to an environment configuration file");
        }

        // initialize Kafka producer
        final Properties props = loadProperties(args[0]);
        Producer<String, GenericData.Record> producer = new KafkaProducer<String, GenericData.Record>(props);
        final String topic = props.getProperty("generic.topic.name");

        // parse Consumer Avro schema from local file
        Schema.Parser parser = new Schema.Parser();
        Schema schema = null;
        try {
            schema = parser.parse(new File("src/main/resources/avro/customer-v1.avsc"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create example Json message
        String json = "{\n" +
                "  \"first_name\": \"Jan\",\n" +
                "  \"last_name\": \"Svoboda\",\n" +
                "  \"age\": 40,\n" +
                "  \"height\": 195.0,\n" +
                "  \"weight\": 91.0,\n" +
                "  \"automated_email\": false\n" +
                "}";

        // initialize the Json to Avro converter
        JsonAvroConverter converter = new JsonAvroConverter();

        // create Avro generic record from JSON message
        GenericData.Record myCustomer = converter.
                convertToGenericDataRecord(json.getBytes(), schema);
        System.out.println(myCustomer);

        // create a single Kafka ProducerRecord<K,V> based on the GenericData.Record
        ProducerRecord<String, GenericData.Record> producerRecord =
                new ProducerRecord<String, GenericData.Record>(topic, myCustomer.get("last_name").toString(), myCustomer);

        // send ProducerRecord to Kafka Broker
        producer.send(producerRecord);

        // close Producer to CCloud
        producer.flush();
        producer.close();

        System.out.println("Generic ProducerRecord sent to Kafka!");

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
