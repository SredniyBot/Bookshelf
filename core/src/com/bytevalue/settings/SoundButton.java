package com.bytevalue.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Button;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;

public class SoundButton extends Button {


    boolean sound;

    public SoundButton(Viewport viewport) {
        super(viewport);
        Preferences prefs = Gdx.app.getPreferences("main");
        sound = prefs.getBoolean("s",true);
        setZIndex(100000);
    }


    @Override
    public void action() {
        sound=!sound;
        Preferences prefs = Gdx.app.getPreferences("main");
        prefs.putBoolean("s",sound);
        SoundService.setSound(sound);
        SoundService.playMenuSound();
        prefs.flush();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return TextureService.getSoundTexture(sound);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(666,700,72,72);
    }
}
