package com.bytevalue.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Button;
import com.bytevalue.service.TextureService;
import com.bytevalue.service.VibrationService;

public class VibrationButton extends Button {

    boolean vibrates;

    public VibrationButton(Viewport viewport) {
        super(viewport);
        Preferences prefs = Gdx.app.getPreferences("main");
        vibrates = prefs.getBoolean("v",true);
        setZIndex(100000);
    }



    @Override
    public void action() {
        vibrates=!vibrates;
        Preferences prefs = Gdx.app.getPreferences("main");
        prefs.putBoolean("v",vibrates);
        VibrationService.setVibrate(vibrates);
        VibrationService.vibrate(120);
        prefs.flush();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return TextureService.getVibrateTexture(vibrates);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(540,700,72,72);
    }
}
