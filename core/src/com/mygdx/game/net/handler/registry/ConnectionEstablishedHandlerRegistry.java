package com.mygdx.game.net.handler.registry;

import com.mygdx.game.net.handler.ConnectionEstablishedHandler;
import com.mygdx.game.net.handler.CreateMyPlayerHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class ConnectionEstablishedHandlerRegistry {
    private final List<ConnectionEstablishedHandler> handlers = new ArrayList<>();

    {
        handlers.add(new CreateMyPlayerHandler());
    }

    public void process(WebSocketSession session){
        handlers.forEach(h -> h.handle(session));
    }
}
