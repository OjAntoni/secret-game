package com.mygdx.game.net.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.PlayerPositionDto;
import com.mygdx.game.messages.messages.MessageType;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.web.socket.WebSocketSession;

@Log
public class NewPlayerMessageResolver implements MessageResolver {
    private final PlayersRegistry pl= PlayersRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        PlayerPositionDto dto = objectMapper.readValue(payload, PlayerPositionDto.class);
        pl.add(new Player(dto.coordinates, dto.playerId));
        log.info(pl.getAll()+"");
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.NEW_PLAYER;
    }
}
