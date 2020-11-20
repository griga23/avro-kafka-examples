# avro-kafka-examples
Jan's example how to work with Confluent Cloud and Schema Registry using Avro based events

How to run:
1) Compile with all dependencies
mvn clean compile assembly:single

2) Run examples 

* Send message based on the Java object that was created from Avro Schema

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties

* Send message based on the Generic object that was validated with a local Avro Schema

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties

* Send message based on the Generic object that was validated with Avro Schema from a remote Schema Registry

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericSchemaDownloadCCloudDemo application.properties

* Receive message that was based on Java object

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerCCloudDemo application.properties

* Receive message that was based on Generic object

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerGenericCCloudDemo application.properties
