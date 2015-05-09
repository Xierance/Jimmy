package com.mygdx.game.resources;

/**
 * Created by for example John on 5/4/2015.
 */
public class timer {
    int time;
    public timer (int Time){
        time  = Time;

    }

    public int getTime(){
        return time;
    }
    public void incrementTime(){
        time++;

    }
}
