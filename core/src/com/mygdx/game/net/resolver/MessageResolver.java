package com.mygdx.game.net.resolver;

import com.mygdx.game.messages.messages.MessageType;
import org.springframework.web.socket.WebSocketSession;

public interface MessageResolver {
    void resolve(WebSocketSession session, String payload);
    MessageType getResolvedType();
}
