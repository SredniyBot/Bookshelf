package com.bytevalue.inter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;
import com.bytevalue.service.VibrationService;

public class QuiteStage extends Stage {

    QuiteStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);

        Button yes = new Button(viewport){
            @Override
            public void action() {
                SoundService.playMenuSound();
                VibrationService.vibrate(40);
                Gdx.app.exit();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,558,137,77);
            }
        };
        Button no = new Button(viewport){
            @Override
            public void action() {
                SoundService.playMenuSound();
                VibrationService.vibrate(40);
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(658,558,137,77);
            }
        };

        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getQuitMenuTexture(),0,0);
            }
        };
        addActor(yes);
        addActor(no);
        addActor(background);
    }
}
