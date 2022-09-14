package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.messages.core.Coordinates;
import com.mygdx.game.util.TextureRegistry;

import java.util.Random;

public class CleanCode extends AbstractObject {

    public CleanCode(int id, Coordinates coordinates) {
        this.id = id;
        texture = TextureRegistry.getInstance().bookTexture;
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        rectangle.width = texture.getWidth()/8f;
        rectangle.height = texture.getHeight()/8f;
    }

    public CleanCode(Coordinates coordinates) {
        this.id = new Random().nextInt();
        texture = TextureRegistry.getInstance().bookTexture;
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        rectangle.width = texture.getWidth()/8f;
        rectangle.height = texture.getHeight()/8f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x - rectangle.width/2, rectangle.y - rectangle.height/2, rectangle.width, rectangle.height);
    }
}
