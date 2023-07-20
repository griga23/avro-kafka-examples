# avro-kafka-examples
Jan's example how to work with Kafka and Schema Registry using Avro based events. Also works with Confluent Cloud.
* Example schema is defined at: [/src/main/resources/avro/customer-v1.avsc](/src/main/resources/avro/customer-v1.avsc)
* Example schema with data validation roles is  at:  [/conditionRulesExample.json](conditionRulesExample.json)
* Java Class Customer will be automatically generated target/generated-sources/avro/com/github/griga23


How to run:

## Compile Java App
Compile with all dependencies

_mvn clean compile assembly:single_

## Set App properties
Edit application.properties to point to your Kafka and Schema Registry.

You can set all properties in [/application.example.properties](/application.example.properties). Don't forget to set Kafka Topics. Then rename the file to application.properties

## Run examples
* Send message based on the Java object Customer that was created from Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties
```

* Send message based on the Generic object that was validated with a local Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties

```

* Send message based on the JSON message that was converted to Avro message and validated with a local Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericJsonToAvroDemo application.properties

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

## Run examples with Data Validation Rules
* Post new Schema with Data Validation to Confluent Cloud Schema Registry
```
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data ''\
https://xxx.confluent.cloud/subjects/customer-avro-value/versions
```
Inside of the data '' must be the escaped schema and the data validation rules. Example is in [/conditionRulesExample.json](conditionRulesExample.json).

* Send message based on the Java object Customer that was created from Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerCCloudDemo application.properties
```

* Send message based on the Generic object that was validated with a local Avro Schema
```
java -cp target/avro-kafka-examples-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.griga23.KafkaAvroProducerGenericCCloudDemo application.properties

```
Change age in the producer to save message in the DLQ topic
