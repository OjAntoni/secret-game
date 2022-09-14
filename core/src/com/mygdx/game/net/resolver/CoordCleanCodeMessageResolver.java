package com.mygdx.game.net.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.messages.core.ObjectDto;
import com.mygdx.game.messages.messages.MessageType;
import com.mygdx.game.objects.AbstractObject;
import com.mygdx.game.objects.CleanCode;
import com.mygdx.game.objects.ObjectRegistry;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

public class CoordCleanCodeMessageResolver implements MessageResolver {
    private final ObjectRegistry objectRegistry = ObjectRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        ObjectDto objectDto = objectMapper.readValue(payload, ObjectDto.class);
        AbstractObject o = objectRegistry.get(objectDto.getId());
        if(o!=null){
            o.setCoordinates(objectDto.getCoordinates());
        } else {
            objectRegistry.add(new CleanCode(objectDto.getId(), objectDto.getCoordinates()));
        }
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.COORD_CLEAN_CODE;
    }
}
