package com.mygdx.game.net.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mygdx.game.actors.ActorsRegistry;
import com.mygdx.game.messages.messages.types.MessageType;
import com.mygdx.game.net.adapter.JstarStateAdapter;
import com.mygdx.game.net.adapter.StateAdapter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Log
public class ActorStateMessageResolver implements MessageResolver {

    private final Map<String, StateAdapter> idsToAdapters = new HashMap<>();

    {
        JstarStateAdapter jstarStateAdapter = new JstarStateAdapter();
        idsToAdapters.put(jstarStateAdapter.getRelevantId(), jstarStateAdapter);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();

    @Override
    @SneakyThrows
    public void resolve(WebSocketSession session, String payload) {
        log.info("received payload="+payload);
        ObjectNode jsonNode = objectMapper.readValue(payload, ObjectNode.class);
        String id = jsonNode.get("id").textValue();

        if (actorsRegistry.getCurrent(id)==null) {
            actorsRegistry.addCurrent(id);
        }
        //log.info("processing actor state with id="+id);
        //log.info("processing actor state with payload="+jsonNode.get("payload").toPrettyString());
        idsToAdapters.get(id).process(id, jsonNode.get("payload").toString());
    }

    @Override
    public MessageType getResolvedType() {
        return MessageType.ACTOR_STATE;
    }
}
