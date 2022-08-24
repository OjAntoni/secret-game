package com.mygdx.game.game;

import lombok.Getter;

import java.util.List;

public enum GameLevel {
    ONE(List.of("jstar", "zawadski")),
    TWO(List.of("jstar", "zawadski")),
    THREE(List.of("jstar", "zawadski", "ato")),
    FOUR(List.of("jstar", "zawadski", "ato", "misiuk"));

    @Getter
    private final List<String> actorNamesInGame;

    GameLevel(List<String> actorNamesInGame) {
        this.actorNamesInGame = actorNamesInGame;
    }

    public static GameLevel getLevel(int level){
        switch (level){
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            default: return ONE;
        }
    }
}
