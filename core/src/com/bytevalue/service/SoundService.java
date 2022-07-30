package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.RandomXS128;

public class SoundService {
    private static Sound stand1;
    private static Sound stand2;
    private static Sound stand3;


    private static Sound menu1;

    private static Sound move;
    private static Sound boom;
    private static Sound peep;
    private static Sound penny;
    private static Sound paper;


    private static Sound shelfMove;
    private static long lastMoveTime=0;

    private static boolean snd;

    public static void init(Skin skin){
        dispose();
        snd= Gdx.app.getPreferences("main").getBoolean("s",true);
        stand1 = Gdx.audio.newSound(Gdx.files.internal(skin.getStand1Sound()));
        stand2 = Gdx.audio.newSound(Gdx.files.internal(skin.getStand2Sound()));
        stand3 = Gdx.audio.newSound(Gdx.files.internal(skin.getStand3Sound()));
        menu1 = Gdx.audio.newSound(Gdx.files.internal(skin.getMenuSound()));
        move = Gdx.audio.newSound(Gdx.files.internal(skin.getMoveSound()));
        shelfMove = Gdx.audio.newSound(Gdx.files.internal(skin.getShelfSound()));
        boom = Gdx.audio.newSound(Gdx.files.internal("sounds/utils/boom.wav"));
        peep = Gdx.audio.newSound(Gdx.files.internal("sounds/utils/peep.wav"));
        penny = Gdx.audio.newSound(Gdx.files.internal("sounds/utils/peny.wav"));
        paper = Gdx.audio.newSound(Gdx.files.internal("sounds/utils/paper.wav"));
    }


    public static void playStandSound(){
        if(snd)
        switch (new RandomXS128().nextInt(3)){
            case 0:
                stand1.play();
                break;
            case 1:
                stand2.play();
                break;
            case 2:
                stand3.play();
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
            move.play(0.5f);
            lastMoveTime =System.currentTimeMillis();
        }
    }


    public static void playShelfMoveSound(){
        if(snd)
            shelfMove.play(0.6f);
    }
    public static void setSound(boolean s) {
        snd= s;
    }


    public static void dispose(){
        if (stand1!=null)
            stand1.dispose();
        if (stand2!=null)
            stand2.dispose();
        if (stand3!=null)
            stand3.dispose();
        if (move!=null)
            move.dispose();
        if (shelfMove!=null)
            shelfMove.dispose();
        if (menu1!=null)
            menu1.dispose();
        if (boom!=null)
            boom.dispose();
        if (peep!=null)
            peep.dispose();
        if (penny!=null)
            penny.dispose();

    }

    public static void playBoomSound(){
        if(snd)
            boom.play(0.6f);
    }
    public static void playPeepSound(){
        if(snd)
            peep.play();
    }


    public static void playPennySound(){
        if(snd)penny.play();
    }
    public static void playPaperSound(){
        if(snd)paper.play();
    }
    public static void playBadMenuSound() {

    }
}
