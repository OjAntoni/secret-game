package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.Direction;
import com.mygdx.game.util.Properties;
import com.mygdx.game.util.TextureRegistry;

public class Niezaliczone extends GameObject {
    private final Direction direction;
    private boolean isAlive = true;

    public Niezaliczone(Coordinates initialCoordinates, Direction direction) {
        texture = TextureRegistry.nzalTexture;
        rectangle = new Rectangle();
        rectangle.x = initialCoordinates.x;
        rectangle.y = initialCoordinates.y;
        rectangle.width = texture.getWidth()/6f;
        rectangle.height = texture.getHeight()/6f;
        this.direction = direction;
        isAlive = true;
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        switch (direction) {
            //todo change x and y carefully
            case UP:
                return new Coordinates(rectangle.x + 3, rectangle.y);
            case DOWN:
                return new Coordinates(rectangle.x - 3, rectangle.y);
            case LEFT:
                return new Coordinates(rectangle.x, rectangle.y - 3);
            case RIGHT:
                return new Coordinates(rectangle.x, rectangle.y + 3);
            default:
                return new Coordinates(rectangle.x, rectangle.y);
        }
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    private boolean isObjectOutOfTheBorders(Coordinates coordinates) {
        return coordinates.x > Properties.SCREEN_WIDTH
                || coordinates.x + rectangle.width < 0
                || coordinates.y + rectangle.height < 0
                || coordinates.y > Properties.SCREEN_HEIGHT;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public boolean isOutdated(long currentTime) {
        return isObjectOutOfTheBorders(new Coordinates(rectangle.x, rectangle.y));
    }
}
