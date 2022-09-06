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
import lombok.extern.java.Log;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@Log
public class GameService {
    private final InputHandler studentInputHandler;
    private final InGameTimer timer = InGameTimer.getInstance();
    private final WebSocketClient webSocketClient = new WebSocketClient();
    private final PlayersRegistry playersRegistry = PlayersRegistry.getInstance();
    private final ActorsRegistry actorsRegistry = ActorsRegistry.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Player me;

    public GameService() {
        me = playersRegistry.getMe();
        studentInputHandler = new StudentInputHandler(me);
    }

    @SneakyThrows
    public void sendMyCoordinates() {
        if (me != null && webSocketClient.getSession().isOpen())
            webSocketClient.send(
                    new SimpleMessage(MessageType.PLAYER_COORD,
                            objectMapper.writeValueAsString(new PlayerPositionDto(me.getCoordinates(), me.getId())))
            );
    }

    public void drawPlayers(SpriteBatch batch) {
        List<Player> all = playersRegistry.getAll();
        for (Player player : all) {
            player.draw(batch, TextureRegistry.getInstance().studentTexture);
        }
        if (me != null) {
            me.draw(batch, TextureRegistry.getInstance().studentTexture);
        }
    }

    public void drawActors(SpriteBatch batch) {
        actorsRegistry.getCurrentActors().forEach((id, actor) -> actor.draw(batch));
    }

    public void handleInput() {
        if (playersRegistry.getMe() != null) {
            studentInputHandler.handleInput();
        }
    }

    public int getTime() {
        return timer.getTime();
    }

    public void startGame() {
        timer.start();
    }

    @SneakyThrows
    public void endGame() {
        if (webSocketClient.getSession().isOpen()) {
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

    public boolean checkForPlayerForLoss() {
        if (me == null) {
            return false;
        }
        boolean isGameLost = actorsRegistry.getCurrentActors().values().stream().anyMatch(a -> a.isGameLost(me));
        if (isGameLost) {
            log.info("Player's game is lost :(");
            timer.stop();
        }
        return isGameLost;
    }

    @SneakyThrows
    public void sendLossMessage() {
        webSocketClient.send(
                new SimpleMessage(
                        MessageType.PLAYER_LOSS,
                        objectMapper.writeValueAsString(
                                new PlayerPositionDto(me.getCoordinates(), me.getId())
                        )
                )
        );
    }

    @SneakyThrows
    public void deleteMyPlayer() {
        webSocketClient.send(
                new SimpleMessage(
                        MessageType.DELETE_PLAYER,
                        objectMapper.writeValueAsString(
                                new PlayerPositionDto(
                                        me.getCoordinates(),
                                        me.getId()
                                )
                        )
                )
        );
        me = null;
    }
}
