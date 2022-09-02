package com.mygdx.game.net.handler;

import com.mygdx.game.net.WebSocketClient;
import org.springframework.web.socket.WebSocketSession;

public interface ConnectionEstablishedHandler {
    void handle(WebSocketSession session);
}
