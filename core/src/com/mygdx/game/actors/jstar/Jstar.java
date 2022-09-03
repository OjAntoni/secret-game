package com.mygdx.game.actors.jstar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.AbstractActor;
import com.mygdx.game.messages.core.Coordinates;

public class Jstar extends AbstractActor {
    private final Texture normalJstar;
    private final Texture stoppedJstar;

    public Jstar(){
        texture = normalJstar = new Texture("jstar.png");
        stoppedJstar = new Texture(Gdx.files.internal("jstar_thinking.png"));
        rectangle = new Rectangle();
        rectangle.width = texture.getWidth()/9f;
        rectangle.height = texture.getHeight()/9f;
    }

    @Override
    public boolean isGameLost() {
        return false;
    }

    @Override
    public void handleState(Object state) {
        JstarState jstarState = (JstarState) state;
        Coordinates coordinates = jstarState.getCoordinates();
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
        if (jstarState.isStoppedJstar()) {
            texture = stoppedJstar;
        }
    }

    @Override
    public String getId() {
        return "jstar";
    }
}
