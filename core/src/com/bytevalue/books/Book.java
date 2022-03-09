package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bytevalue.TextureIds;

public class Book extends BookPositionerActor implements Comparable<Book>{

    private int id;
    private TextureRegion region;
    private final BookHandler bookHandler;

    private int positionNumber;

    private Vector2 destination;
    private boolean isMoving = false;
    private boolean isSelected =false;

    Book(int id,int positionNumber,BookHandler bookHandler,BookContainer bookContainer){
        this.id=id;
        this.positionNumber=positionNumber;
        this.bookHandler = bookHandler;
        setBookContainer(bookContainer);
        setZIndex(2);
        region= TextureIds.getTextureById(id);
        setSize(bookContainer.getBookWidth(),bookContainer.getBookHeight());
    }


    public void changeSelection(){
        isSelected =!isSelected;
        isMoving =true;
        if (isSelected) {
            region= TextureIds.getTextureById(1);
            destination=new Vector2(getStartPosition()).add(getUpOffset());
        } else {
            region=TextureIds.getTextureById(2);
            destination=getStartPosition();
        }
    }

    public boolean isSelected() {
        return isSelected;
    }


    @Override
    public void act(float delta) {
        if (isMoving) {
            isMoving =!moveTo(delta,destination);
        }
        super.act(delta);
        if(bookHandler.isBookPressured()&&isSelected){
            moveTo(delta,bookHandler.getMainBookPosition());
        }
    }



    @Override
    public void justTouched() {
        if (!bookHandler.isBookPressured()) {
            changeSelection();
            bookHandler.selectBook(this);
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onStartPressure() {
        if(!bookHandler.isBookPressured()) {
            if (!isSelected) {
                justTouched();
            }
            Gdx.input.vibrate(20);
            bookHandler.setBookPressured(true);
            bookHandler.setMainBook(this);
        }
    }

    @Override
    public void isPressed(Vector2 touch) {
        setPosition(touch.x - getWidth() / 2, touch.y - getHeight() / 2);
    }


    @Override
    public void onPressureRelease() {
        if(isSelected) {
            bookHandler.release();
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getRealX(), getRealY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(getPositionNumber(),book.getPositionNumber());
    }

    public int getPositionNumber(){
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public void setDestination(Vector2 destination){
        this.destination=destination;
    }

}
