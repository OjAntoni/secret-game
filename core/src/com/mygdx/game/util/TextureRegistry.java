package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureRegistry {
    private static final TextureRegistry instance = new TextureRegistry();
    public final Texture bookTexture = new Texture(Gdx.files.internal("screenshot-58-jpg-1-1600x900-product_popup.png"));
    public final Texture networkBookTexture = new Texture(Gdx.files.internal("9780128182000.jpg"));
    public final Texture boomTexture = new Texture(Gdx.files.internal("8e4eca42b17e4b3.png"));
    public final Texture nzalTexture = new Texture(Gdx.files.internal("heart.png"));
    public final Texture linuxTexture = new Texture(Gdx.files.internal("Tux.svg.png"));

    public static synchronized TextureRegistry getInstance(){
        return instance;
    }
}
