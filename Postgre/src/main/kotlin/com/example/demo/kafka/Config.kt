package com.example.demo.kafka

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import ru.leroymerlin.billing.NewGameMessage


@Configuration
private class Config {
    @Bean
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, NewGameMessage> {
        return DefaultKafkaConsumerFactory<String, NewGameMessage>(kafkaProperties.buildConsumerProperties())
    }

    @Bean
    fun kafkaListenerContainerFactory(kafkaProperties: KafkaProperties):
            KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewGameMessage>> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, NewGameMessage> =
            ConcurrentKafkaListenerContainerFactory<String, NewGameMessage>()
        factory.setConsumerFactory(consumerFactory(kafkaProperties))
        return factory
    }
}