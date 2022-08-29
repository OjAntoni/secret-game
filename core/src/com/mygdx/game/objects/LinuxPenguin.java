package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.Direction;
import com.mygdx.game.util.Properties;
import com.mygdx.game.util.TextureRegistry;

import java.util.function.Function;

public class LinuxPenguin extends GameObject {
    private final float xDelta = 0.6f;
    private Function<Float, Float> movingFunction;
    int coef = 1;
    private boolean isOutdated;

    public LinuxPenguin(Coordinates coordinates, Direction direction) {
        texture = TextureRegistry.linuxTexture;
        rectangle = new Rectangle(coordinates.x, coordinates.y, texture.getWidth() / 24f, texture.getHeight() / 24f);
        if (direction == Direction.DOWN) {
            coef = -1;
        }
        createTrajectoryFunction();
    }

    private void createTrajectoryFunction() {
        movingFunction = (x) -> (float) ((Properties.SCREEN_HEIGHT / 2f * coef) * 0.9 * Math.sin(x / 40)) + Properties.SCREEN_HEIGHT / 2f - rectangle.height / 2f;
    }

    @Override
    public boolean isOutdated(long currentTime) {
        return isOutOfTheBorders() || isOutdated;
    }

    private boolean isOutOfTheBorders() {
        return rectangle.x > Properties.SCREEN_WIDTH ||
                rectangle.x + rectangle.width < 0 ||
                rectangle.y > Properties.SCREEN_HEIGHT ||
                rectangle.y + rectangle.height < 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        rectangle.x += xDelta;
        return new Coordinates(rectangle.x, movingFunction.apply(rectangle.x));
    }

    @Override
    public void setAsDeleted() {
        isOutdated = true;
    }
}
