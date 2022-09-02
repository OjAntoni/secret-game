package com.mygdx.game.util;

import com.badlogic.gdx.utils.Timer;
import lombok.Getter;

public class InGameTimer {
    private static final InGameTimer instance = new InGameTimer();
    @Getter
    private int timeMillis;
    private Timer.Task timer;

    public static InGameTimer getInstance(){
        return instance;
    }

    public void start(){
        timer = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeMillis++;
            }
        }, 0.001f, 0.001f);
    }

    public int getTime(){
        return timeMillis/1000;
    }


    public void stop(){
        timer.cancel();
    }

    public void clear(){
        timeMillis =0;
    }
}
