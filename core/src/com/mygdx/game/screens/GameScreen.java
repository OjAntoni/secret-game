package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.game.GameService;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.util.Properties;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class GameScreen implements Screen {
    final PWGame game;
    Music backgroundMusic;
    Sound looseSound;
    OrthographicCamera camera;
    GameService gameService;


    @SneakyThrows
    public GameScreen(final PWGame game) {
        loadMusic();
        configMusic();
        configCamera();
        this.game = game;
        this.gameService = new GameService();
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
        gameService.sendMyCoordinates();

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        drawMenu();
        gameService.drawPlayers(game.batch);
        gameService.drawActors(game.batch);
        game.batch.end();

        gameService.handleInput();
        if (gameService.checkForPlayerForLoss()) {
            gameService.sendLossMessage();
            gameService.deleteMyPlayer();
        }

    }

    private void drawMenu() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Time survived: " + gameService.getTime() + "s", 0, Properties.SCREEN_HEIGHT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        backgroundMusic.play();
        gameService.startGame();
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
    @SneakyThrows
    public void dispose() {
        backgroundMusic.dispose();
        looseSound.dispose();
        gameService.endGame();
    }

}
