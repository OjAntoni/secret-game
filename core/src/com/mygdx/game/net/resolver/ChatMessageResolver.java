package com.mygdx.game.net.resolver;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.game.ChatService;
import com.mygdx.game.messages.messages.ChatMessage;
import com.mygdx.game.messages.messages.MessageType;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

public class ChatMessageResolver implements MessageResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatService chatService = ChatService.getInstance();

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        chatService.add(chatMessage);
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.CHAT_MESSAGE;
    }
}
