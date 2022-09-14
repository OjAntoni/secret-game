package com.mygdx.game.objects;

import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class ObjectRegistry {
    @Getter
    private final List<AbstractObject> objects = new ArrayList<>();
    private static final ObjectRegistry instance = new ObjectRegistry();

    private ObjectRegistry() {

    }

    public static ObjectRegistry getInstance() {
        return instance;
    }

    public void add(AbstractObject object) {
        objects.add(object);
    }

    public void remove(int id) {
        objects.removeIf(o -> o.id == id);
    }

    public AbstractObject get(int id) {
        for (AbstractObject o : objects) {
            if (o.id == id) {
                return o;
            }
        }
        log.warning("Can't find object with id=" + id);
        return null;
    }
}
