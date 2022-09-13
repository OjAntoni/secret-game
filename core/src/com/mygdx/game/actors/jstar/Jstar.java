package com.mygdx.game.actors.jstar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actors.AbstractActor;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.messages.core.Coordinates;
import com.mygdx.game.util.Properties;

public class Jstar extends AbstractActor {
    private final Texture normalJstar;
    private final Texture stoppedJstar;

    public Jstar(){
        texture = normalJstar = new Texture("jstar.png");
        stoppedJstar = new Texture(Gdx.files.internal("jstar_thinking.png"));
        rectangle = new Rectangle();
        //setting initial x and y in order player will not lose the game (my init x = y = 0)
        rectangle.x = Properties.SCREEN_WIDTH + 50;
        rectangle.y = Properties.SCREEN_HEIGHT + 50;
        rectangle.width = texture.getWidth()/9f;
        rectangle.height = texture.getHeight()/9f;
    }

    @Override
    public boolean isGameLost(Player player) {
        Rectangle pr = player.getRectangleCopy();
        return Vector2.dst(pr.x, pr.y, rectangle.x, rectangle.y) < 20;
    }

    @Override
    public void handleState(Object state) {
        JstarState jstarState = (JstarState) state;
        if (jstarState.isStoppedJstar()) {
            texture = stoppedJstar;
        } else {
            texture = normalJstar;
        }
        Coordinates coordinates = jstarState.getCoordinates();
        rectangle.x = coordinates.x;
        rectangle.y = coordinates.y;
    }

    @Override
    public String getId() {
        return "jstar";
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x - rectangle.width/2,
                rectangle.y - rectangle.height/2, rectangle.width, rectangle.height);
    }
}
