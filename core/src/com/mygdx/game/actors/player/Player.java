package com.mygdx.game.actors.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.messages.core.Coordinates;
import lombok.Getter;

import java.util.Objects;
import java.util.Random;

public class Player {
    private boolean isStopped;
    private boolean initialStop;
    @Getter
    private final int id;
    private final Rectangle rectangle;

    public Player() {
        rectangle = new Rectangle();
        rectangle.width = 60f;
        rectangle.height = 60f;
        this.id = new Random().nextInt();
    }

    public Player(Coordinates coordinates, int id) {
        rectangle = new Rectangle();
        rectangle.width = 60f;
        rectangle.height = 60f;
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(rectangle.x, rectangle.y);
    }

    public void setCoordinates(Coordinates coordinates) {
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    public void draw(SpriteBatch batch, Texture texture) {
       batch.draw(texture, rectangle.x-rectangle.width/2f, rectangle.y-rectangle.height/2f, rectangle.width, rectangle.height);
    }

    public Rectangle getRectangleCopy(){
        return new Rectangle(rectangle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id==player.id;
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
