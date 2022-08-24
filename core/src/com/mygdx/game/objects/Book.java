package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.TextureRegistry;

public class Book extends GameObject{
    private static final int TTL = 5;
    private final long createdAt;

    public Book(Coordinates coordinates, long createdAtSeconds) {
        this.texture = TextureRegistry.bookTexture;

        this.rectangle = new Rectangle(coordinates.x-texture.getWidth()/20f,
                coordinates.y-texture.getHeight()/20f,
                texture.getWidth()/10f,
                texture.getHeight()/10f);

        createdAt = createdAtSeconds;

    }

    @Override
    public boolean isOutdated(long currentGameTime){
        return currentGameTime-createdAt>TTL;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, texture.getWidth()/6f, texture.getHeight()/6f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
