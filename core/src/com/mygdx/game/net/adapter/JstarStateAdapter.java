package com.mygdx.game.net.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.ActorsRegistry;
import com.mygdx.game.actors.jstar.JstarState;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class JstarStateAdapter implements StateAdapter {
    private final ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void process(String id, String jsonState) {
        System.out.println(jsonState);
        JstarState jstarState = objectMapper.readValue(jsonState, JstarState.class);
        log.info("converted jstar state: "+jsonState);
        actorsRegistry.getCurrent(id).handleState(jstarState);
    }

    @Override
    public String getRelevantId() {
        return "jstar";
    }
}
