package com.example.chatroomskafkabackendprocessorwordcount.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String username;
    private String message;
    private String timestamp;
    private String chatRoomName;
}
