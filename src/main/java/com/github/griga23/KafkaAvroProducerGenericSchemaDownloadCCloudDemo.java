package com.github.griga23;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaAvroProducerGenericSchemaDownloadCCloudDemo {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IllegalArgumentException(
                    "Please provide: the path to an environment configuration file");
        }

        // initialize Kafka producer
        final Properties props = loadProperties(args[0]);
        Producer<String, GenericData.Record> producer = new KafkaProducer<String, GenericData.Record>(props);
        final String topic = props.getProperty("generic.topic.name");

        // connect client to Schema Registry in Confluent Cloud
        Map<String, String> map = getSchemaRegistryConfigMap(props);
        SchemaRegistryClient client = new CachedSchemaRegistryClient(
                props.getProperty("schema.registry.url"),
                1000,
                map);

        // download some specific Avro schema from CCloud
        Schema schema = null;
        try {
            //parsedSchema = client.getSchemaById(100036);
            SchemaMetadata sm = client.getLatestSchemaMetadata(topic+"-value");
            System.out.println("Schema: " + sm.getSchema());
            // parse it
            Schema.Parser parser = new Schema.Parser();
            schema = parser.parse(sm.getSchema());
        } catch (IOException | RestClientException e) {
            e.printStackTrace();
        }

        //  create a generic customer record based on the Avro schema
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "Jan");
        customerBuilder.set("last_name", "Svoboda");
        customerBuilder.set("age", 40);
        customerBuilder.set("height", 195f);
        customerBuilder.set("weight", 91f);
        customerBuilder.set("automated_email", false);
        GenericData.Record myCustomer = customerBuilder.build();
        System.out.println("Message: " + myCustomer);

        // serialize single Kafka ProducerRecord based on the GenericData.Record
        ProducerRecord<String, GenericData.Record> producerRecord =
                new ProducerRecord<String, GenericData.Record>(topic, myCustomer.get("last_name").toString(), myCustomer);

        // send ProducerRecord to Kafka Broker in CCloud
        producer.send(producerRecord);

        // close Producer to CCloud
        producer.flush();
        producer.close();

        System.out.println("Generic ProducerRecord sent to Kafka!");
    }

    // create config map needed for Schema Registry client
    private static Map<String, String> getSchemaRegistryConfigMap(Properties props) {
        Map<String, String> map = new HashMap<>();
        map.put(AbstractKafkaSchemaSerDeConfig.BASIC_AUTH_CREDENTIALS_SOURCE, props.getProperty("basic.auth.credentials.source"));
        map.put(AbstractKafkaSchemaSerDeConfig.USER_INFO_CONFIG,props.getProperty("basic.auth.user.info"));
        map.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,  props.getProperty("schema.registry.url"));
        return map;
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
