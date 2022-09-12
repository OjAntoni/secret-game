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
public class PlayerCoordMessageResolver implements MessageResolver {
    private final PlayersRegistry playersRegistry =  PlayersRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        PlayerPositionDto dto = objectMapper.readValue(payload, PlayerPositionDto.class);
        if(playersRegistry.get(dto.playerId)==null && playersRegistry.getMe().getId()!=dto.playerId){
            playersRegistry.add(new Player(dto.coordinates, dto.playerId));
        }
        if(dto.playerId != playersRegistry.getMe().getId()){
            playersRegistry.get(dto.playerId).setCoordinates(dto.coordinates);
        }
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.PLAYER_COORD;
    }
}
