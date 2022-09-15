package com.mygdx.game.actors;

import com.mygdx.game.actors.jstar.Jstar;
import com.mygdx.game.actors.wilk.Wilk;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActorsRegistry {
    private static final ActorsRegistry instance = new ActorsRegistry();

    public static ActorsRegistry getInstance(){
        return instance;
    }

    @Getter
    private static final Map<String, AbstractActor> allActors = new HashMap<>();
    @Getter
    @Setter
    private Map<String, AbstractActor> currentActors = new ConcurrentHashMap<>();

    static {
        Jstar jstar = new Jstar();
        allActors.put(jstar.getId(), jstar);
        Wilk wilk = new Wilk();
        allActors.put(wilk.getId(), wilk);
    }

    public AbstractActor getCurrent(String id){
        return currentActors.get(id);
    }

    public AbstractActor get(String id){
        return allActors.get(id);
    }

    public void addCurrent(String id){
        currentActors.put(id, allActors.get(id));
    }

}
