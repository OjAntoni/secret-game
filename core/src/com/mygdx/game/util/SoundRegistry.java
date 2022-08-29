package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundRegistry {
    private final static SoundRegistry instance = new SoundRegistry();
    public final Sound boomSound = Gdx.audio.newSound(Gdx.files.internal("587193_derplayer_explosion-big-02 (online-audio-converter.com).mp3"));

    public static synchronized SoundRegistry getInstance(){
        return instance;
    }
}
