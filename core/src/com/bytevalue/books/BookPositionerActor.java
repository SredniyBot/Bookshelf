package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class BookPositionerActor extends Actor {

    private final float minPressureTime=0.25f;
    private final Vector2 upOffset=new Vector2(0,20);

    private BookContainer bookContainer;
    private final Vector2 bias;

    private Viewport viewport;
    private float pressureTime=0;
    private boolean isPressuredBefore =false;
    private boolean isTouchedBefore =false;

    private boolean isReturning=false;

    private final Rectangle rectangle;

    BookPositionerActor(){
        bias=new Vector2(0,0);
        rectangle=new Rectangle(getX(),getY(),getWidth(),getHeight());
    }


    public boolean moveTo(float delta, Vector2 endOffset) {
        float yDelta= endOffset.y-bias.y-getStartPosition().y;
        float xDelta= endOffset.x-bias.x-getStartPosition().x;
        if (Math.sqrt(xDelta*xDelta+yDelta*yDelta)>=5){
            bias.y+= (yDelta * delta *20);
            bias.x+= (xDelta * delta *20);
            resetRectangle();
            return false;
        }else {
            bias.y=endOffset.y-getStartPosition().y;
            bias.x=endOffset.x-getStartPosition().x;
            resetRectangle();
            return true;
        }
    }

    public boolean moveToHome(float delta) {
        if (Math.sqrt(bias.y*bias.y+bias.x*bias.x)>=5){
            bias.y-= (bias.y * delta *20);
            bias.x-= (bias.x * delta *20);
            resetRectangle();
            return false;
        }else {
            bias.y=0;
            bias.x=0;
            resetRectangle();
            return true;
        }
    }

    public boolean moveToUp(float delta) {
        return moveTo(delta,new Vector2(upOffset).add(getStartPosition()));
    }


    @Override
    public void act(float delta) {

        if(isReturning){
            isReturning=!moveToUp(delta);
            return;
        }

        if (Gdx.input.isTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(contains(touch)) {
                pressureTime += delta;
                isTouchedBefore=true;
            }else {
                if (pressureTime>0&&pressureTime<minPressureTime){
                    justTouched();
                    if (isTouchedBefore){
                        onRelease();
                        pressureTime=0;
                        isTouchedBefore=false;
                        isPressuredBefore =false;
                    }
                }
            }
            if (pressureTime >= minPressureTime) {
                if (!isPressuredBefore){
                    onStartPressure();
                    isPressuredBefore =true;
                }
                isPressed(touch);
            }
        }else {
            if (pressureTime>=minPressureTime)onPressureRelease();
            if (pressureTime>0&&pressureTime<minPressureTime){
                justTouched();
            }
            if (isTouchedBefore){
                onRelease();
                pressureTime=0;
                isTouchedBefore=false;
                isPressuredBefore =false;
            }
        }
    }




    @Override
    @Deprecated
    public void setX(float x) {
        super.setX(x);
    }
    @Override
    @Deprecated
    public void setY(float y) {
        super.setY(y);

    }
    @Override
    public void setPosition(float x, float y) {
        bias.set(x-getStartPosition().x,y-getStartPosition().y);
        resetRectangle();
    }
    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        resetRectangle();
    }
    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        resetRectangle();
    }
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        resetRectangle();
    }
    public void setBookContainer(BookContainer bookContainer) {
        this.bookContainer = bookContainer;
    }
    public abstract void justTouched();
    public abstract void isPressed(Vector2 touch);
    public abstract void onStartPressure();
    public abstract void onRelease();
    public abstract void onPressureRelease();
    public abstract int getPositionNumber();
    public Vector2 getStartPosition(){
        return bookContainer.getStartPosition(getPositionNumber());
    }
    public float getRealX(){
        return rectangle.x;
    }
    public float getRealY(){
        return rectangle.y;
    }
    public Vector2 getPosition() {
        return new Vector2(getStartPosition().x+bias.x,getStartPosition().y+bias.y);
    }
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    public boolean contains(Vector2 vector2){
        return rectangle.contains(vector2);
    }
    public Vector2 getUpOffset(){
        return upOffset;
    }

    public void startReturning() {
        isReturning= true;
    }

    private void resetRectangle(){
        rectangle.set(getStartPosition().x+bias.x,getStartPosition().y+bias.y,
                getWidth(),getHeight());
    }

}