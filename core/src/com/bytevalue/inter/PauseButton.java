package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;

public class PauseButton extends Button {


    private final ActivitySwitcher activitySwitcher;
    private final Pauser pauser;


    public PauseButton(Viewport viewport, ActivitySwitcher activitySwitcher,Pauser pauser){
        super(viewport);
        this.pauser=pauser;
        this.activitySwitcher=activitySwitcher;

    }


    @Override
    public void action() {
        if (!pauser.isInStartPosition()) {
            activitySwitcher.switchActivity(Activity.PAUSE);
            SoundService.playMenuSound();
        }
    }

    @Override
    public TextureRegion getTextureRegion() {
        if (!pauser.isInStartPosition())
        return TextureService.getPauseTexture();
        else return null;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(858-10,1216-10,32+20,44+20);
    }

    @Override
    public float getRectX() {
        return 858;
    }

    @Override
    public float getRectY() {
        return 1216;
    }
}
