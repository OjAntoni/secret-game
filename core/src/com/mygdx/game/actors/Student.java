package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.util.Properties;

public class Student extends Actor{
    private boolean isStopped;
    private boolean initialStop;

    public Student() {
        texture = new Texture(Gdx.files.internal("student.png"));
        rectangle = new Rectangle();
        rectangle.width = texture.getWidth()/13f;
        rectangle.height = texture.getHeight()/13f;
        rectangle.x = Properties.SCREEN_WIDTH / 2f - rectangle.width / 2;
        rectangle.y = Properties.SCREEN_HEIGHT / 2f - rectangle.height / 2;
    }

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates(rectangle.x, rectangle.y);
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        if(isStopped){
            if(initialStop){
                rectangle.x = coordinates.x - rectangle.width / 2;
                rectangle.y = coordinates.y - rectangle.height / 2;
                initialStop = false;
            }
            return;
        }
        rectangle.x = coordinates.x - rectangle.width / 2;
        rectangle.y = coordinates.y - rectangle.height / 2;
        if (rectangle.x < 0)
            rectangle.x = 0;
        if (rectangle.x > Properties.SCREEN_WIDTH - rectangle.width)
            rectangle.x = Properties.SCREEN_WIDTH - rectangle.width;
        if(rectangle.y < 0)
            rectangle.y = 0;
        if(rectangle.y > Properties.SCREEN_HEIGHT - rectangle.height)
            rectangle.y = Properties.SCREEN_HEIGHT - rectangle.height;
    }

    @Override
    public long getTimeLived() {
        return 0;
    }

    @Override
    public void stop(int secondsDelay) {
        isStopped = true;
        initialStop = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isStopped = false;
            }
        }, secondsDelay, 0, 1);
    }

    @Override
    public boolean canBeMoved() {
        return !isStopped;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width*2, rectangle.height*2);
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        return new Coordinates(rectangle.x, rectangle.y);
    }
}
