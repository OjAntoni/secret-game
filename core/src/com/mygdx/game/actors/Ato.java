package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.objects.CleanCodeBook;
import com.mygdx.game.objects.LinuxPenguin;
import com.mygdx.game.objects.NetworkingBook;
import com.mygdx.game.util.Direction;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Ato extends Actor {
    private static final float RADIUS = Properties.SCREEN_WIDTH * 2;
    private final Texture atoLaserTexture;
    private final Texture atoNormalTexture;
    private boolean isInGame;
    @Getter
    private Direction laserDirection;
    @Getter
    private final Function<Float, Float> lineFunction;
    private float sinAlphaValue;
    private Random r;
    private Timer.Task scheduleForLaser;
    private Timer.Task scheduleForPlacingLinux;
    @Getter
    private boolean isLaserActive;
    @Getter
    private final float x0;
    private final float y0;
    private final ObjectRegistry objectRegistry;
    private int secondsInPeriod;
    private Direction directionForLinux;

    private final List<CleanCodeBook> aimedCleanCodeBooks;
    private Timer.Task scheduleForThrowingBooks;

    public Ato() {
        texture = atoNormalTexture = new Texture(Gdx.files.internal("ato.png"));
        atoLaserTexture = new Texture(Gdx.files.internal("ato_laser.png"));
        rectangle = new Rectangle();
        rectangle.width = texture.getWidth();
        rectangle.height = texture.getHeight();
        rectangle.x = 0;
        rectangle.y = (Properties.SCREEN_HEIGHT - texture.getHeight()) / 2f;

        x0 = rectangle.getWidth() / 2f;
        y0 = rectangle.y + rectangle.height / 2f;

        r = new Random();

        sinAlphaValue = 1f;
        laserDirection = Direction.DOWN;
        lineFunction = (value) -> (float) ((value - x0) * Math.tan(Math.asin(sinAlphaValue)) + y0);

        objectRegistry = ObjectRegistry.getInstance();

        directionForLinux = Direction.UP;

        aimedCleanCodeBooks = new ArrayList<>();


    }

    {

    }

    @Override
    public Coordinates calculateNewCoordinates() {
        if (Math.abs(sinAlphaValue) < 0.01) {
            isLaserActive = false;
            texture = atoNormalTexture;
        }
        if (isLaserActive) {
            if (laserDirection.equals(Direction.DOWN))
                sinAlphaValue -= 0.003;
            else
                sinAlphaValue += 0.003;
        }
        return new Coordinates(rectangle.x, rectangle.y);
    }

    private void aimBooks() {
        aimedCleanCodeBooks.removeIf(b -> !objectRegistry.exists(b));
        List<CleanCodeBook> cleanCodeBooks = objectRegistry.getAll(CleanCodeBook.class);
        for (CleanCodeBook cleanCodeBook : cleanCodeBooks) {
            if(!aimedCleanCodeBooks.contains(cleanCodeBook)){
                aimedCleanCodeBooks.add(cleanCodeBook);
                objectRegistry.add(new NetworkingBook(
                        new Coordinates(x0, y0),
                        new Coordinates(cleanCodeBook.getRectangle().x, cleanCodeBook.getRectangle().y),
                        cleanCodeBook
                ));
            }
        }
    }

    @Override
    public long getTimeLived() {
        return -1;
    }

    @Override
    public void stop(int secondsDelay) {
    }

    @Override
    public boolean canBeMoved() {
        return false;
    }

    @Override
    public void pause() {
        isInGame = false;
        if (scheduleForLaser!=null && scheduleForLaser.isScheduled()) {
            scheduleForLaser.cancel();
        }
        if (scheduleForPlacingLinux != null && scheduleForPlacingLinux.isScheduled()) {
            scheduleForPlacingLinux.cancel();
        }
        if(scheduleForThrowingBooks != null && scheduleForThrowingBooks.isScheduled()){
            scheduleForThrowingBooks.cancel();
        }
    }

    @Override
    public void resume() {
        isInGame = true;
        scheduleForPlacingLinux = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                secondsInPeriod++;
                secondsInPeriod = secondsInPeriod%15;
                if(secondsInPeriod==0){
                    directionForLinux = directionForLinux.equals(Direction.DOWN) ? Direction.UP : Direction.DOWN;
                }
                if(secondsInPeriod >= 10){
                    objectRegistry.add(
                            new LinuxPenguin(
                                    new Coordinates(rectangle.x, rectangle.y+rectangle.height/2f),
                                    directionForLinux)
                    );
                }
            }
        }, 0f, 1f);

        scheduleForThrowingBooks = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                aimBooks();
            }
        }, 0f, 0.5f);

        scheduleForLaser = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (isInGame) {
                    isLaserActive = true;
                    texture = atoLaserTexture;
                    int i = r.nextInt(10);
                    if (i % 2 == 0) {
                        laserDirection = Direction.DOWN;
                        sinAlphaValue = 1;
                    } else {
                        laserDirection = Direction.UP;
                        sinAlphaValue = -1;
                    }
                }
            }
        }, 4f, 15f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (isLaserActive) {
            renderer.setColor(Color.RED);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rectLine(x0, y0,
                    (float) (x0 + RADIUS * Math.cos(Math.asin(sinAlphaValue))),
                    lineFunction.apply((float) (x0 + RADIUS * Math.cos(Math.asin(sinAlphaValue)))),
                    20);
        }
    }
}
