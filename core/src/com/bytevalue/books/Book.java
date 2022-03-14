package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bytevalue.TextureIds;

public class Book extends BookPositionerActor implements Comparable<Book>{

    private int id;
    private TextureRegion region;
    private final BookHandler bookHandler;

    private int positionNumber;
    private boolean selected;

    private Vector2 destination;

    Book(int id,int positionNumber,BookHandler bookHandler,BookContainer bookContainer){
        this.id=id+5;
        this.positionNumber=positionNumber;
        this.bookHandler = bookHandler;
        destination=new Vector2(0,0);
        setBookContainerF(bookContainer);
        setZIndex(2);
        region= TextureIds.getTextureById(this.id);
        setSize(bookContainer.getBookWidth(),bookContainer.getBookHeight());
    }

    @Override
    public void act(float delta) {
        if (bookHandler.isShifting()){
            moveToHome(delta);
            return;
        }
        super.act(delta);
    }



    @Override
    public void onRelease() {
        bookHandler.release();
    }

    @Override
    public void onTouch() {
        if(!bookHandler.isBookPressured()) {
            Gdx.input.vibrate(20);
            bookHandler.selectBook(this);
        }
    }

    @Override
    public void onScreenTouch(float delta,Vector2 touch) {
        if (selected){
            destination.set(touch.x - getWidth() / 2, touch.y - getHeight() / 2);
            moveTo(delta,destination);
        }
    }


    @Override
    public void isPressed(Vector2 touch) {

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

    public int getId() {
        return id;
    }

    public Array<Book> getGoodNeighbours(){
        return getBookContainer().getSameNeighbours(this);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void replacePosition(int positionNumber){
        Vector2 c=getPosition();
        setPositionNumber(positionNumber);
        setPosition(c);
        startReturning();
    }

}
