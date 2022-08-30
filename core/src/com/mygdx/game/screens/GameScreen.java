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
import com.mygdx.game.actors.Ato;
import com.mygdx.game.game.PWGame;
import com.mygdx.game.game.PositionHandler;
import com.mygdx.game.game.StudentInputHandler;
import com.mygdx.game.objects.LinuxPenguin;
import com.mygdx.game.objects.Niezaliczone;
import com.mygdx.game.util.ActorsRegistry;
import com.mygdx.game.util.InGameTimer;
import com.mygdx.game.util.ObjectRegistry;
import com.mygdx.game.util.Properties;
import com.mygdx.game.actors.Actor;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.objects.CleanCodeBook;

import java.util.List;
import java.util.function.Function;


public class GameScreen implements Screen {
    final PWGame game;
    Music backgroundMusic;
    Sound looseSound;
    OrthographicCamera camera;
    ActorsRegistry actorsRegistry;
    ObjectRegistry objectRegistry;
    PositionHandler studentPositionHandler;
    InGameTimer timer;

    public GameScreen(final PWGame game) {
        this.game = game;
        this.objectRegistry = ObjectRegistry.getInstance();
        this.actorsRegistry = new ActorsRegistry();
        this.studentPositionHandler = new StudentInputHandler(actorsRegistry.get("student"));
        this.timer = InGameTimer.getInstance();
        loadMusic();
        configMusic();
        configCamera();
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

        game.renderer.setAutoShapeType(true);
        game.renderer.begin();
        actorsRegistry.drawAll(game.renderer);
        game.renderer.end();

        game.batch.begin();
        drawMenu();
        drawObjects();
        drawActors();
        game.batch.end();

        studentPositionHandler.handleInput();

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            CleanCodeBook cleanCodeBook = new CleanCodeBook(new Coordinates(touchPos.x, touchPos.y), timer.getTime());
            objectRegistry.add(cleanCodeBook);
        }

        checkIfJstarCollapsesWithCleanCode();
        checkIfWilkCollapsesWithNiezaliczone();
        checkIfStudentCollapsesWithLinux();

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

    private void checkIfStudentCollapsesWithLinux() {
        Actor student = actorsRegistry.getCurrent("student");
        List<LinuxPenguin> linuxes = objectRegistry.getAll(LinuxPenguin.class);
        linuxes.forEach(l -> {
            if (student.getRectangle().overlaps(l.getRectangle())) {
                l.setAsDeleted();
                if (student.canBeMoved()) {
                    student.stop(3);
                }
            }
        });
    }


    private void checkIfWilkCollapsesWithNiezaliczone() {
        Actor wilk = actorsRegistry.getCurrent("wilk");
        if (wilk != null) {
            for (Niezaliczone nzal : objectRegistry.getAll(Niezaliczone.class)) {
                if (wilk.getRectangle().contains(nzal.getRectangle())) {
                    wilk.deleteFromGame();
                }
            }
        }
    }

    private void checkIfJstarCollapsesWithCleanCode() {
        if (actorsRegistry.getCurrent("jstar") == null) {
            return;
        }
        for (CleanCodeBook cleanCodeBook : objectRegistry.getAll(CleanCodeBook.class)) {
            Actor jstar = actorsRegistry.getCurrent("jstar");
            if (jstar.getRectangle().contains(cleanCodeBook.getRectangle())) {
                jstar.stop(5);
                jstar.setInitialPace(jstar.getPace() * 0.8f);
            }
        }
    }

    private boolean studentLoosedTheGame() {
        Actor student = actorsRegistry.getCurrent("student");
        return isStudentEatenByJstar(student) || isStudentCatchedNzal(student) || isStudentDeadByLaser(student);
    }

    private boolean isStudentDeadByLaser(Actor student) {
        Ato ato = (Ato) actorsRegistry.getCurrent("ato");
        if (ato != null) {
            Function<Float, Float> lineFunction = ato.getLineFunction();
            Float yOfLaserIntersection = lineFunction.apply(student.getRectangle().x);
            boolean caughtByLaser = yOfLaserIntersection > student.getRectangle().y && yOfLaserIntersection < student.getRectangle().y + student.getRectangle().height;
            return caughtByLaser
                    && student.getRectangle().x > ato.getX0()
                    && ato.isLaserActive();
        }
        return false;
    }

    private boolean isStudentCatchedNzal(Actor student) {
        return objectRegistry.getAll(Niezaliczone.class)
                .stream()
                .anyMatch(nzal -> student.getRectangle()
                        .contains(nzal.getRectangle()));
    }

    private boolean isStudentEatenByJstar(Actor student) {
        if (actorsRegistry.getCurrent("jstar") == null) {
            return false;
        }
        return actorsRegistry.getCurrent("jstar").getRectangle().contains(student.getRectangle());
    }


    private void drawMenu() {
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Time survived: " + timer.getTime() + "s", 0, Properties.SCREEN_HEIGHT);
    }

    private void drawObjects() {
        objectRegistry.drawAll(game.batch);
    }


    private void drawActors() {
        actorsRegistry.drawAll(game.batch);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        backgroundMusic.play();
        timer.start();
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
