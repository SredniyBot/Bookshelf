package com.bytevalue.service;

import com.badlogic.gdx.Gdx;

public class VibrationService {
    public static void vibrate(int milliseconds){
        Gdx.input.vibrate(40);
    }
}
