package com.mygdx.game.net.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.messages.messages.PlayerPositionDto;
import com.mygdx.game.messages.messages.MessageType;
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
    public void handle(WebSocketSession session) {

    }
}
