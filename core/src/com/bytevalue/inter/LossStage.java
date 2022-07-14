package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.TextureService;

public class LossStage extends Stage {

    LossStage(Viewport viewport, ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getLossTexture(),0,0);
            }
        };
    }

    public void setScore(int score) {

    }
}
