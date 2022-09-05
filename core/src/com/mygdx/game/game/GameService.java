package com.mygdx.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.game.actors.ActorsRegistry;
import com.mygdx.game.actors.player.Player;
import com.mygdx.game.actors.player.PlayersRegistry;
import com.mygdx.game.messages.messages.SimpleMessage;
import com.mygdx.game.messages.messages.dto.PlayerPositionDto;
import com.mygdx.game.messages.messages.types.MessageType;
import com.mygdx.game.net.WebSocketClient;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.TextureRegistry;
import lombok.SneakyThrows;
import org.springframework.web.socket.TextMessage;

import java.util.List;

public class GameService {
    private final InputHandler studentInputHandler;
    private final InGameTimer timer = InGameTimer.getInstance();
    private final WebSocketClient webSocketClient = new WebSocketClient();
    private final PlayersRegistry playersRegistry = PlayersRegistry.getInstance();
    private final ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Player me;

    public GameService() {
        me = playersRegistry.getMe();
        studentInputHandler = new StudentInputHandler(me);
    }

    @SneakyThrows
    public void sendMyCoordinates(){
        webSocketClient.send(
                new SimpleMessage(MessageType.PLAYER_COORD, objectMapper.writeValueAsString(new PlayerPositionDto(me.getCoordinates(), me.getId())))
        );
    }

    public void drawPlayers(SpriteBatch batch){
        List<Player> all = playersRegistry.getAll();
        for (Player player : all) {
            player.draw(batch, TextureRegistry.getInstance().studentTexture);
        }
        me.draw(batch, TextureRegistry.getInstance().studentTexture);
    }

    public void drawActors(SpriteBatch batch){
        actorsRegistry.getCurrentActors().forEach((id, actor) -> actor.draw(batch));
    }

    public void handleInput(){
        studentInputHandler.handleInput();
    }

    public int getTime(){
        return timer.getTime();
    }

    public void startGame(){
        timer.start();
    }

    @SneakyThrows
    public void endGame(){
        webSocketClient.getSession().sendMessage(new TextMessage(
                objectMapper.writeValueAsString(List.of(
                        new SimpleMessage(MessageType.DELETE_PLAYER,
                                objectMapper.writeValueAsString(
                                        new PlayerPositionDto(
                                                playersRegistry.getMe().getCoordinates(),
                                                playersRegistry.getMe().getId()
                                        ))
                        )
                ))
        ));
        webSocketClient.close();
    }
}
