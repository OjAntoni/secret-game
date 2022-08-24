package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.TextureRegistry;
import lombok.Getter;

@Getter
public abstract class GameObject {
    protected TextureRegistry textureRegistry;
    protected Rectangle rectangle;
    protected Texture texture;

    public boolean isOutdated(long currentTime){
        return false;
    }

    public void draw(SpriteBatch batch){}

    public void dispose(){
    }

    public void setCoordinates(Coordinates coordinates){
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    public Coordinates calculateNewCoordinates(){
        return new Coordinates(rectangle.x, rectangle.y);
    };

}
