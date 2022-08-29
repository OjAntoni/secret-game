package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.Niezaliczone;
import com.mygdx.game.util.Direction;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Zawadski extends Actor {
    enum Axis {
        VERTICAL,
        HORIZONTAL
    }

    private boolean isInGameScreen = false;
    private Axis axis;
    private int movementCoefficient;
    private final Random r = new Random();
    private static final List<Axis> values = Collections.unmodifiableList(Arrays.asList(Axis.values()));
    private ObjectRegistry objectRegistry;
    private Direction directionForObjects;
    private boolean isPaused;


    public Zawadski() {
        texture = new Texture(Gdx.files.internal("zaw.png"));
        rectangle = new Rectangle();
        objectRegistry = ObjectRegistry.getInstance();
        rectangle.setHeight(texture.getHeight() / 6f);
        rectangle.setWidth(texture.getWidth() / 6f);
        isPaused = true;

        Timer.Task scheduleForAppearance = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setInitialPosition();
                isInGameScreen = true;
            }
        }, 0f, 10);

        Timer.Task scheduleForThrowingObjects = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (isInGameScreen && !isPaused)
                    placeObject(new Niezaliczone(new Coordinates(rectangle.x, rectangle.y), directionForObjects));
            }
        }, 0, 1);
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        if (isInGameScreen) {
            switch (axis) {
                case VERTICAL:
                    rectangle.y += movementCoefficient * 2;
                    break;
                case HORIZONTAL:
                    rectangle.x += movementCoefficient * 2;
                    break;
            }
            if (rectangle.x - rectangle.width > Properties.SCREEN_WIDTH || rectangle.x < 0) {
                isInGameScreen = false;
            }
            if (rectangle.y > Properties.SCREEN_HEIGHT || rectangle.y + rectangle.height < 0) {
                isInGameScreen = false;
            }
        }
        return new Coordinates(rectangle.x, rectangle.y);
    }

    private void setInitialPosition() {
        this.axis = values.get(r.nextInt(values.size()));
        rectangle.y = r.nextInt(10) % 2 == 0 ? 0 : Properties.SCREEN_HEIGHT - rectangle.height;
        rectangle.x = r.nextInt(10) % 2 == 0 ? 0 : Properties.SCREEN_WIDTH - rectangle.width;
        if (axis.equals(Axis.VERTICAL)) {
            movementCoefficient = rectangle.y - 1 < 0 ? 1 : -1;
        } else {
            movementCoefficient = rectangle.x - 1 < 0 ? 1 : -1;
        }
        setDirectionForObjects();
    }

    private void setDirectionForObjects() {
        if (axis.equals(Axis.VERTICAL)) {
            if (rectangle.x == 0) {
                directionForObjects = Direction.RIGHT;
            } else {
                directionForObjects = Direction.LEFT;
            }
        } else {
            if (rectangle.y == 0) {
                directionForObjects = Direction.UP;
            } else {
                directionForObjects = Direction.DOWN;
            }
        }
    }

    @Override
    public Coordinates placeObject(GameObject object) {
        objectRegistry.add(object);
        return new Coordinates(rectangle.x, rectangle.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isInGameScreen)
            batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates(rectangle.x, rectangle.y);
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    @Override
    public long getTimeLived() {
        return 0;
    }

    @Override
    public void stop(int secondsDelay) {

    }

    @Override
    public boolean canBeMoved() {
        return true;
    }

    @Override
    public boolean shouldBeDeletedFromGame() {
        return super.shouldBeDeletedFromGame();
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }
}
