package com.mygdx.game.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.net.handler.registry.ConnectionEstablishedHandlerRegistry;
import com.mygdx.game.net.resolver.registry.MessageResolverRegistry;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Log
public class WebSocketEventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConnectionEstablishedHandlerRegistry connectionEstablishedHandlerRegistry = new ConnectionEstablishedHandlerRegistry();
    private final MessageResolverRegistry messageResolverRegistry = MessageResolverRegistry.getInstance();

    @Override
    @SneakyThrows
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established with session " + session);
        connectionEstablishedHandlerRegistry.process(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Got message" + message.getPayload());
        List<SimpleMessage> messages = objectMapper.readValue(message.getPayload(), new TypeReference<>() {});
        if(messages!=null){
            messages.forEach(m -> {
                log.info("resolving "+m);
                if(messageResolverRegistry!=null)
                    messageResolverRegistry.handle(session, m);
            });
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed with session " + session);
    }
}
