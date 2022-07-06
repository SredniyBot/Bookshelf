package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.RandomXS128;

public class SoundService {
    private static final Sound sound1 = Gdx.audio.newSound(Gdx.files.internal("sound/stand1.wav"));
    private static final Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("sound/stand2.wav"));
    private static final Sound sound3 = Gdx.audio.newSound(Gdx.files.internal("sound/stand3.wav"));
    private static final Sound sound4 = Gdx.audio.newSound(Gdx.files.internal("sound/stand4.wav"));

    private static final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/move.wav"));
    private static long lastMoveTime=0;

    private static boolean snd;

    static {
        snd= Gdx.app.getPreferences("main").getBoolean("s",true);
    }

    public static void playStandSound(){
        if(snd)
        switch (new RandomXS128().nextInt(4)){
            case 0:
                sound1.play();
                break;
            case 1:
                sound2.play();
                break;
            case 2:
                sound3.play();
                break;
            case 3:
                sound4.play();

                break;
        }
    }

    public static void playMoveSound() {
        if(snd)
        if(System.currentTimeMillis() -lastMoveTime>300){
            sound.play();
            lastMoveTime =System.currentTimeMillis();
        }
    }

    public static void playMenuSound() {

    }

    public static void setSound(boolean s) {
        snd= s;
    }


}
