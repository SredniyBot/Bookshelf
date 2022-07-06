package com.bytevalue.pause;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.ActivitySwitcher;

public class PauseStage extends Stage {

    private PauseBackground bg;


    public PauseStage(Viewport viewport, ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        SettingsButton sb = new SettingsButton(viewport,activitySwitcher);
        MainMenuButton mb = new MainMenuButton(viewport,activitySwitcher);
        ContinueButton cb = new ContinueButton(viewport,activitySwitcher);
        bg = new PauseBackground();

        addActor(sb);
        addActor(cb);
        addActor(mb);
        addActor(bg);

    }


    public void setBackground(TextureRegion background) {
        bg.setBackground(background);
    }
}
