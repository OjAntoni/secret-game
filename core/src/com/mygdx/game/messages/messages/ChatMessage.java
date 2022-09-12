package com.mygdx.game.messages.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Data
public class ChatMessage {
    private String from;
    private String payload;
}
