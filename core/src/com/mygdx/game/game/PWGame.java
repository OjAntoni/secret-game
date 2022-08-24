package com.mygdx.game.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MainMenuScreen;


public class PWGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public MainMenuScreen mainMenuScreen;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        mainMenuScreen = new MainMenuScreen(this);
        this.setScreen(mainMenuScreen);
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
