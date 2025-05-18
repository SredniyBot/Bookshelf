package ru.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.bytevalue.service.*;


public class PauseStage extends Stage {

    public PauseStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);

        Button settingsButton = new Button(viewport){

            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.SETTINGS);
                SoundService.playMenuSound();

            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,468,312,77);
            }
        };
        Button mainMenuButton = new Button(viewport){
            @Override
            public void action() {
                SoundService.playMenuSound();
                activitySwitcher.switchActivity(Activity.MAIN_MENU);
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,591,312,77);
            }
        };
        Button continueButton = new Button(viewport){

            @Override
            public void action() {
                activitySwitcher.switchActivity(Activity.GAME);
                SoundService.playMenuSound();
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(483,718,312,77);
            }
        };

        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getPauseMenuTexture(),0,0);
            }
        };
        addActor(settingsButton);
        addActor(continueButton);
        addActor(mainMenuButton);
        addActor(background);
    }

}
