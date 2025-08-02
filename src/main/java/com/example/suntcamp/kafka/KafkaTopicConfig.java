package com.example.suntcamp.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCreateTopic() {
        return TopicBuilder.name("orderCreate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCreateDltTopic() {
        return TopicBuilder.name("orderCreate.DLT")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
