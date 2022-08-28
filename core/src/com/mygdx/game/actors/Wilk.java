package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;

import java.util.Random;

public class Wilk extends Actor implements ActorAware {
    public static final int TIME_TO_BE_STOPPED = 2;
    private Actor jstar;
    private Random r;
    private ObjectRegistry registry;
    private boolean canBeMoved;
    private boolean isAlive;

    public Wilk() {
        texture = new Texture(Gdx.files.internal("wilk.png"));
        r = new Random();
        rectangle = new Rectangle(r.nextInt(Properties.SCREEN_WIDTH - texture.getWidth()),
                r.nextInt(Properties.SCREEN_HEIGHT - texture.getHeight()),
                texture.getWidth()/3f,
                texture.getHeight()/3f);
        registry = ObjectRegistry.getInstance();
        isAlive = true;
        canBeMoved = true;
        pace = 0.2f;
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        if (canBeMoved()) {
            rectangle.y = rectangle.y + ((jstar.rectangle.y - rectangle.y) / Math.abs(jstar.rectangle.y - rectangle.y)) * pace;
            rectangle.x = rectangle.x + ((jstar.rectangle.x - rectangle.x) / Math.abs(jstar.rectangle.x - rectangle.x)) *
            pace;
        }
        checkForCollisionsWithJstar();
        return new Coordinates(rectangle.x, rectangle.y);
    }

    private void checkForCollisionsWithJstar() {
        if(jstar.rectangle.contains(rectangle)){
            jstar.stop(6);
            jstar.setInitialPace(0.005f);
            isAlive = false;
        }
    }

    @Override
    public long getTimeLived() {
        return -1;
    }

    @Override
    public void stop(int secondsDelay) {
        canBeMoved = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canBeMoved = true;
            }
        }, 0, secondsDelay, 1);
    }


    @Override
    public boolean canBeMoved() {
        return canBeMoved;
    }

    @Override
    public void setActor(Actor actor) {
        jstar = actor;
    }

    @Override
    public boolean shouldBeDeletedFromGame() {
        return !isAlive;
    }

    @Override
    public void deleteFromGame() {
        isAlive = false;
    }

    @Override
    public void resume() {
        isAlive=true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
