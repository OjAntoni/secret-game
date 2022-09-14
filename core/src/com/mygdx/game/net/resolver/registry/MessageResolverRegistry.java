package com.mygdx.game.net.resolver.registry;

import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.messages.messages.MessageType;
import com.mygdx.game.net.resolver.*;
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
        resolvers.put(MessageType.ACTOR_STATE, new ActorStateMessageResolver());
        resolvers.put(MessageType.CHAT_MESSAGE, new ChatMessageResolver());
        resolvers.put(MessageType.COORD_CLEAN_CODE, new CoordCleanCodeMessageResolver());
        resolvers.put(MessageType.NEW_CLEAN_CODE, new NewCleanCodeMessageResolver());
        resolvers.put(MessageType.DELETE_CLEAN_CODE, new DeleteCleanCodeMessageResolver());
    }

    public void handle(WebSocketSession session, SimpleMessage simpleMessage){
        resolvers.get(simpleMessage.type).resolve(session, simpleMessage.payload);
    }

}
