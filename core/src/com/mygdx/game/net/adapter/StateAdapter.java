package com.mygdx.game.net.adapter;

public interface StateAdapter {
    void process(String actorId, String stateAsJson);
    String getRelevantId();
}
