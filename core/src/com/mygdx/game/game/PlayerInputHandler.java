package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.messages.core.Coordinates;
import com.mygdx.game.messages.core.ObjectDto;
import com.mygdx.game.messages.messages.MessageType;
import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.net.WebSocketClient;
import com.mygdx.game.objects.CleanCode;
import com.mygdx.game.objects.ObjectRegistry;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.Properties;
import com.mygdx.game.util.TextureRegistry;
import lombok.SneakyThrows;

public class PlayerInputHandler implements InputHandler {
    private final Player me;
    private final InGameTimer timer;
    private final TextureRegistry textureRegistry;
    private final GameMenuProperties gameMenuProperties;
    private final ObjectRegistry objectRegistry;
    private final WebSocketClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private boolean chatReload = true;
    private boolean timeReload = true;
    private boolean objectReload = true;

    public PlayerInputHandler(Player me) {
        this.me= me;
        this.timer = InGameTimer.getInstance();
        this.textureRegistry = TextureRegistry.getInstance();
        this.gameMenuProperties = GameMenuProperties.getInstance();
        this.objectRegistry = ObjectRegistry.getInstance();
        this.client = WebSocketClient.getInstance();
    }

    @SneakyThrows
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

        if(Gdx.input.isKeyPressed(Input.Keys.E) && objectReload){
            CleanCode cc = new CleanCode(me.getCoordinates());
            objectReload = false;
            client.send(new SimpleMessage(
                    MessageType.NEW_CLEAN_CODE,
                    objectMapper.writeValueAsString(new ObjectDto(cc.getId(), "clean_code", cc.getCoordinates())))
            );
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    objectReload = true;
                }
            }, 0.5f, 0, 1);
        }

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
