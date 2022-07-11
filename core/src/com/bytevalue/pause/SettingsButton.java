package com.bytevalue.pause;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Activity;
import com.bytevalue.ActivitySwitcher;
import com.bytevalue.Button;
import com.bytevalue.service.SoundService;

public class SettingsButton extends Button {

    private final ActivitySwitcher activitySwitcher;

    public SettingsButton(Viewport viewport, ActivitySwitcher activitySwitcher) {
        super(viewport);
        this.activitySwitcher = activitySwitcher;
    }

    @Override
    public void action() {
        activitySwitcher.switchActivity(Activity.SETTINGS);
        SoundService.playMenuSound();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(483,718,312,77);
    }

}
