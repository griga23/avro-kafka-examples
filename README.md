# avro-kafka-examples
Jan's example how to work with Confluent Cloud and Schema Registry using Avro based events

How to run:
1) Compile with all dependencies
mvn clean compile assembly:single

2) Run examples 

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericSchemaDownloadCCloudDemo application.properties

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerCCloudDemo application.properties

java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroConsumerGenericCCloudDemo application.properties