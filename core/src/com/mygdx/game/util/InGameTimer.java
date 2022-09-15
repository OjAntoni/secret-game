package com.mygdx.game.util;

import com.badlogic.gdx.utils.Timer;
import lombok.Getter;

public class InGameTimer {
    private static final InGameTimer instance = new InGameTimer();
    @Getter
    private int timeMillis;
    private int timeS;
    private Timer.Task timerMs;
    private Timer.Task timerS;

    public static InGameTimer getInstance(){
        return instance;
    }

    public void start(){
        timerMs = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeMillis++;
            }
        }, 0.001f, 0.001f);
        timerS = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timeS++;
            }
        }, 1f, 1f);
    }

    public int getTime(){
        return timeS;
    }


    public void stop(){
        timerMs.cancel();
        timerS.cancel();
    }

    public void clear(){
        timeMillis =0;
        timeS = 0;
    }
}
