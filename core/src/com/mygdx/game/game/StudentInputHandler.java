package com.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.actors.Actor;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.objects.CleanCodeBook;

public class StudentInputHandler implements PositionHandler {
    private final Actor student;

    public StudentInputHandler(Actor student) {
        this.student = student;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            student.getRectangle().y += 2;
            student.getRectangle().x += 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            student.getRectangle().y -= 2;
            student.getRectangle().x += 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            student.getRectangle().y -= 2;
            student.getRectangle().x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            student.getRectangle().y += 2;
            student.getRectangle().x -= 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            student.getRectangle().y+=2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            student.getRectangle().x += 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            student.getRectangle().y-=2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            student.getRectangle().x -= 2;
        }



//        else if (Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            student.setCoordinates(new Coordinates(touchPos.x, touchPos.y));
//        }


    }
}
