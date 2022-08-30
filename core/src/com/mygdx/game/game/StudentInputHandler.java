package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.actors.Actor;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.objects.CleanCodeBook;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;

public class StudentInputHandler implements PositionHandler {
    private final Actor student;
    private final ObjectRegistry objectRegistry;
    private final Camera camera;
    private final InGameTimer timer;
    private float timeLastCleanCodeBookPlaced;

    public StudentInputHandler(Actor student, Camera camera) {
        this.student = student;
        this.objectRegistry = ObjectRegistry.getInstance();
        this.camera = camera;
        this.timer = InGameTimer.getInstance();
    }

    @Override
    public void handleInput() {
        Coordinates c = student.getCoordinates();
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.y+=2;
            c.x+=2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            c.y -= 2;
            c.x += 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.y -= 2;
            c.x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.y += 2;
            c.x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            c.y+=3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.x += 3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            c.y-=3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.x -= 3;
        }
        student.setCoordinates(c);

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if(timer.getTimeMillis() - timeLastCleanCodeBookPlaced > Properties.PLACING_CLEAN_CODE_INTERVAL_MS){
                CleanCodeBook cleanCodeBook = new CleanCodeBook(student.getCoordinates(), timer.getTime());
                objectRegistry.add(cleanCodeBook);
                timeLastCleanCodeBookPlaced = timer.getTimeMillis();
            }
        }

    }
}
