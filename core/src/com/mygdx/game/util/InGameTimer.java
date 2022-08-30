package com.mygdx.game.util;

import com.badlogic.gdx.utils.Timer;

public class InGameTimer {
    private static final InGameTimer instance = new InGameTimer();
    private int time;
    private Timer.Task timer;

    public static InGameTimer getInstance(){
        return instance;
    }

    public void start(){
        timer = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                time++;
            }
        }, 1f, 1f);
    }

    public int getTime(){
        return time;
    }

    public void stop(){
        timer.cancel();
    }

    public void clear(){
        time=0;
    }
}
