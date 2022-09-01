package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.PlayersRegistry;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.game.InputHandler;
import com.mygdx.game.game.StudentInputHandler;
import com.mygdx.game.net.WebSocketClient;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.Properties;
import com.mygdx.game.util.TextureRegistry;
import lombok.SneakyThrows;

import java.util.List;


public class GameScreen implements Screen {
    final PWGame game;
    Music backgroundMusic;
    Sound looseSound;
    OrthographicCamera camera;
    InputHandler studentInputHandler;
    InGameTimer timer;
    WebSocketClient webSocketClient = new WebSocketClient();
    PlayersRegistry playersRegistry = PlayersRegistry.getInstance();
    ObjectMapper objectMapper = new ObjectMapper();
    Player me;

    public GameScreen(final PWGame game) {
        loadMusic();
        configMusic();
        configCamera();
        this.game = game;
        this.timer = InGameTimer.getInstance();
        String myId = webSocketClient.getSession().getId();
        System.out.println("my id :   " + myId);
        me = playersRegistry.get(myId);
        studentInputHandler = new StudentInputHandler(me, camera);
    }

    private void configCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Properties.SCREEN_WIDTH, Properties.SCREEN_HEIGHT);
    }

    private void configMusic() {
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    private void loadMusic() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("plants_vs_zombies_28 - Graze the Roof (in game).mp3"));
        looseSound = Gdx.audio.newSound(Gdx.files.internal("421872__theuncertainman__loose-archers-british-male.mp3"));
    }


    @Override
    @SneakyThrows
    public void render(float delta) {
        webSocketClient.send(objectMapper.writeValueAsString(me.getCoordinates()));

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        drawMenu();
        List<Player> all = playersRegistry.getAll();
        for (Player player : all) {
            player.draw(game.batch, TextureRegistry.getInstance().studentTexture);
        }
        game.batch.end();
        studentInputHandler.handleInput();

    }

    private void drawMenu() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Time survived: " + timer.getTime() + "s", 0, Properties.SCREEN_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        backgroundMusic.play();
        timer.start();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        looseSound.dispose();
        webSocketClient.close();
    }

}
