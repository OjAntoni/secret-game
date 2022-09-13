package com.mygdx.game.actors.wilk;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.AbstractActor;
import com.mygdx.game.actors.ActorsRegistry;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.messages.core.Coordinates;

public class Wilk extends AbstractActor {
    private boolean isAlive;
    private ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();

    public Wilk() {
        rectangle = new Rectangle();
        texture = new Texture("wilk.png");
        rectangle.height = texture.getHeight()/4f;
        rectangle.width = texture.getWidth()/4f;
    }

    @Override
    public boolean isGameLost(Player player) {
        return false;
    }

    @Override
    public void handleState(Object state) {
        WilkState wilkState = (WilkState) state;
        Coordinates c = wilkState.getCoordinates();
        rectangle.x = c.x;
        rectangle.y = c.y;
        if(!wilkState.isAlive()){
            actorsRegistry.getCurrentActors().remove(getId());
        }
    }

    @Override
    public String getId() {
        return "wilk";
    }

    @Override
    public void draw(SpriteBatch batch) {
            batch.draw(texture, rectangle.x - rectangle.width/2,
                    rectangle.y - rectangle.height/2, rectangle.width, rectangle.height);
    }
}
