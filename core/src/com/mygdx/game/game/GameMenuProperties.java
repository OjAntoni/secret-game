package com.mygdx.game.game;

public class GameMenuProperties {
    private static final GameMenuProperties instance = new GameMenuProperties();

    public static GameMenuProperties getInstance(){
        return instance;
    }

    public boolean isChatShown = true;
    public boolean isTimeShown = true;
}
