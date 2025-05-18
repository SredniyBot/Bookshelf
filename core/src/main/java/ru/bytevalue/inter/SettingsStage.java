package ru.bytevalue.inter;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.bytevalue.service.*;

public class SettingsStage extends Stage {


    public SettingsStage(Viewport viewport, final ActivitySwitcher activitySwitcher) {
        setViewport(viewport);
        Button soundButton = new Button(viewport) {
            boolean sound;

            {
                Preferences prefs = Gdx.app.getPreferences("main");
                sound = prefs.getBoolean("s", true);
                setZIndex(100000);
            }

            @Override
            public void action() {
                sound = !sound;
                Preferences prefs = Gdx.app.getPreferences("main");
                prefs.putBoolean("s", sound);
                SoundService.setSound(sound);
                SoundService.playMenuSound();
                prefs.flush();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return TextureService.getSoundTexture(sound);
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(666, 700, 72, 72);
            }
        };
        Button vibrationButton = new Button(viewport) {
            boolean vibrates;

            {
                Preferences prefs = Gdx.app.getPreferences("main");
                vibrates = prefs.getBoolean("v", true);
                setZIndex(100000);
            }

            @Override
            public void action() {
                vibrates = !vibrates;
                Preferences prefs = Gdx.app.getPreferences("main");
                prefs.putBoolean("v", vibrates);
                VibrationService.setVibrate(vibrates);
                VibrationService.vibrate(200);
                prefs.flush();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return TextureService.getVibrateTexture(vibrates);
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(540, 700, 72, 72);
            }
        };
        Button backButton = new Button(viewport) {
            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.PAUSE);
                SoundService.playMenuSound();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(606, 268, 72, 72);
            }
        };

        Actor background = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getSettingsTexture(), 0, 0);
            }
        };


        LanguageButton lb = new LanguageButton(viewport);

        addActor(background);
        addActor(backButton);
        addActor(vibrationButton);
        addActor(soundButton);
        addActor(lb);

    }
}
