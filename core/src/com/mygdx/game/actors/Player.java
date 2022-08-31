package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.util.TextureRegistry;
import lombok.Getter;

import java.util.Objects;

public class Player {
    private boolean isStopped;
    private boolean initialStop;
    @Getter
    private final String id;
    private final Rectangle rectangle;

    public Player(String id) {
        rectangle = new Rectangle();
        rectangle.width = 30f;
        rectangle.height = 30f;
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(rectangle.x, rectangle.y, id);
    }

    public void setCoordinates(Coordinates coordinates) {
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    public void draw(SpriteBatch batch, Texture texture) {
       batch.draw(texture, rectangle.x, rectangle.y, rectangle.width*2, rectangle.height*2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                '}';
    }
}
