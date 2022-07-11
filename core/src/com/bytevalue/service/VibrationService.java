package com.bytevalue.service;

import com.badlogic.gdx.Gdx;

public class VibrationService {
    private static boolean vibrate;
    static {
        vibrate= Gdx.app.getPreferences("main").getBoolean("v",true);
    }
    public static void vibrate(int milliseconds){
        if (vibrate)
        Gdx.input.vibrate(milliseconds);
    }


    public static void setVibrate(boolean b){
        vibrate = b;
    }

    public static void dispose(){

    }

}
