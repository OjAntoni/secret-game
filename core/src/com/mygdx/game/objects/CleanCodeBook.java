package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.TextureRegistry;

public class CleanCodeBook extends GameObject {
    private static final int TTL = 7;
    private final long createdAt;
    private boolean isOutdated;

    public CleanCodeBook(Coordinates coordinates, long createdAtSeconds) {
        this.texture = TextureRegistry.getInstance().bookTexture;

        this.rectangle = new Rectangle();
        rectangle.setWidth(texture.getWidth() / 10f);
        rectangle.setHeight(texture.getHeight() / 10f);
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y + rectangle.height/2;

        createdAt = createdAtSeconds;
    }

    @Override
    public boolean isOutdated(long currentGameTime) {
        return currentGameTime - createdAt > TTL || isOutdated;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, texture.getWidth() / 6f, texture.getHeight() / 6f);
    }

    @Override
    public void setAsDeleted() {
        isOutdated = true;
    }
}
