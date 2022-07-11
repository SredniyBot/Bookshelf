package com.bytevalue.settings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bytevalue.service.TextureService;

public class SettingsBackground extends Actor {

    private final TextureRegion textureRegion;

    public SettingsBackground() {
        textureRegion = TextureService.getSettingsTexture();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion,0,0);
    }


}
