package com.bytevalue.settings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.Activity;
import com.bytevalue.ActivitySwitcher;
import com.bytevalue.Button;

public class BackButton extends Button {

    private ActivitySwitcher activitySwitcher;

    public BackButton(Viewport viewport, ActivitySwitcher activitySwitcher) {
        super(viewport);
        this.activitySwitcher = activitySwitcher;
        setZIndex(100);
    }

    @Override
    public void action() {
        activitySwitcher.switchActivity(Activity.GAME);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(606,268,72,72);
    }

}
