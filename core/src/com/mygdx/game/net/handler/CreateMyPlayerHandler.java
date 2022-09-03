package com.mygdx.game.net.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.messages.messages.dto.PlayerPositionDto;
import com.mygdx.game.messages.messages.types.MessageType;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Log
public class CreateMyPlayerHandler implements ConnectionEstablishedHandler {
    PlayersRegistry playersRegistry = PlayersRegistry.getInstance();
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    @SneakyThrows
    public void handle(WebSocketSession session) {
        Player me = new Player();
        log.info("Created me: " + me);
        playersRegistry.setMe(me);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(List.of(new SimpleMessage(MessageType.NEW_PLAYER,
                objectMapper.writeValueAsString(new PlayerPositionDto(me.getCoordinates(), me.getId())))))));
    }
}
