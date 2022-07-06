package com.bytevalue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Button extends Actor {

    private Viewport viewport;
    private float pressureTime=0;
    private Rectangle rectangle;


    public Button(Viewport viewport){
        this.viewport=viewport;
        rectangle=getRectangle();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getTextureRegion()!=null)
        batch.draw(getTextureRegion(),getRectX(),getRectY());
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(rectangle.contains(touch)) {
                pressureTime+=delta;
            }else {
                pressureTime=0;
            }
        }else {
            if (pressureTime>0&&pressureTime<1){
                pressureTime=0;
                action();
            }
        }
    }

    public abstract void action();
    public abstract TextureRegion getTextureRegion();
    public abstract Rectangle getRectangle();
    public float getRectX(){
        return rectangle.getX();
    }
    public float getRectY(){
        return rectangle.getY();
    }
}
