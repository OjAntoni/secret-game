package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.messages.core.Coordinates;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.Properties;
import com.mygdx.game.util.TextureRegistry;

public class PlayerInputHandler implements InputHandler {
    private final Player me;
    private final InGameTimer timer;
    private final TextureRegistry textureRegistry;
    private final GameMenuProperties gameMenuProperties;

    private boolean chatReload = true;
    private boolean timeReload = true;

    public PlayerInputHandler(Player me) {
        this.me= me;
        this.timer = InGameTimer.getInstance();
        this.textureRegistry = TextureRegistry.getInstance();
        this.gameMenuProperties = GameMenuProperties.getInstance();
    }

    @Override
    public void handleInput() {
        Coordinates c = me.getCoordinates();
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.y+=2;
            c.x+=2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            c.y -= 2;
            c.x += 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.y -= 2;
            c.x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.y += 2;
            c.x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            c.y+=3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            c.x += 3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            c.y-=3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            c.x -= 3;
        }

        c.x = c.x < 0 ? 0: c.x;
        c.x = c.x + 30f > Properties.SCREEN_WIDTH ? Properties.SCREEN_WIDTH - 30f : c.x;
        c.y = c.y < 0 ? 0 : c.y;
        c.y = c.y + 30f > Properties.SCREEN_HEIGHT ? Properties.SCREEN_HEIGHT - 30f : c.y;

        me.setCoordinates(c);

        if(Gdx.input.isKeyPressed(Input.Keys.F1) && chatReload){
            gameMenuProperties.isChatShown = !gameMenuProperties.isChatShown;
            chatReload = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    chatReload = true;
                }
            }, 0.5f, 0, 1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.F2) && timeReload){
            gameMenuProperties.isTimeShown = !gameMenuProperties.isTimeShown;
            timeReload = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    timeReload = true;
                }
            }, 0.5f, 0, 1);
        }
    }
}
