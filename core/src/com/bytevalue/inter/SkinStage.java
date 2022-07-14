package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.Skin;
import com.bytevalue.service.SkinService;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;

public class SkinStage extends Stage {

    SkinStage(Viewport viewport, final ActivitySwitcher activitySwitcher){
        setViewport(viewport);
        setViewport(viewport);

        Button neon = new Button(viewport){
            @Override
            public void action() {
                SkinService.setSkin(Skin.NEON);
                SoundService.playMenuSound();                       //TODO
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(443,811,187,179);
            }
        };
        Button halloween = new Button(viewport){
            @Override
            public void action() {
                SkinService.setSkin(Skin.HALLOWEEN);
                SoundService.playMenuSound();                       //TODO
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(675,811,187,179);
            }
        };
        Button usual = new Button(viewport){
            @Override
            public void action() {
                SkinService.setSkin(Skin.NEON);
                SoundService.playMenuSound();                       //TODO
            }

            @Override
            public TextureRegion getTextureRegion() {
                return null;
            }

            @Override
            public Rectangle getRectangle() {
                return new Rectangle(443,587,187,179);
            }
        };

        Actor background = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(TextureService.getSkinsTexture(),0,0);
            }
        };
        addActor(background);
        addActor(neon);
        addActor(halloween);
        addActor(usual);


    }

}
