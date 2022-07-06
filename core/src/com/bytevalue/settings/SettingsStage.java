package com.bytevalue.settings;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.ActivitySwitcher;

public class SettingsStage extends Stage {
    SettingsBackground bg;


    public SettingsStage(Viewport viewport, ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        bg = new SettingsBackground();

        VibrationButton sb = new VibrationButton(viewport);
        SoundButton mb = new SoundButton(viewport);
        BackButton bb = new BackButton(viewport,activitySwitcher);
        addActor(bg);
        addActor(bb);
        addActor(sb);
//        addActor(cb);
        addActor(mb);


    }

    public void setBackground(TextureRegion background) {
        bg.setBackground(background);
    }
}
