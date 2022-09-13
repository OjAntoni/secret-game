package com.mygdx.game.net.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.ActorsRegistry;
import com.mygdx.game.actors.wilk.WilkState;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class WilkStateAdapter implements StateAdapter {
    private final ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    @SneakyThrows
    public void process(String actorId, String stateAsJson) {
        WilkState wilkStateState = objectMapper.readValue(stateAsJson, WilkState.class);
        actorsRegistry.getCurrent(actorId).handleState(wilkStateState);
    }

    @Override
    public String getRelevantId() {
        return "wilk";
    }
}
