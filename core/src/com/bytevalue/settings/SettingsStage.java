package com.bytevalue.settings;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.ActivitySwitcher;

public class SettingsStage extends Stage {


    public SettingsStage(Viewport viewport, ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        SettingsBackground bg = new SettingsBackground();

        VibrationButton sb = new VibrationButton(viewport);
        SoundButton mb = new SoundButton(viewport);
        BackButton bb = new BackButton(viewport,activitySwitcher);
//        LanguageButton lb = new LanguageButton(viewport,activitySwitcher);     TODO

        addActor(bg);
        addActor(bb);
        addActor(sb);
        addActor(mb);


    }
}
