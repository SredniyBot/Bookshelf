package com.bytevalue.pause;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bytevalue.service.TextureService;

public class PauseBackground extends Actor {

    private final TextureRegion textureRegion;
    private TextureRegion back;

    public PauseBackground() {
        textureRegion = TextureService.getPauseMenuTexture();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion,0,0);
    }

    public void setBackground(TextureRegion background) {
        back=background;
    }
}
