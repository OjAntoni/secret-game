package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.actors.Coordinates;
import com.mygdx.game.util.SoundRegistry;
import com.mygdx.game.util.TextureRegistry;

import java.util.function.Function;

public class NetworkingBook extends GameObject{
    private Coordinates dst;
    private Function<Float, Float> moveFunction;
    private boolean isOutdated;
    private boolean inExplosionMode;
    private int pace;
    private Texture boomTexture;
    private Sound boomSound;
    private Book dstBook;

    public NetworkingBook(Coordinates srs, Coordinates dst, Book dstBook) {
        this.dstBook = dstBook;
        texture = TextureRegistry.networkBookTexture;
        boomSound = SoundRegistry.boomSound;
        boomTexture = TextureRegistry.boomTexture;
        rectangle = new Rectangle(srs.x, srs.y, texture.getWidth()/6f, texture.getHeight()/6f);
        this.dst = dst;
        moveFunction = (x) -> (dst.y - srs.y)/(dst.x - srs.x) * (x-srs.x) + srs.y;
        pace = 5;
        inExplosionMode = false;
    }

    @Override
    public boolean isOutdated(long currentTime) {
        return isOutdated;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Coordinates calculateNewCoordinates() {
        if(!inExplosionMode && Math.abs(dst.x-rectangle.x+ rectangle.getWidth()/2) > pace){
            rectangle.x += pace;
            rectangle.y = moveFunction.apply(rectangle.x);
            return new Coordinates(rectangle.x, rectangle.y);
        }
        if (!inExplosionMode){
            inExplosionMode = true;
            dstBook.setAsDeleted();
            rectangle.x = dst.x- rectangle.width/2;
            rectangle.y = dst.y - rectangle.height/2;
            texture = boomTexture;
            rectangle.width = boomTexture.getWidth()/10f;
            rectangle.height = boomTexture.getHeight()/10f;
            boomSound.play(0.1f);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isOutdated = true;
                }
            },3f,0f,1);
        }
        return new Coordinates(rectangle.x, rectangle.y);
    }

    @Override
    public void dispose() {
        super.dispose();
        boomSound.dispose();
        boomTexture.dispose();
    }
}
