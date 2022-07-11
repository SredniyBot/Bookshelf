package com.bytevalue.pause;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Activity;
import com.bytevalue.ActivitySwitcher;
import com.bytevalue.Button;
import com.bytevalue.service.SoundService;

public class ContinueButton extends Button {

    private TextureRegion textureRegion;
    private Rectangle rectangle;
    private ActivitySwitcher activitySwitcher;

    public ContinueButton(Viewport viewport, ActivitySwitcher activitySwitcher) {
        super(viewport);
        this.activitySwitcher = activitySwitcher;
    }

    @Override
    public void action() {
        activitySwitcher.switchActivity(Activity.GAME);
        SoundService.playMenuSound();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(483,468,312,77);
    }
}
