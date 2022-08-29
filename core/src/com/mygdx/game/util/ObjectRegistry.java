package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.objects.GameObject;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectRegistry {
    private static final ObjectRegistry registry = new ObjectRegistry();

    public static ObjectRegistry getInstance(){
        return registry;
    }

    private ObjectRegistry() {
    }

    private final List<GameObject> objects = new LinkedList<>();
    @Setter
    private long time;

    {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                objects.removeIf(o -> o.isOutdated(time));
            }
        }, 0f, 0.5f);
    }

    public void drawAll(SpriteBatch batch){
        for (GameObject object : objects) {
            object.draw(batch);
        }
    }

    public void add(GameObject object){
        objects.add(object);
    }

    public <T> List<T> getAll(Class<T> tClass){
        return objects.stream().filter(o -> o.getClass() == tClass).map(tClass::cast).collect(Collectors.toList());
    }

    public void updatePositions(){
        for (GameObject object : objects) {
            object.setCoordinates(object.calculateNewCoordinates());
        }
    }

    public boolean exists(GameObject gameObject){
        return objects.contains(gameObject);
    }



}
