package com.bytevalue;

import com.badlogic.gdx.Gdx;

public class Vibrator {
    public static void vibrate(int milliseconds){
        Gdx.input.vibrate(40);
    }
}
