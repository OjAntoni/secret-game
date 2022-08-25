package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.objects.GameObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
public abstract class Actor {
    protected Texture texture;
    protected long timeAlive;
    @Getter
    protected Rectangle rectangle;
    protected Collection<GameObject> gameObjects;
    @Getter
    protected float pace;

    public Coordinates getCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    }
    public void setCoordinates(Coordinates coordinates){
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }
    abstract public long getTimeLived();
    abstract public void stop(int secondsDelay);
    abstract public boolean canBeMoved();
    public Coordinates calculateNewCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    }
    public void setInitialPace(float pace){
        this.pace = pace;
    }
    public void dispose(){}
    public void draw(SpriteBatch batch){
        texture.dispose();
    }

    public Coordinates placeObject(GameObject object) {
        return this.getCoordinates();
    }

    public void setGameObjects(Collection<GameObject> gameObjects){
        this.gameObjects = gameObjects;
    }

    public void resetLivedTime(){timeAlive=0;}
    public boolean shouldBeDeletedFromGame(){
        return false;
    }
    public void deleteFromGame(){}
    public void pause(){}
    public void resume(){}
}
