package com.mygdx.game.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.messages.messages.SimpleMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.List;

@Log
public class WebSocketClient {
    private String uri = "ws://localhost:8080/game";
    @Getter
    private final WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketClient() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            WebSocketHandler socket = new WebSocketEventHandler();
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            session = fut.get();
            log.info("Session obtained by WebSocketClient.class: " + session);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t.getMessage());
        }
    }

    @SneakyThrows
    public void close() {
        log.info("Closed session: " + session);
        session.close();
    }

    @SneakyThrows
    public void send(SimpleMessage simpleMessage) {
        synchronized (session) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(List.of(simpleMessage))));
        }
    }
}
