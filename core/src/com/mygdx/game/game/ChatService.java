package com.mygdx.game.game;

import com.mygdx.game.messages.messages.ChatMessage;
import com.mygdx.game.net.WebSocketClient;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private static final ChatService instance = new ChatService();
    private final WebSocketClient webSocketClient = WebSocketClient.getInstance();

    public static ChatService getInstance() {
        return instance;
    }

    @Getter
    private List<ChatMessage> messages = new ArrayList<>();

    public void add(ChatMessage chatMessage){
        messages.add(chatMessage);
    }
}
