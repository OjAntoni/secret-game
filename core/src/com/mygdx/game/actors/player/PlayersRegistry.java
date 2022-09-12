package com.mygdx.game.actors.player;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class PlayersRegistry {
    private static final PlayersRegistry instance = new PlayersRegistry();
    public static PlayersRegistry getInstance(){
        return instance;
    }

    private final List<Player> players = new ArrayList<>();
    @Setter @Getter
    private Player me;
    private PlayersRegistry(){}

    public void add(Player player){
        log.info("Adding player: "+player);
        players.add(player);
    }

    public Player get(int id){
        for (Player player : players) {
            if(player.getId()==id){
                return player;
            }
        }
        return null;
    }

    public void remove(int id){
        players.removeIf(p -> p.getId()==id);
    }

    public List<Player> getAll(){
        return new ArrayList<>(players);
    }
}
