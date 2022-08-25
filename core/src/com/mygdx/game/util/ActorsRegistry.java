package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
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
    private int level;

    {
        Student student = new Student();
        Jstar jstar = new Jstar();
        jstar.setActor(student);
        allActors.put("jstar", jstar);
        allActors.put("student", student);
        allActors.put("zawadski", new Zawadski());
        Wilk wilk = new Wilk();
        wilk.setActor(jstar);
        allActors.put("wilk", wilk);

        allActors.values().forEach(Actor::pause);
    }

    {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                level++;
                updateInGameActors(GameLevel.getLevel(level));
            }
        }, 0f, 60f);
    }

    {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                deleteDeadActors();
            }
        }, 0f, 0.5f);
    }

    private void deleteDeadActors() {
        List<String> actorsToDelete = new ArrayList<>();
        currentInGameActors.forEach((name, a) -> {
            if(a.shouldBeDeletedFromGame()){
                actorsToDelete.add(name);
            }
        });
        actorsToDelete.forEach(name -> currentInGameActors.remove(name));
    }

    private void updateInGameActors(GameLevel level) {
        currentInGameActors.values().forEach(Actor::pause);
        currentInGameActors = new HashMap<>();
        currentInGameActors.put("student", allActors.get("student"));
        for (String s : level.getActorNamesInGame()) {
            currentInGameActors.put(s, allActors.get(s));
            allActors.get(s).resume();
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
