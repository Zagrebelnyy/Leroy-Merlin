package com.example.demo.kafka


import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener


@EnableKafka
@Configuration
class Consumer {

    @KafkaListener(
        topics = ["billing-trainee-new-game-input-v1",
            "billing-trainee-game-result-v1", "billing-trainee-new-game-input-v1-dlt"]
    )
    public fun listen(record: ConsumerRecord<String, Any>) {
        val key = record.key()
        val value: Any = record.value()
        println("Avro message received for key : $key value : $value")

    }
}