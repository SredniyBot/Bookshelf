package ru.bytevalue.service;

import com.badlogic.gdx.*;

public class SkinService {

    private static Skin currentSkin;


    public static void init(){
        Preferences preferences = Gdx.app.getPreferences("main");
        currentSkin = Skin.getSkinById(preferences.getInteger("skin",0));
        TextureService.init(currentSkin);
        SoundService.init(currentSkin);
        FontService.init(currentSkin.getScoreColor(), currentSkin.getNotesColor());
    }


    public static void setSkin(Skin skin){
        currentSkin= skin;
        Preferences preferences = Gdx.app.getPreferences("main");
        preferences.putInteger("skin",skin.getNumber());
        preferences.flush();
        TextureService.init(currentSkin);
        SoundService.init(currentSkin);
        FontService.init(currentSkin.getScoreColor(), currentSkin.getNotesColor());
    }

    public static Skin getCurrentSkin() {
        return currentSkin;
    }
}
