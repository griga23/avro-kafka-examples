# avro-kafka-examples
Jan's example how to work with Confluent Cloud and Schema Registry using Avro based events

How to run:
1) Compile with all dependencies
mvn clean compile assembly:single

2) Run examples 

* Send message based on the Java object that was created from Avro Schema

_java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties_

* Send message based on the Generic object that was validated with a local Avro Schema

_java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties_

* Send message based on the Generic object that was validated with Avro Schema from a remote Schema Registry

_java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericSchemaDownloadCCloudDemo application.properties_

* Receive message that was based on Java object

_java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerCCloudDemo application.properties_

* Receive message that was based on Generic object

_java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerGenericCCloudDemo application.properties_
