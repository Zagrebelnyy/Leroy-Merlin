package com.example.demo.kafka


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback


@Service
class Producer {

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    fun send(topic: String, newGameMessage: Any) {
        val future: ListenableFuture<SendResult<String, Any>> =
            kafkaTemplate.send(topic, newGameMessage.hashCode().toString(), newGameMessage)
        future.addCallback(object : ListenableFutureCallback<SendResult<String, Any>> {
            override fun onFailure(ex: Throwable) {
                println("Message failed to produce")
            }

            override fun onSuccess(result: SendResult<String, Any>?) {
                println("Avro message successfully produced")
            }
        })
    }

}