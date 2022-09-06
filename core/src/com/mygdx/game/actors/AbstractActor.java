package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.player.Player;

public abstract class AbstractActor {
    protected Rectangle rectangle;
    protected Texture texture;

    public abstract boolean isGameLost(Player player);

    public abstract void handleState(Object state);

    public abstract String getId();

    public void draw(SpriteBatch batch){
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
