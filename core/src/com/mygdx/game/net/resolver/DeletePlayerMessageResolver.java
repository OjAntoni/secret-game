package com.mygdx.game.net.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.PlayerPositionDto;
import com.mygdx.game.messages.messages.MessageType;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

public class DeletePlayerMessageResolver implements MessageResolver {
    private final PlayersRegistry playersRegistry = PlayersRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public void resolve(WebSocketSession session, String payload) {
        PlayerPositionDto dto = objectMapper.readValue(payload, PlayerPositionDto.class);
        playersRegistry.remove(dto.playerId);
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.DELETE_PLAYER;
    }
}
