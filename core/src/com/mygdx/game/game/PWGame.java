package com.mygdx.game.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.MainMenuScreen;


public class PWGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public MainMenuScreen mainMenuScreen;
    public ShapeRenderer renderer;
    public Skin skin;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        mainMenuScreen = new MainMenuScreen(this);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        this.setScreen(mainMenuScreen);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        mainMenuScreen.gameScreen.dispose();
        mainMenuScreen.dispose();
    }
}
