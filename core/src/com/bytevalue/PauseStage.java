package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseStage extends Stage {

    Viewport viewport;


    PauseStage(Viewport viewport){
        this.viewport=viewport;
        setViewport(viewport);

    }


}
