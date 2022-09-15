package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.util.Properties;


public class MainMenuScreen implements Screen {
    final PWGame game;
    OrthographicCamera camera;
    private Texture backgroundTexture;
    private Music mainMenuMusic;
    public Screen gameScreen;
    private Stage stage;
    private TextArea textArea;
    private final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    public MainMenuScreen(final PWGame game) {
        this.game = game;
        loadResources();
        configCamera();
        configMusic();
    }

    private void configMusic() {
        mainMenuMusic.setLooping(true);
        mainMenuMusic.play();
    }

    private void configCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Properties.SCREEN_WIDTH, Properties.SCREEN_HEIGHT);
    }

    private void loadResources() {
        backgroundTexture = new Texture("elektryczny.jpg");
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("plants_vs_zombies_02 - Crazy Dave (Intro Theme).mp3"));
    }


    @Override
    public void render(float delta) {

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        renderBackground();
        renderText();
        renderNameInput();
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gameScreen = new GameScreen(game);
            game.setScreen(gameScreen);
            dispose();
        }
    }

    private void renderNameInput() {

    }

    private void renderBackground() {
        game.batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
    }

    private void renderText(){
        Rectangle textBackground = new Rectangle();
        textBackground.setWidth(100);
        textBackground.setHeight(60);
        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "Welcome to elektryczny!!! ", Properties.SCREEN_WIDTH/2f, Properties.SCREEN_HEIGHT-20);
        game.font.draw(game.batch, "Press Enter to begin!", Properties.SCREEN_WIDTH/2f, Properties.SCREEN_HEIGHT-35);
    }

    @Override
    public void dispose() {
        mainMenuMusic.stop();
        backgroundTexture.dispose();
        mainMenuMusic.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        confidureInputNameTextField();
    }

    private void confidureInputNameTextField() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table menu = new Table();
        menu.setPosition(Properties.SCREEN_WIDTH*0.9f, Properties.SCREEN_HEIGHT*0.6f);
        stage.addActor(menu);

        textArea = new TextArea("", skin);
        textArea.setPrefRows(1);
        textArea.setMaxLength(15);
        textArea.setAlignment(Align.center);
        menu.add(textArea).row();

        TextButton play = new TextButton("PLAY", skin);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo
            }
        });

        menu.add(play).fill().pad(5, 0, 0, 0).row();


    }
}
