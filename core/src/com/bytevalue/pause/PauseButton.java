package com.bytevalue.pause;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Activity;
import com.bytevalue.ActivitySwitcher;
import com.bytevalue.Button;
import com.bytevalue.service.TextureService;

public class PauseButton extends Button {


    private final ActivitySwitcher activitySwitcher;



    public PauseButton(Viewport viewport, ActivitySwitcher activitySwitcher){
        super(viewport);
        this.activitySwitcher=activitySwitcher;
    }


    @Override
    public void action() {
        activitySwitcher.switchActivity(Activity.PAUSE);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return TextureService.getPauseTexture();
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
