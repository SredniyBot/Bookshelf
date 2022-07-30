package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.Score;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;
import com.bytevalue.service.VibrationService;

public class LossStage extends Stage {

    private Score score;

    LossStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        score = new Score(695);
        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getLossTexture(),0,0);
            }
        };

        Button mainMenu = new Button(viewport) {
            @Override
            public void action() {
                VibrationService.vibrate(20);
                SoundService.playMenuSound();
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,405,313,77);
            }
        };
        Button again = new Button(viewport) {
            @Override
            public void action() {
                VibrationService.vibrate(20);
                SoundService.playMenuSound();
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
                activitySwitcher.startGame();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,528,313,77);
            }
        };

        addActor(mainMenu);
        addActor(again);
        addActor(background);
        addActor(score);
    }

    public void setScore(int score) {
        this.score.toZero();
        this.score.increaseScore(score);
    }
}
