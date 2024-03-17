package com.example.chatroomskafkabackendprocessorwordcount.converter;

import com.example.chatroomskafkabackendprocessorwordcount.pojo.Message;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ChatMessageSerDes extends JsonSerde<Message> {
    public ChatMessageSerDes() {
        super();
        ignoreTypeHeaders();
    }
}
