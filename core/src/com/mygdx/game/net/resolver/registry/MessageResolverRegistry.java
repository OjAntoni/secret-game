package com.mygdx.game.net.resolver.registry;

import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.messages.messages.types.MessageType;
import com.mygdx.game.net.resolver.DeletePlayerMessageResolver;
import com.mygdx.game.net.resolver.MessageResolver;
import com.mygdx.game.net.resolver.NewPlayerMessageResolver;
import com.mygdx.game.net.resolver.PlayerCoordMessageResolver;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class MessageResolverRegistry {
    private static final MessageResolverRegistry instance = new MessageResolverRegistry();

    public static MessageResolverRegistry getInstance(){
        return instance;
    }

    private final Map<MessageType, MessageResolver> resolvers = new HashMap<>();

    {
        resolvers.put(MessageType.NEW_PLAYER, new NewPlayerMessageResolver());
        resolvers.put(MessageType.PLAYER_COORD, new PlayerCoordMessageResolver());
        resolvers.put(MessageType.DELETE_PLAYER, new DeletePlayerMessageResolver());
    }

    public void handle(WebSocketSession session, SimpleMessage simpleMessage){
        resolvers.get(simpleMessage.type).resolve(session, simpleMessage.payload);
    }

}
