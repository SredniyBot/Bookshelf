package com.bytevalue.pause;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseStage extends Stage {

    Viewport viewport;


    public PauseStage(Viewport viewport){
        this.viewport=viewport;
        setViewport(viewport);

    }


}
