package com.example.chatroomskafkabackendprocessorwordcount.config;

import com.example.chatroomskafkabackendprocessorwordcount.pojo.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ChatKafkaProcessor {
    @Bean
    public Function<KStream<String, Message>, KStream<String, Long>> aggregateWordsAndCount(){
        return kStream -> kStream
                .flatMapValues(value -> Arrays.asList(value.getMessage().toLowerCase().split("\\W+")))
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
                .aggregate(() -> 0L,
                        (key, value, aggregate) -> aggregate+1,
                        Materialized.with(Serdes.String(), Serdes.Long()))
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((key, value) -> new KeyValue<>(key.key(), value))
                .peek((key, value) -> log.info("Aggregated Words and Count: Key: {}   -   Value: {}", key, value));

    }
}
