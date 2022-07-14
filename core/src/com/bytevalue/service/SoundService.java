package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.RandomXS128;

public class SoundService {
    private static Sound stand1;
    private static Sound stand2;
    private static Sound stand3;
    private static Sound stand4;


    private static Sound menu1;
    private static Sound menu2;
    private static Sound menu3;

    private static Sound move;
    private static long lastMoveTime=0;

    private static boolean snd;

    public static void init(){
        snd= Gdx.app.getPreferences("main").getBoolean("s",true);
        stand1 = Gdx.audio.newSound(Gdx.files.internal("sounds/stand/stand1.wav"));
        stand2 = Gdx.audio.newSound(Gdx.files.internal("sounds/stand/stand2.wav"));
        stand3 = Gdx.audio.newSound(Gdx.files.internal("sounds/stand/stand3.wav"));
        stand4 = Gdx.audio.newSound(Gdx.files.internal("sounds/stand/stand4.wav"));
        menu1 = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/menu4.wav"));
        menu2 = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/menu2.wav"));
        menu3 = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/menu3.wav"));
        move = Gdx.audio.newSound(Gdx.files.internal("sounds/move/move.wav"));
    }


    public static void playStandSound(){
        if(snd)
        switch (new RandomXS128().nextInt(4)){
            case 0:
                stand1.play();
                break;
            case 1:
                stand2.play();
                break;
            case 2:
                stand3.play();
                break;
            case 3:
                stand4.play();

                break;
        }
    }


    public static void playMenuSound(){

        if(snd)
            menu1.play();
//            switch (new RandomXS128().nextInt(3)){
//                case 0:
//
//                    break;
//                case 1:
//                    menu2.play();
//                    break;
//                case 2:
//                    menu3.play();
//                    break;
//            }
    }

    public static void playMoveSound() {
        if(snd)
        if(System.currentTimeMillis() -lastMoveTime>300){
            move.play();
            lastMoveTime =System.currentTimeMillis();
        }
    }



    public static void setSound(boolean s) {
        snd= s;
    }


    public static void dispose(){
        stand1.dispose();
        stand2.dispose();
        stand3.dispose();
        stand4.dispose();
        move.dispose();
    }
}
