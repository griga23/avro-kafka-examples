# avro-kafka-examples
Jan's example how to work with Kafka and Schema Registry using Avro based events. Also works with Confluent Cloud.

How to run:

## Compile Java App
Compile with all dependencies

_mvn clean compile assembly:single_

## Set App properties
Edit application.properties to point to your Kafka and Schema Registry.

You can set all properties in [/application.properties](/application.properties) or you can overwrite the properties as runtime parameters.

## Run examples
* Send message based on the Java object that was created from Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties
```

* Send message based on the Generic object that was validated with a local Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties

```

* Send message based on the Generic object that was validated with Avro Schema from a remote Schema Registry
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericSchemaDownloadCCloudDemo application.properties
```

* Receive message that was based on Java object
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerCCloudDemo application.properties
```

* Receive message that was based on Generic object
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerGenericCCloudDemo application.properties
```
