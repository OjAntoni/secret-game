package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.util.Properties;

public class Jstar extends Actor implements ActorAware{
    private Actor studentToSneak;
    private int leftTimeToBeStopped;
    private final double pace = 0.01;
    private final Texture normalJstar;
    private final Texture stoppedJstar;

    public Jstar() {
        texture = normalJstar = new Texture(Gdx.files.internal("jstar.png"));
        stoppedJstar = new Texture(Gdx.files.internal("jstar_thinking.png"));
        rectangle = new Rectangle();
        rectangle.width = texture.getWidth()/3f;
        rectangle.height = texture.getHeight()/3f;
        rectangle.x = MathUtils.random(0, Properties.SCREEN_WIDTH-rectangle.width);
        rectangle.y = 20;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeAlive++;
                if(leftTimeToBeStopped>0){
                    leftTimeToBeStopped--;
                }
            }
        }, 1f, 1f);
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
        return timeAlive;
    }

    @Override
    public void stop(int secondsDelay) {
        leftTimeToBeStopped = secondsDelay;
        texture = stoppedJstar;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                texture=normalJstar;
            }
        }, secondsDelay, 0, 1);
    }

    @Override
    public boolean canBeMoved() {
        return leftTimeToBeStopped==0;
    }

    @Override
    public void setActor(Actor student) {
        this.studentToSneak = student;
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        if(canBeMoved()){
            rectangle.y = (float) (rectangle.y + ((studentToSneak.rectangle.y - rectangle.y)/Math.abs(studentToSneak.rectangle.y - rectangle.y))*10*(pace+timeAlive*0.01));
            rectangle.x = (float) (rectangle.x + ((studentToSneak.rectangle.x - rectangle.x)/Math.abs(studentToSneak.rectangle.x - rectangle.x))*10*(pace+timeAlive*0.01));
        }
        return new Coordinates(rectangle.x, rectangle.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

}
