package com.mygdx.game.actors;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersRegistry {
    private static final PlayersRegistry instance = new PlayersRegistry();
    public static PlayersRegistry getInstance(){
        return instance;
    }

    private final List<Player> players = new ArrayList<>();
    private PlayersRegistry(){}

    public void add(Player player){
        if(player.getId()==null){
            return;
        }
        players.add(player);
    }

    public Player get(String id){
        for (Player player : players) {
            if(player.getId()!=null && player.getId().equals(id)){
                return player;
            }
        }
        return null;
    }

    public void remove(String id){
        players.removeIf(p -> p.getId().equals(id));
    }

    public List<Player> getAll(){
        return new ArrayList<>(players);
    }
}
