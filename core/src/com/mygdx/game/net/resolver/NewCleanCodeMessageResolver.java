package com.mygdx.game.net.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.messages.core.ObjectDto;
import com.mygdx.game.messages.messages.MessageType;
import com.mygdx.game.objects.CleanCode;
import com.mygdx.game.objects.ObjectRegistry;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

public class NewCleanCodeMessageResolver implements MessageResolver {
    private final ObjectRegistry objectRegistry = ObjectRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        ObjectDto objectDto = objectMapper.readValue(payload, ObjectDto.class);
        CleanCode cleanCode = new CleanCode(objectDto.getId(), objectDto.getCoordinates());
        objectRegistry.add(cleanCode);
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.NEW_CLEAN_CODE;
    }
}
