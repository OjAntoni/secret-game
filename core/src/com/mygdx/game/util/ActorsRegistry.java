package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actors.*;
import com.mygdx.game.game.GameLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ActorsRegistry {
    private final HashMap<String, Actor> allActors = new HashMap<>();
    @Getter
    @Setter
    private HashMap<String, Actor> currentInGameActors = new HashMap<>();

    {
        Student student = new Student();
        Jstar jstar = new Jstar();
        jstar.setActor(student);
        allActors.put("jstar", jstar);
        allActors.put("student", student);
        allActors.put("zawadski", new Zawadski());
    }

    public void updateInGameActors(GameLevel level) {
        for (Map.Entry<String, Actor> e : allActors.entrySet()) {
            if(!e.getKey().equals("student"))
                e.getValue().resetLivedTime();
        }
        currentInGameActors = new HashMap<>();
        currentInGameActors.put("student", allActors.get("student"));
        for (String s : level.getActorNamesInGame()) {
            currentInGameActors.put(s, allActors.get(s));
        }
    }

    public Actor get(String name){
        return currentInGameActors.get(name);
    }

    public List<Actor> getAll(){
        return new ArrayList<>(currentInGameActors.values());
    }

    public void dispose(){
        currentInGameActors.values().forEach(Actor::dispose);
        allActors.values().forEach(Actor::dispose);
    }

    public void drawAll(SpriteBatch batch){
        currentInGameActors.values().forEach(a -> a.draw(batch));
    }

    public void updatePositions(){
        currentInGameActors.values().forEach(a -> {
            Coordinates coordinates = a.calculateNewCoordinates();
            a.setCoordinates(coordinates);
        });
    }
}
