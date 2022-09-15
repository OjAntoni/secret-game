package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.game.ChatService;
import com.mygdx.game.game.GameMenuProperties;
import com.mygdx.game.game.GameService;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.messages.messages.ChatMessage;
import com.mygdx.game.util.Properties;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class GameScreen implements Screen{
    final PWGame game;
    Music backgroundMusic;
    Sound looseSound;
    OrthographicCamera camera;
    GameService gameService;
    ChatService chatService;
    Stage stage;
    Table table;
    Skin skin;
    TextArea textArea;
    ScrollPane scrollPane;
    GameMenuProperties gameMenuProperties;


    @SneakyThrows
    public GameScreen(final PWGame game) {
        loadMusic();
        configMusic();
        configCamera();
        this.game = game;
        this.gameService = new GameService();
        chatService = ChatService.getInstance();
        gameMenuProperties = GameMenuProperties.getInstance();
        skin = game.skin;
    }


    private void configCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Properties.SCREEN_WIDTH, Properties.SCREEN_HEIGHT);
    }

    private void configMusic() {
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    private void loadMusic() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("plants_vs_zombies_28 - Graze the Roof (in game).mp3"));
        looseSound = Gdx.audio.newSound(Gdx.files.internal("421872__theuncertainman__loose-archers-british-male.mp3"));
    }

    @Override
    @SneakyThrows
    public void render(float delta) {
        gameService.sendMyCoordinates();

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        if(gameMenuProperties.isTimeShown){
            drawTimeMenu();
        }
        gameService.drawPlayers(game.batch);
        gameService.drawObjects(game.batch);
        gameService.drawActors(game.batch);

        game.batch.end();

        gameService.handleInput();
        if (gameService.checkForPlayerForLoss()) {
            gameService.sendLossMessage();
            gameService.deleteMyPlayer();
        }

        updateChat();
        if(gameMenuProperties.isChatShown){
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    private void updateChat() {
        if(!gameMenuProperties.isChatShown){
            stage.unfocusAll();
        }
        List<ChatMessage> messages = chatService.getMessages();
        messages.forEach(
                m -> {
                    Label label = new Label(
                            String.format("%s: %s", m.getFrom(), m.getPayload()), skin);
                    label.setWrap(true);
                    label.setFontScale(0.75f, 0.75f);
                    table.add(label).width(Properties.SCREEN_WIDTH * 0.2f).row();
                }
        );
        if(messages.size()!=0){
            scrollPane.scrollTo(0, 0, 0, 0);
        }
        messages.clear();
    }

    private void drawTimeMenu() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Time survived: " + gameService.getTime() + "s", 5, Properties.SCREEN_HEIGHT-5);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        backgroundMusic.play();
        gameService.startGame();
        configureChatWindow();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    @SneakyThrows
    public void dispose() {
        backgroundMusic.dispose();
        looseSound.dispose();
        gameService.endGame();
    }

    private void configureChatWindow() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setHeight(Properties.SCREEN_HEIGHT * 0.8f);
        table.top();
        scrollPane = new ScrollPane(table);
        scrollPane.setPosition(Properties.SCREEN_WIDTH * 0.8f, Properties.SCREEN_HEIGHT * 0.2f);
        scrollPane.setWidth(Properties.SCREEN_WIDTH * 0.2f);
        scrollPane.setHeight(Properties.SCREEN_HEIGHT * 0.8f);
        scrollPane.addListener(new InputListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if(!gameMenuProperties.isChatShown) return false;
                if(x < scrollPane.getX()){
                    stage.unfocus(scrollPane);
                } else {
                    stage.setScrollFocus(scrollPane);
                }
                return true;
            }
        });
        stage.addActor(scrollPane);
        stage.setScrollFocus(scrollPane);

        textArea = new TextArea("", skin);
        textArea.setPosition(Properties.SCREEN_WIDTH * 0.8f, Properties.SCREEN_HEIGHT * 0.08f);
        textArea.setWidth(Properties.SCREEN_WIDTH * 0.2f);
        textArea.setHeight(Properties.SCREEN_HEIGHT * 0.12f);
        stage.addActor(textArea);

        TextButton sendButton = new TextButton("Send",  skin);
        sendButton.setPosition(Properties.SCREEN_WIDTH * 0.8f, 10);
        sendButton.setWidth(Properties.SCREEN_WIDTH * 0.2f);
        sendButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!gameMenuProperties.isChatShown) return;
                stage.unfocus(textArea);
                String text = textArea.getText();
                textArea.setText("");
                if(text.equals("")){
                   return;
                }
                gameService.sendMessageToChat(text);
            }
        });


        stage.addActor(sendButton);

    }
}
