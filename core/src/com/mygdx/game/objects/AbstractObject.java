package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.messages.core.Coordinates;
import lombok.Getter;

public abstract class AbstractObject {
    @Getter
    protected int id;
    protected Rectangle rectangle = new Rectangle();
    protected Texture texture;
    public abstract void draw(SpriteBatch batch);
    public void setCoordinates(Coordinates coordinates){
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    public Coordinates getCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    }
}
