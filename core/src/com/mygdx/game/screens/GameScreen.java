package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game.GameLevel;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.objects.Niezaliczone;
import com.mygdx.game.util.ActorsRegistry;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;
import com.mygdx.game.actors.Actor;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.objects.Book;


public class GameScreen implements Screen {
    final PWGame game;
    Music backgroundMusic;
    Sound looseSound;
    OrthographicCamera camera;
    ActorsRegistry actorsRegistry;
    ObjectRegistry objectRegistry;
    private long timeSurvived;

    public GameScreen(final PWGame game) {
        this.game = game;
        this.objectRegistry = ObjectRegistry.getInstance();
        this.actorsRegistry = new ActorsRegistry();
//        actorsRegistry.updateInGameActors(GameLevel.ONE);
        loadMusic();
        configMusic();
        configCamera();
        startSecondsTimer();
//        startLevelTimer();
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
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        drawMenu();
        drawObjects();
        drawActors();
        game.batch.end();

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            Book book = new Book(new Coordinates(touchPos.x, touchPos.y), timeSurvived);
            objectRegistry.add(book);
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            Actor student = actorsRegistry.get("student");
            student.setCoordinates(new Coordinates(touchPos.x, touchPos.y));
        }

        checkIfJstarCollapsesWithCleanCode();
        checkIfWilkCollapsesWithNiezaliczone();

        if (studentLoosedTheGame()) {
            looseSound.play();
            try {
                Thread.sleep(2000);
                dispose();
                System.exit(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        actorsRegistry.updatePositions();
        objectRegistry.updatePositions();
    }

    private void checkIfWilkCollapsesWithNiezaliczone() {
        Actor wilk = actorsRegistry.get("wilk");
        if(wilk!=null){
            for (Niezaliczone nzal : objectRegistry.getAll(Niezaliczone.class)) {
                if(wilk.getRectangle().contains(nzal.getRectangle())){
                    wilk.deleteFromGame();
                }
            }
        }
    }

    private void checkIfJstarCollapsesWithCleanCode() {
        for (Book book : objectRegistry.getAll(Book.class)) {
            Actor jstar = actorsRegistry.get("jstar");
            if (jstar.getRectangle().contains(book.getRectangle())) {
                jstar.stop(5);
                jstar.setInitialPace(jstar.getPace()*0.8f);
            }
        }
    }

    private boolean studentLoosedTheGame() {
        Actor student = actorsRegistry.get("student");
        return isStudentEatenByJstar(student) || isStudentCatchedNzal(student);
    }

    private boolean isStudentCatchedNzal(Actor student) {
        return objectRegistry.getAll(Niezaliczone.class)
                .stream()
                .anyMatch(nzal -> student.getRectangle()
                .contains(nzal.getRectangle()));
    }

    private boolean isStudentEatenByJstar(Actor student) {
        return actorsRegistry.get("jstar").getRectangle().contains(student.getRectangle());
    }


    private void drawMenu() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Time survived: " + timeSurvived + "s", 0, Properties.SCREEN_HEIGHT);
    }

    private void drawObjects() {
        objectRegistry.drawAll(game.batch);
    }


    private void drawActors() {
        actorsRegistry.drawAll(game.batch);
    }

    public void startSecondsTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeSurvived++;
                objectRegistry.setTime(timeSurvived);
            }
        }, 1f, 1f);
    }

//    public void startLevelTimer() {
//        Timer.schedule(new Timer.Task() {
//            int level = 1;
//
//            @Override
//            public void run() {
//                actorsRegistry.updateInGameActors(GameLevel.getLevel(level));
//                level++;
//            }
//        }, 0f, 60f);
//    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        backgroundMusic.play();
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
    public void dispose() {
        actorsRegistry.dispose();
        backgroundMusic.dispose();
        looseSound.dispose();
    }

}
