package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.VibrationService;

public class StartStage extends Stage {

    StartStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        Button startButton = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.startGame();
                SoundService.playMenuSound();
                VibrationService.vibrate(40);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(433,810,313,77);
            }
        };
        Button settingsButton = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.SETTINGS);
                SoundService.playMenuSound();
                VibrationService.vibrate(10);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(433,210,313,77);
            }
        };
        Button themesButton = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.THEMES);
                SoundService.playMenuSound();
                VibrationService.vibrate(10);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(533,410,313,77);
            }
        };
        Button notesButton = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.NOTES);
                SoundService.playMenuSound();
                VibrationService.vibrate(10);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(533,10,313,77);
            }
        };

        addActor(startButton);
        addActor(notesButton);
        addActor(themesButton);
        addActor(settingsButton);
    }


}
