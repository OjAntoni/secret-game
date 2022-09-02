package com.mygdx.game.messages.messages;

import com.mygdx.game.messages.messages.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleMessage {
    public MessageType type;
    public String payload;
}
