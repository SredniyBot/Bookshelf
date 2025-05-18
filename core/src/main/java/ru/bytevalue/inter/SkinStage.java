package ru.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.bytevalue.service.*;

public class SkinStage extends Stage {

    SkinStage(Viewport viewport, final ActivitySwitcher activitySwitcher) {
        setViewport(viewport);
        setViewport(viewport);

        Button neon = new Button(viewport) {
            @Override
            public void action() {
                SkinService.setSkin(Skin.NEON);
                SoundService.playMenuSound();                       //TODO
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(443, 811, 187, 179);
            }
        };
        Button halloween = new Button(viewport) {
            @Override
            public void action() {
                SkinService.setSkin(Skin.HALLOWEEN);
                SoundService.playMenuSound();                       //TODO
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(675, 811, 187, 179);
            }
        };
        Button usual = new Button(viewport) {
            @Override
            public void action() {
                SkinService.setSkin(Skin.USUAL);
                VibrationService.vibrate(20);
                SoundService.playMenuSound();                       //TODO
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(443, 587, 187, 179);
            }
        };
        Button back = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
                SoundService.playMenuSound();                       //TODO
                VibrationService.vibrate(20);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(479, 36, 313, 77);
            }
        };

        Actor background = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getSkinsTexture(), 0, 0);
            }
        };
        addActor(background);
        addActor(neon);
        addActor(halloween);
        addActor(usual);
        addActor(back);


    }

}
